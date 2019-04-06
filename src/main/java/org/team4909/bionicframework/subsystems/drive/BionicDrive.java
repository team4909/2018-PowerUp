package org.team4909.bionicframework.subsystems.drive;

 import edu.wpi.first.wpilibj.command.Command;
 import edu.wpi.first.wpilibj.command.Subsystem;
 import org.team4909.bionicframework.hardware.motor.BionicSRX;
 import edu.wpi.first.wpilibj.interfaces.Gyro;
 import jaci.pathfinder.Waypoint;
 import org.team4909.bionicframework.hardware.motor.BionicSRX;
 import edu.wpi.first.wpilibj.SpeedControllerGroup;
 import edu.wpi.first.wpilibj.command.Subsystem;
 import edu.wpi.first.wpilibj.drive.DifferentialDrive;
 import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 import org.team4909.bionicframework.subsystems.drive.commands.DriveOI;

public class BionicDrive extends Subsystem {
    BionicSRX frontLeftSparkMax, rearLeftSparkMax;
    DifferentialDrive bionicDrive;
    double speedMultiplier = 1;
    double turnMultiplier = 1;
    boolean inverted = false;
    boolean preciseMode = false;
    final double kP = 0.25;
    final double kD = 0;

    public BionicDrive() {
        System.out.println("here2");
        frontLeftSparkMax = new BionicSRX(2,false);
        rearLeftSparkMax = new BionicSRX(4, true);

        bionicDrive = new DifferentialDrive(frontLeftSparkMax, rearLeftSparkMax);
    }

    public void driveAssisted(double throttle, double angleOffset) {
        // System.out.println("Angle offset = " + angleOffset);
        double correction = angleOffset * kP - angleOffset * kD;
        arcadeDrive(throttle * 0.5, correction);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        double leftSpeedOutput = leftSpeed;
        double rightSpeedOutput = rightSpeed;

        if (inverted) { //inverts tankDrive direction
            leftSpeedOutput = -rightSpeed;
            rightSpeedOutput = -leftSpeed;
        }

        leftSpeedOutput = leftSpeedOutput * speedMultiplier;
        rightSpeedOutput = rightSpeedOutput * speedMultiplier;

        bionicDrive.tankDrive(leftSpeedOutput, rightSpeedOutput);
    }

    public void arcadeDrive(double leftSpeed, double rightSpeed) {
        double speedOutput = leftSpeed;
        double turnOutput = rightSpeed;
            turnMultiplier = 1;
            speedMultiplier = -1;

            SmartDashboard.putNumber("Drivetrain turnMultiplier", turnMultiplier);
            SmartDashboard.putNumber("Drivetrain speedMultiplier", speedMultiplier);


        speedOutput = speedOutput * speedMultiplier;
        turnOutput = turnOutput * turnMultiplier;

        bionicDrive.arcadeDrive(turnOutput, speedOutput);
    }

    public void invertDriveDirection() {
        inverted = !inverted;
    }

    public void togglePreciseMode() {
        preciseMode = !preciseMode;
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DriveOI());
    }

    @Override
    public void periodic() {
    }
}
