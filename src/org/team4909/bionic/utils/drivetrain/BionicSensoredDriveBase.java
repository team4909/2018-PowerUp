package org.team4909.bionic.utils.drivetrain;

import com.ctre.phoenix.Sensors.PigeonImu;

public abstract class BionicSensoredDriveBase extends BionicDriveBase {
	private PigeonImu pigeonImu;
	
	public BionicSensoredDriveBase(PigeonImu pigeonImu, BionicDriveOIConstants driveOIConstants) {
		super(driveOIConstants);
		
		this.pigeonImu = pigeonImu;
	}
}
