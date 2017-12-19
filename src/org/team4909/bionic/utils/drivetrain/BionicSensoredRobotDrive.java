package org.team4909.bionic.utils.drivetrain;

import com.ctre.phoenix.Drive.ISmartDrivetrain;
import com.ctre.phoenix.Drive.Styles.Smart;
import com.ctre.phoenix.Sensors.PigeonImu;

public abstract class BionicSensoredRobotDrive extends BionicDriveBase {
	private ISmartDrivetrain smartDrivetrain;
	private PigeonImu pigeonImu;
	
	public BionicSensoredRobotDrive(ISmartDrivetrain smartDrivetrain, PigeonImu pigeonImu, BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		this.smartDrivetrain = smartDrivetrain;
		this.pigeonImu = pigeonImu;
	}
	
	public void arcadeDriveDirect(double speed, double rotation) {
		arcadeDriveDirect((float) speed, (float) rotation);
	}
	
	public void arcadeDriveDirect(float speed, float rotation) {
		 Math.copySign(speed, rotation);
		 
		 smartDrivetrain.set(Smart.PercentOutput, speed, rotation);
	}
}
