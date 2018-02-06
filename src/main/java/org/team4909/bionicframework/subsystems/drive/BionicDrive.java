package org.team4909.bionicframework.subsystems.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import jaci.pathfinder.Pathfinder;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.drive.commands.DriveTrajectory;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainProfileUtil;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.controllers.BionicF310;

import jaci.pathfinder.Waypoint;
import org.team4909.bionicframework.subsystems.drive.commands.DriveOI;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainTrajectory;

/**
 * BionicDrive abstracts away much of the underlying drivetrain functionality
 */
public class BionicDrive extends Subsystem {
    /* Hardware */
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;
    private final BionicSingleSolenoid shifter;

    private final Command defaultCommand;

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
                       BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier,
                       BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier,
                       DrivetrainConfig drivetrainConfig,
                       Gyro bionicGyro, BionicSingleSolenoid shifter) {
        super();

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;
        this.leftSRX.config_kF(1023 / drivetrainConfig.getMaxVelocity());
        this.rightSRX.config_kF(1023 / drivetrainConfig.getMaxVelocity());

        this.bionicGyro = bionicGyro;
        this.pathgen = new DrivetrainProfileUtil(drivetrainConfig);

        this.shifter = shifter;

        this.defaultCommand = new DriveOI(this, leftSRX, rightSRX,
                speedInputGamepad, speedInputAxis, speedMultiplier,
                rotationInputGamepad, rotationInputAxis, rotationMultiplier);
    }

    /**
     * @return Returns Robot's Current Heading [0, 2pi)
     */
    public double getHeading() {
        return Pathfinder.d2r(bionicGyro.getAngle());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(defaultCommand);
    }

    public Commandable shiftGear(boolean gear) {
        return shifter.setState(gear);
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
