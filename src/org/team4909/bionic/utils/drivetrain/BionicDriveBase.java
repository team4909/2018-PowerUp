package org.team4909.bionic.utils.drivetrain;

public abstract class BionicDriveBase {
	private BionicDriveOIConstants driveOIConstants;
	
	public BionicDriveBase(BionicDriveOIConstants driveOIConstants) {
		this.driveOIConstants = driveOIConstants;
	}

	abstract public void arcadeDriveDirect(double speed, double rotation);
	
	public void arcadeDriveScaled(double speed, double rotation) {
		arcadeDriveDirect(speed*driveOIConstants.speedFactor, rotation*driveOIConstants.rotationFactor);
	}
}
