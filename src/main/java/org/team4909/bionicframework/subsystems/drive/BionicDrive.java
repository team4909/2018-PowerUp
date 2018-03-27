package org.team4909.bionicframework.subsystems.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.controllers.BionicF310;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.subsystems.drive.commands.DriveOI;
import org.team4909.bionicframework.subsystems.drive.commands.DriveRotate;
import org.team4909.bionicframework.subsystems.drive.commands.DriveTrajectory;
import org.team4909.bionicframework.subsystems.drive.commands.InvertDriveDirection;

/**
 * BionicDrive abstracts away much of the underlying drivetrain functionality
 */
public class BionicDrive extends Subsystem {
    /* Hardware */
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;
    private final BionicSingleSolenoid shifter;

    public final DriveOI defaultCommand;

    /* Sensors */
    private final Gyro bionicGyro;
    public boolean encoderOverride;

    public final double wheelbaseWidth, ticksToFeet;
    public final Trajectory.Config pathfinderConfig;
    public double speedDeltaLimit, rotationDeltaLimit;

    /**
     * @param leftSRX              Left Drivetrain SRX
     * @param rightSRX             Right Drivetrain SRX
     * @param speedInputGamepad    Speed Input Gamepad/Joystick
     * @param speedInputAxis       Speed Input Axis
     * @param rotationInputGamepad Rotation Input Gamepad/Joystick
     * @param rotationInputAxis    Rotation Input Axis
     * @param bionicGyro           Gyro to Use for Closed-Loop
     */
    public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
                       BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier, double speedDeltaLimit,
                       BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier, double rotationDeltaLimit,
                       Gyro bionicGyro, double maxVelocity, double maxAccel, double maxJerk,
                       double wheelbaseWidth, double ticksToFeet) {
        this(leftSRX, rightSRX,
                speedInputGamepad, speedInputAxis, speedMultiplier, speedDeltaLimit,
                rotationInputGamepad, rotationInputAxis, rotationMultiplier, rotationDeltaLimit,
                bionicGyro, maxVelocity, maxAccel, maxJerk, wheelbaseWidth, ticksToFeet, null);
    }

    public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
                       BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier, double speedDeltaLimit,
                       BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier, double rotationDeltaLimit,
                       Gyro bionicGyro, double maxVelocity, double maxAccel, double maxJerk,
                       double wheelbaseWidth, double ticksToFeet, BionicSingleSolenoid shifter) {
        super();

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.speedDeltaLimit = speedDeltaLimit;
        this.rotationDeltaLimit = rotationDeltaLimit;

        this.bionicGyro = bionicGyro;

        this.shifter = shifter;

        this.defaultCommand = new DriveOI(this, leftSRX, rightSRX,
                speedInputGamepad, speedInputAxis, speedMultiplier,
                rotationInputGamepad, rotationInputAxis, rotationMultiplier);

        this.ticksToFeet = ticksToFeet;
        this.wheelbaseWidth = wheelbaseWidth;

        this.pathfinderConfig = new Trajectory.Config(
                Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 10, maxVelocity, maxAccel, maxJerk
        )
    }

    /**
     * @return Returns Robot's Current Heading [0, 2pi)
     */
    public double getHeading() {
        return bionicGyro.getAngle();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(defaultCommand);
    }

    public Commandable invertDirection() {
        return new InvertDriveDirection(this);
    }

    public boolean getGear(){
        if(shifter != null) {
            return shifter.get();
        } else {
            return false;
        }
    }

    public Commandable changeGear() {
        if(shifter != null) {
            return shifter.invert();
        } else {
            return null;
        }
    }

    public Command driveWaypoints(Waypoint[] points, double maxVelocity, double maxAccel, double maxJerk,
                                  double xProportional, double xDerivative,
                                  double kVelocity, double kAccel, double vIntercept) {
        return new DriveTrajectory(
                this, leftSRX, rightSRX,
                points, maxVelocity, maxAccel, maxJerk,
                xProportional, xDerivative, kVelocity, kAccel, vIntercept
        );
    }

    public Command driveRotation(double angle) {
        return new DriveRotate(this, leftSRX, rightSRX, angle);
    }
}
