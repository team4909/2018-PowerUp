package org.team4909.bionic.utils.drivetrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class BionicTankDrive extends BionicDriveBase {
	private RobotDrive robotDrive;
	
	public BionicTankDrive(SpeedController drivetrainLeftMotor, SpeedController drivetrainRightMotor, BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor);
	}
	
	public BionicTankDrive(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor);
	}

	public void arcadeDriveDirect(double speed, double rotation) {
		robotDrive.arcadeDrive(speed, rotation);
	}
}
