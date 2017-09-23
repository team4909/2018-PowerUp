package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.core.BionicRobot;
import org.team4909.bionic.utils.subsystem.BionicDrivetrain;

public class BionicDriveCommand extends BionicCommand {
	public BionicDriveCommand(BionicDrivetrain drivetrain) {
		requires(drivetrain);
	}
	
	protected void execute() {
		BionicRobot.robotMap.drivetrain.Drive();
    }

	protected boolean isFinished() {
		return false;
	}
}
