package org.team4909.bionic.utils.drivetrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class BionicTankDrive extends BionicDriveBase {
	private RobotDrive robotDrive;
	
	public BionicTankDrive(RobotDrive robotDrive, BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		this.robotDrive = robotDrive;
	}
	
	public BionicTankDrive(SpeedController drivetrainLeftMotor, SpeedController drivetrainRightMotor, BionicDriveOIConstants driveOIConstants) {
		this(
			new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor),
			driveOIConstants
		);
	}
	
	public BionicTankDrive(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, BionicDriveOIConstants driveOIConstants) {
		this(
			new RobotDrive(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor),
			driveOIConstants
		);
	}

	public void arcadeDriveDirect(double speed, double rotation) {
		 Math.copySign(speed, rotation);
		 
		robotDrive.arcadeDrive(speed, rotation);
	}
}
