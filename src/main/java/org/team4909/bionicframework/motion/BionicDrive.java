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

    /* OI */
    private final BionicF310 speedInputGamepad;
    private final BionicAxis speedInputAxis;
    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;

    /* Sensors */
    private Gyro bionicGyro;
    private MotionProfileUtil pathgen;

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
        }

        @Override
        protected void execute() {
            double speed = speedInputGamepad.getSensitiveAxis(speedInputAxis);
            double rotation = rotationInputGamepad.getSensitiveAxis(rotationInputAxis);

            double leftMotorOutput;
            double rightMotorOutput;

            double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotation)), speed);

            if (speed >= 0.0 && rotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            } else if (speed >= 0.0 && rotation < 0.0) {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            } else if (speed < 0.0 && rotation >= 0.0) {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            } else { // speed < 0.0 &&  rotation < 0.0
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            }

            leftSRX.set(ControlMode.PercentOutput, limit(leftMotorOutput));
            rightSRX.set(ControlMode.PercentOutput, limit(rightMotorOutput));
        }

        private double limit(double value) {
            return Math.copySign(Math.abs(value) > 1.0 ? 1.0 : value, value);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }
    }

    public Command driveHalfVoltage() {
        return new DriveHalfVoltage(this);
    }

    private class DriveHalfVoltage extends Command {
        public DriveHalfVoltage(BionicDrive subsystem) {
           requires(subsystem);
        }

        @Override
        protected void initialize() {
            leftSRX.setNeutralMode(NeutralMode.Brake);
            rightSRX.setNeutralMode(NeutralMode.Brake);

            leftSRX.configOpenloopRamp(0);
            rightSRX.configOpenloopRamp(0);
        }

        @Override
        protected void execute() {
            leftSRX.set(ControlMode.PercentOutput, 0.5);
            rightSRX.set(ControlMode.PercentOutput, 0.5);

            System.out.println("Avg. Half Voltage Velocity (ticks/s): " +
                    ((leftSRX.getSelectedSensorVelocity(0) + rightSRX.getSelectedSensorVelocity(0)) / 2));
        }

        @Override
        protected boolean isFinished() {
            return false;
        }
    }

    public Command driveDistance(Waypoint[] points) {
        return new DriveTrajectory(this, pathgen.getTrajectory(points));
    }

    public Command driveWaypoints(Waypoint[] points) {
        return new DriveTrajectory(this, pathgen.getTrajectory(points));
    }

    private class DriveTrajectory extends Command {
        private final MotionProfileTrajectory trajectory;

        public DriveTrajectory(BionicDrive subsystem, MotionProfileTrajectory trajectory) {
            requires(subsystem);
            setInterruptible(false);

            this.trajectory = trajectory;
        }

        @Override
        protected void initialize() {
            leftSRX.setNeutralMode(NeutralMode.Brake);
            rightSRX.setNeutralMode(NeutralMode.Brake);

            leftSRX.configOpenloopRamp(0);
            rightSRX.configOpenloopRamp(0);

            leftSRX.zeroEncoderPosition();
            rightSRX.zeroEncoderPosition();

            leftSRX.initMotionProfile(trajectory.profileInterval, trajectory.left);
            rightSRX.initMotionProfile(trajectory.profileInterval, trajectory.right);
        }

        @Override
        protected void execute() {
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
        }
    }
}
