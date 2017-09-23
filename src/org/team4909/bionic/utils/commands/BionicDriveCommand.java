package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.subsystem.BionicDrivetrain;

public class BionicDriveCommand extends BionicCommand {
	private BionicDrivetrain drivetrainSubsystem;
	
	public BionicDriveCommand(BionicDrivetrain drivetrain) {
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;
	}
	
	protected void execute() {
		drivetrainSubsystem.Drive();
    }

	protected boolean isFinished() {
		return false;
	}
}
