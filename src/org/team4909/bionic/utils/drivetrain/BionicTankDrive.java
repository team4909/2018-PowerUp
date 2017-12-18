package org.team4909.bionic.utils.drivetrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class BionicTankDrive implements IBionicDriveBase {
	private RobotDrive robotDrive;
	private BionicDriveOIConstants driveOIConstants;
	
	public BionicTankDrive(SpeedController drivetrainLeftMotor, SpeedController drivetrainRightMotor, BionicDriveOIConstants driveOIConstants) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor);
		
		this.driveOIConstants = driveOIConstants;
	}
	
	public BionicTankDrive(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, BionicDriveOIConstants driveOIConstants) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor);
		
		this.driveOIConstants = driveOIConstants;
	}

	public void arcadeDriveDirect(double speed, double rotation) {
		robotDrive.arcadeDrive(speed, rotation);
	}

	public void arcadeDriveScaled(double speed, double rotation) {
		arcadeDriveDirect(speed*driveOIConstants.speedFactor, rotation*driveOIConstants.rotationFactor);
	}
}
