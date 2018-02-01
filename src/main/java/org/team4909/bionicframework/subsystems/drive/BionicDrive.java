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
import org.team4909.bionicframework.subsystems.drive.commands.DriveOI;
import org.team4909.bionicframework.subsystems.drive.commands.FindMaxVelocity;
import org.team4909.bionicframework.subsystems.drive.commands.FindRampTime;

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
        this.leftSRX.configOpenloopRamp(pathgen.motionProfileConfig.getSecondsFromNeutralToFull());
        this.rightSRX.configOpenloopRamp(pathgen.motionProfileConfig.getSecondsFromNeutralToFull());

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
        return driveDistance(pathgen.motionProfileConfig.getDriveRotationTestFeet(), -pathgen.motionProfileConfig.getDriveRotationTestFeet());
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
}
