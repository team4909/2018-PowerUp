package org.team4909.bionicframework.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.sensors.BionicSRXEncoder;
import org.team4909.bionicframework.subsystems.drive.MotionProfileUtil.MotionProfileTrajectory;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.BionicF310;

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

        this.bionicGyro = bionicGyro;
        this.leftSRX.configEncoder(encoderConfig);
        this.rightSRX.configEncoder(encoderConfig);

        this.rightSRX.setInverted(true);
        this.rightSRX.setSensorPhase(true);

        this.pathgen = new MotionProfileUtil(motionProfileConfig);
        this.leftSRX.configOpenloopRamp(pathgen.motionProfileConfig.secondsFromNeutralToFull);
        this.rightSRX.configOpenloopRamp(pathgen.motionProfileConfig.secondsFromNeutralToFull);

        this.rotationInputGamepad = rotationInputGamepad;
        this.rotationInputAxis = rotationInputAxis;

        this.speedInputGamepad = speedInputGamepad;
        this.speedInputAxis = speedInputAxis;
    }

    /**
     * @return Returns Robot's Current Heading [0, 2pi)
     */
    public double getHeading() {
        return bionicGyro.getAngle() * (Math.PI / 180);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveOI(this));
    }

    public Command findMaxVelocity() {
        return new FindMaxVelocity(this);
    }

    public Command findRampTime() {
        return new FindRampTime(this);
    }

    public Command driveRotationTest() {
        return driveDistance(pathgen.motionProfileConfig.driveRotationTestFeet, -pathgen.motionProfileConfig.driveRotationTestFeet);
    }

    private Command driveDistance(double leftDistance, double rightDistance) {
        return new DriveTrajectory(this, pathgen.getTrajectory(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(leftDistance, 0, 0)
        }, new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(rightDistance, 0, 0)
        }));
    }

    public Command driveWaypoints(Waypoint[] points) {
        return new DriveTrajectory(this, pathgen.getTrajectory(points));
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

    private class FindMaxVelocity extends Command {
        double startTime;
        double runningAverage;

        public FindMaxVelocity(BionicDrive subsystem) {
            requires(subsystem);
        }

        @Override
        protected void initialize() {
            startTime = Timer.getFPGATimestamp();
        }

        @Override
        protected void execute() {
            leftSRX.set(ControlMode.PercentOutput, 0.5);
            rightSRX.set(ControlMode.PercentOutput, 0.5);

            leftSRX.enableVoltageCompensation(true);
            rightSRX.enableVoltageCompensation(true);

            double timeSinceStart = Timer.getFPGATimestamp() - startTime;
            double currentVelocityTicks = (leftSRX.getSelectedSensorVelocity(0) + rightSRX.getSelectedSensorVelocity(0)) * 10;

            if(timeSinceStart > 10) {
                if (runningAverage != 0) {
                    runningAverage = (runningAverage + currentVelocityTicks) / 2;
                } else {
                    runningAverage = currentVelocityTicks;
                }
            }
        }

        @Override
        protected boolean isFinished() {
            return (Timer.getFPGATimestamp() - startTime) > 30;
        }

        @Override
        protected void end() {
            System.out.println("Max Velocity (ticks/s): " + runningAverage);
        }
    }

    private class FindRampTime extends Command {
        double startTime;

        public FindRampTime(BionicDrive subsystem) {
            requires(subsystem);
        }

        @Override
        protected void initialize() {
            startTime = Timer.getFPGATimestamp();
        }

        @Override
        protected void execute() {
            leftSRX.set(ControlMode.PercentOutput, 0.5);
            rightSRX.set(ControlMode.PercentOutput, 0.5);
        }

        @Override
        protected boolean isFinished() {
            double currentVelocityTicks = (leftSRX.getSelectedSensorVelocity(0) + rightSRX.getSelectedSensorVelocity(0)) * 10;

            return currentVelocityTicks > pathgen.motionProfileConfig.maxVelocityTicks;
        }

        @Override
        protected void end() {
            System.out.println("Seconds from Neutral: " + (Timer.getFPGATimestamp() - startTime));
        }
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
            System.out.println("Final Heading: " + getHeading() + "rad");
        }
    }
}
