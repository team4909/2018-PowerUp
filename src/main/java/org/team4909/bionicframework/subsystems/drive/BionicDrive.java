package org.team4909.bionicframework.subsystems.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import jaci.pathfinder.Pathfinder;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.sensors.SRXEncoder;
import org.team4909.bionicframework.subsystems.drive.commands.DriveTrajectory;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainProfileUtil;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.BionicF310;

import jaci.pathfinder.Waypoint;
import org.team4909.bionicframework.subsystems.drive.commands.DriveOI;
import org.team4909.bionicframework.subsystems.drive.commands.FindMaxVelocity;
import org.team4909.bionicframework.subsystems.drive.commands.FindRampTime;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainTrajectory;

/**
 * BionicDrive abstracts away much of the underlying drivetrain functionality
 */
public class BionicDrive extends Subsystem {
    /* Hardware */
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;

    /* OI */
    private final BionicF310 speedInputGamepad;
    private final BionicAxis speedInputAxis;
    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;

    /* Sensors */
    private final Gyro bionicGyro;
    public final DrivetrainProfileUtil pathgen;

    /**
     * @param leftSRX              Left Drivetrain SRX
     * @param rightSRX             Right Drivetrain SRX
     * @param speedInputGamepad    Speed Input Gamepad/Joystick
     * @param speedInputAxis       Speed Input Axis
     * @param rotationInputGamepad Rotation Input Gamepad/Joystick
     * @param rotationInputAxis    Rotation Input Axis
     * @param encoder              Encoder plugged into SRXs
     * @param bionicGyro           Gyro to Use for Closed-Loop
     */
    public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
                       BionicF310 speedInputGamepad, BionicAxis speedInputAxis,
                       BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis,
                       SRXEncoder encoderConfig,
                       DrivetrainConfig drivetrainConfig,
                       Gyro bionicGyro) {
        super();

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.bionicGyro = bionicGyro;
        this.leftSRX.configEncoder(encoderConfig);
        this.rightSRX.configEncoder(encoderConfig);

        this.rightSRX.setInverted(true);
        this.rightSRX.setSensorPhase(true);

        this.pathgen = new DrivetrainProfileUtil(drivetrainConfig);
        this.leftSRX.configOpenloopRamp(pathgen.drivetrainConfig.getSecondsFromNeutralToFull());
        this.rightSRX.configOpenloopRamp(pathgen.drivetrainConfig.getSecondsFromNeutralToFull());

        this.rotationInputGamepad = rotationInputGamepad;
        this.rotationInputAxis = rotationInputAxis;

        this.speedInputGamepad = speedInputGamepad;
        this.speedInputAxis = speedInputAxis;
    }

    /**
     * @return Returns Robot's Current Heading [0, 2pi)
     */
    public double getHeading() {
        return Pathfinder.d2r(bionicGyro.getAngle());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveOI(this, leftSRX, rightSRX, speedInputGamepad, speedInputAxis, rotationInputGamepad, rotationInputAxis));
    }

    public Command findMaxVelocity() {
        return new FindMaxVelocity(this, leftSRX, rightSRX);
    }

    public Command findRampTime() {
        return new FindRampTime(this, leftSRX, rightSRX);
    }

//    public Command driveRotationTest() {
//        return driveTrajectory(pathgen.getRotationalTrajectory(
//                pathgen.drivetrainConfig.getDriveRotationTestFeet(),
//                -pathgen.drivetrainConfig.getDriveRotationTestFeet()
//        );
//    }

    private Command driveTrajectory(DrivetrainTrajectory trajectory) {
        return new DriveTrajectory(this, leftSRX, rightSRX, trajectory);
    }

    public Command driveWaypoints(Waypoint[] points) {
        return driveTrajectory(pathgen.getTrajectory(points));
    }
}
