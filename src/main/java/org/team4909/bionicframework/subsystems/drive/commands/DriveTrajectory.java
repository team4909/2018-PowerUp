package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.*;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class DriveTrajectory extends Command {
    private final BionicDrive bionicDrive;
    private final BionicSRX leftSRX, rightSRX;
    private final TrajectoryPoint[] leftTrajectory, rightTrajectory;
    private final double kVelocity, kAccel, vIntercept;

    public DriveTrajectory(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX,
                           Waypoint[] points,
                           // Following Control
                           double xProportional, double xDerivative,
                           double kVelocity, double kAccel, double vIntercept) {
        requires(bionicDrive);

        this.bionicDrive = bionicDrive;
        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.kVelocity = kVelocity;
        this.kAccel = kAccel;
        this.vIntercept = vIntercept;

        /*** GENERATE TRAJECTORY ***/
        Trajectory centerTrajectory = Pathfinder.generate(points, bionicDrive.pathfinderConfig);
        TankModifier modifier = new TankModifier(centerTrajectory).modify(bionicDrive.wheelbaseWidth);

        leftTrajectory = convertToSRXTrajectory(modifier.getLeftTrajectory());
        rightTrajectory = convertToSRXTrajectory(modifier.getRightTrajectory());

        leftSRX.config_kPIDF(xProportional, 0, xDerivative, 1023 / 12);
        rightSRX.config_kPIDF(xProportional, 0, xDerivative, 1023 / 12);
    }

    public TrajectoryPoint[] convertToSRXTrajectory(Trajectory trajectory) {
        int length = trajectory.length();
        TrajectoryPoint[] parsedSRXTrajectory = new TrajectoryPoint[length];

        for (int i = 0; i < length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();

            // Profile Data: Position is in Ticks, Velo is in Volts
            point.position = (trajectory.get(i).x / bionicDrive.ticksToFeet);
            point.velocity = kVelocity * trajectory.get(i).velocity + kAccel * trajectory.get(i).acceleration + vIntercept;

            // Configuration Data
            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms;
            point.profileSlotSelect0 = 0;
            point.isLastPoint = (i == length - 1);

            point.zeroPos = (i == 0);
            point.velocity = point.zeroPos ? 0 : point.velocity;

//            point.position = (invert ? -1 : 1) * point.position;
//            point.velocity = (invert ? -1 : 1) * point.velocity;

            parsedSRXTrajectory[i] = point;
        }

        return parsedSRXTrajectory;
    }

    @Override
    protected void initialize() {
        if (bionicDrive.encoderOverride) {
            cancel();
        }

        leftSRX.configMotionProfileTrajectoryPeriod(10, 0);
        rightSRX.configMotionProfileTrajectoryPeriod(10, 0);

        leftSRX.initMotionProfile(5, leftTrajectory);
        rightSRX.initMotionProfile(5, rightTrajectory);
    }

    @Override
    protected void execute() {
        leftSRX.runMotionProfile();
        rightSRX.runMotionProfile();
    }

    @Override
    protected boolean isFinished() {
        return leftSRX.isMotionProfileFinished()
                && rightSRX.isMotionProfileFinished()
                && leftSRX.getClosedLoopError(0) < (.25 / bionicDrive.ticksToFeet)
                && rightSRX.getClosedLoopError(0) < (.25 / bionicDrive.ticksToFeet);
    }

    @Override
    protected void end() {
        System.out.println("FINAL HEADING: " + bionicDrive.getHeading() + " DEG"); // NOT RADIANS
    }
}