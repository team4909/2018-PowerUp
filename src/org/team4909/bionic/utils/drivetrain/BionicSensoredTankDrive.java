package org.team4909.bionic.utils.drivetrain;

import com.ctre.phoenix.Drive.SensoredTank;
import com.ctre.phoenix.Drive.Styles.Smart;
import com.ctre.phoenix.Mechanical.SensoredGearbox;
import com.ctre.phoenix.MotorControl.SmartMotorController;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
import com.ctre.phoenix.Sensors.PigeonImu;

public class BionicSensoredTankDrive extends BionicSensoredDriveBase {
	private SensoredTank sensoredTank;
	private PigeonImu pigeonImu;
	
	public BionicSensoredTankDrive(
			SensoredTank sensoredTank,
			PigeonImu pigeonImu,
			BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		this.sensoredTank = sensoredTank;
		this.pigeonImu = pigeonImu;
	}
	
	public BionicSensoredTankDrive(
			SensoredGearbox drivetrainLeftGearbox, SensoredGearbox drivetrainRightGearbox, 
			float wheelRadius, float drivetrainWidth,
			PigeonImu pigeonImu,
			BionicDriveOIConstants driveOIConstants) {
		this(new SensoredTank(drivetrainLeftGearbox, drivetrainRightGearbox, false, true, wheelRadius, drivetrainWidth), pigeonImu, driveOIConstants);
	}

	public BionicSensoredTankDrive(
			TalonSRX drivetrainLeftMotor,
			TalonSRX drivetrainRightMotor,
			SmartMotorController.FeedbackDevice feedbackDevice, 
			float wheelRadius, float drivetrainWidth,
			PigeonImu pigeonImu,
			float unitsPerRevolution, BionicDriveOIConstants driveOIConstants) {
		this(
			new SensoredGearbox(unitsPerRevolution, drivetrainLeftMotor, feedbackDevice),
			new SensoredGearbox(unitsPerRevolution, drivetrainRightMotor, feedbackDevice),
			wheelRadius, drivetrainWidth,
			pigeonImu,
			driveOIConstants
		);
	}
	
	public BionicSensoredTankDrive(
			TalonSRX drivetrainLeftMotor, TalonSRX drivetrainLeftBackMotor,
			TalonSRX drivetrainRightMotor, TalonSRX drivetrainRightBackMotor, 
			SmartMotorController.FeedbackDevice feedbackDevice, 
			float wheelRadius, float drivetrainWidth,
			PigeonImu pigeonImu,
			float unitsPerRevolution, BionicDriveOIConstants driveOIConstants) {
		this(
			new SensoredGearbox(unitsPerRevolution, drivetrainLeftMotor, drivetrainLeftBackMotor, feedbackDevice),
			new SensoredGearbox(unitsPerRevolution, drivetrainRightMotor, drivetrainRightBackMotor, feedbackDevice),
			wheelRadius, drivetrainWidth,
			pigeonImu,
			driveOIConstants
		);
	}

	
	public void arcadeDriveDirect(double speed, double rotation) {
		arcadeDriveDirect((float) speed, (float) rotation);
	}
	
	public void arcadeDriveDirect(float speed, float rotation) {
		 Math.copySign(speed, rotation);
		 
		 sensoredTank.set(Smart.PercentOutput, speed, rotation);
	}
}
