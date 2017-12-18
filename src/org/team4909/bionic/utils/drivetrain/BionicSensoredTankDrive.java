package org.team4909.bionic.utils.drivetrain;

import com.ctre.phoenix.Drive.SensoredTank;
import com.ctre.phoenix.Drive.Styles.Smart;
import com.ctre.phoenix.Mechanical.SensoredGearbox;
import com.ctre.phoenix.MotorControl.SmartMotorController;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

public class BionicSensoredTankDrive extends BionicDriveBase {
	private SensoredTank robotDrive;
	
	public BionicSensoredTankDrive(
			SensoredGearbox drivetrainLeftGearbox, SensoredGearbox drivetrainRightGearbox, 
			float wheelRadius, float drivetrainWidth, 
			BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		robotDrive = new SensoredTank(drivetrainLeftGearbox, drivetrainRightGearbox, false, true, wheelRadius, drivetrainWidth);
	}
	
	public BionicSensoredTankDrive(
			TalonSRX drivetrainLeftMotor, TalonSRX drivetrainLeftBackMotor,
			TalonSRX drivetrainRightMotor, TalonSRX drivetrainRightBackMotor, 
			SmartMotorController.FeedbackDevice feedbackDevice, 
			float wheelRadius, float drivetrainWidth, 
			float unitsPerRevolution, BionicDriveOIConstants driveOIConstants) {
		this(
			new SensoredGearbox(unitsPerRevolution, drivetrainLeftMotor, drivetrainLeftBackMotor, feedbackDevice),
			new SensoredGearbox(unitsPerRevolution, drivetrainRightMotor, drivetrainRightBackMotor, feedbackDevice),
			wheelRadius, drivetrainWidth,
			driveOIConstants
		);
	}

	public BionicSensoredTankDrive(
			TalonSRX drivetrainLeftMotor,
			TalonSRX drivetrainRightMotor,
			SmartMotorController.FeedbackDevice feedbackDevice, 
			float wheelRadius, float drivetrainWidth, 
			float unitsPerRevolution, BionicDriveOIConstants driveOIConstants) {
		this(
			new SensoredGearbox(unitsPerRevolution, drivetrainLeftMotor, feedbackDevice),
			new SensoredGearbox(unitsPerRevolution, drivetrainRightMotor, feedbackDevice),
			wheelRadius, drivetrainWidth,
			driveOIConstants
		);
	}

	
	public void arcadeDriveDirect(double speed, double rotation) {
		arcadeDriveDirect((float) speed, (float) rotation);
	}
	
	public void arcadeDriveDirect(float speed, float rotation) {
		 Math.copySign(speed, rotation);
		 
		robotDrive.set(Smart.PercentOutput, speed, rotation);
	}
}
