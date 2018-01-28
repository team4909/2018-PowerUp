package org.team4909.bionicframework.motion;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.team4909.bionicframework.hardware.BionicSRX;
import org.team4909.bionicframework.motion.MotionProfileUtil.MotionProfileTrajectory;
import org.team4909.bionicframework.operator.BionicAxis;
import org.team4909.bionicframework.operator.BionicF310;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * BionicDrive abstracts away much of the underlying drivetrain functionality
 */
public class BionicDrive extends Subsystem {
    /* Hardware */
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;
    private final DifferentialDrive differentialDrive;

    /* OI */
    private final BionicF310 speedInputGamepad;
    private final BionicAxis speedInputAxis;
    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;

    /* Sensors */
    private Gyro bionicGyro;
    private MotionProfileUtil pathgen;

    /* Hardware Initialization */

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
                       BionicSRXEncoder encoderConfig,
                       MotionProfileConfig motionProfileConfig,
                       Gyro bionicGyro) {
        super();

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.leftSRX.configEncoder(encoderConfig);
        this.rightSRX.configEncoder(encoderConfig);

        this.rightSRX.setInverted(true);
        this.rightSRX.setSensorPhase(true);

        this.bionicGyro = bionicGyro;

        this.pathgen = new MotionProfileUtil(motionProfileConfig);

        this.rotationInputGamepad = rotationInputGamepad;
        this.rotationInputAxis = rotationInputAxis;

        this.speedInputGamepad = speedInputGamepad;
        this.speedInputAxis = speedInputAxis;

        differentialDrive = new DifferentialDrive(leftSRX, rightSRX);
    }

    /**
     * @return Returns Robot's Current Heading [0, 360)
     */
    public double getHeading() {
        return bionicGyro.getAngle();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveOI(this));
    }

    private class DriveOI extends Command {
        public DriveOI(BionicDrive subsystem) {
            requires(subsystem);
        }

        @Override
        protected void initialize() {
            super.initialize();

            leftSRX.setNeutralMode(NeutralMode.Coast);
            rightSRX.setNeutralMode(NeutralMode.Coast);

            leftSRX.configOpenloopRamp(pathgen.motionProfileConfig.secondsFromNeutralToFull);
            rightSRX.configOpenloopRamp(pathgen.motionProfileConfig.secondsFromNeutralToFull);

            rightSRX.setInverted(false);

            differentialDrive.setSafetyEnabled(true);
        }

        @Override
        protected void execute() {
            double speed = speedInputGamepad.getThresholdAxis(speedInputAxis, 0.15);
            double rotation = rotationInputGamepad.getThresholdAxis(rotationInputAxis, 0.15);

            differentialDrive.arcadeDrive(speed, rotation, false);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }
    }

    public Command driveHalfVoltage() {
        return new DriveHalfVoltage(this);
    }

    private class DriveHalfVoltage extends AutoDriveCommand {
        public DriveHalfVoltage(BionicDrive subsystem) {
            super(subsystem);
        }

        @Override
        protected void execute() {
            leftSRX.set(0.5);
            rightSRX.set(0.5);

            System.out.println("L(ticks/s): " + leftSRX.getSelectedSensorVelocity(0) + ", R(ticks/s): "+ rightSRX.getSelectedSensorVelocity(0));
        }
    }

    @Override
    public void periodic() {
        super.periodic();

        System.out.println(leftSRX.getSelectedSensorPosition(0) + "," + rightSRX.getSelectedSensorPosition(0));
    }

    public Command driveDistance() {
        return new DriveDistance(this);
    }

    private class DriveDistance extends AutoDriveCommand {
        public DriveDistance(BionicDrive subsystem) {
            super(subsystem);
        }

        @Override
        protected void initialize() {
            super.initialize();

            System.out.println("Starting Pos. PID");

            leftSRX.set(ControlMode.Position, pathgen.motionProfileConfig.driveTestTicks);
            rightSRX.set(ControlMode.Position, pathgen.motionProfileConfig.driveTestTicks);
        }

        @Override
        protected boolean isFinished() {
            return (Math.abs(leftSRX.getClosedLoopError(0)) < 100) && (Math.abs(rightSRX.getClosedLoopError(0)) < 100);
        }

        @Override
        protected void end() {
            super.end();

            System.out.println("Finished Pos. PID");
        }
    }

    /**
     * @param points Path consisting of waypoints to follow
     * @return Returns a Command that can be used by the operator and autonomous CommandGroups.
     */
    public Command driveWaypoints(Waypoint[] points) {
        return new DriveWaypoints(this, points);
    }

    private class DriveWaypoints extends AutoDriveCommand {
        private final MotionProfileTrajectory trajectory;

        public DriveWaypoints(BionicDrive subsystem, Waypoint[] points) {
            super(subsystem);

            System.out.println("Generating Trajectory...");
            trajectory = pathgen.getTrajectory(points);
        }

        @Override
        protected void initialize() {
            super.initialize();

            System.out.println("Initializing Trajectory...");
            leftSRX.initMotionProfile(trajectory.profileInterval, trajectory.left);
            rightSRX.initMotionProfile(trajectory.profileInterval, trajectory.right);
        }

        @Override
        protected void execute() {
            System.out.println("Executing Trajectory...");

            leftSRX.runMotionProfile();
            rightSRX.runMotionProfile();
        }

        @Override
        protected boolean isFinished() {
            return leftSRX.isMotionProfileFinished() && rightSRX.isMotionProfileFinished();
        }

        @Override
        protected void end() {
            super.end();

            System.out.println("Executed Trajectory.");

            leftSRX.resetProfileSlot();
            rightSRX.resetProfileSlot();
        }
    }

    private abstract class AutoDriveCommand extends Command {
        private boolean defaultSRXInvertState = false;

        public AutoDriveCommand(BionicDrive subsystem) {
            requires(subsystem);

            setInterruptible(false);
        }

        @Override
        protected void initialize() {
            leftSRX.setNeutralMode(NeutralMode.Brake);
            rightSRX.setNeutralMode(NeutralMode.Brake);

            leftSRX.configOpenloopRamp(0);
            rightSRX.configOpenloopRamp(0);

            leftSRX.zeroEncoderPosition();
            rightSRX.zeroEncoderPosition();

            rightSRX.setInverted(true);

            differentialDrive.setSafetyEnabled(false);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }
    }
}
