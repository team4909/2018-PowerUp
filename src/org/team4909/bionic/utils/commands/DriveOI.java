package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain;

public class DriveOI extends BionicCommand {
	private BionicDrivetrain drivetrainSubsystem;
	
	public DriveOI(BionicDrivetrain bionicDrivetrain) {
		requires(bionicDrivetrain);
		
		drivetrainSubsystem = bionicDrivetrain;
	}
	
	protected void execute() {
		drivetrainSubsystem.driveOIArcade();
    }

	protected boolean isFinished() {
		return false;
	}
}
