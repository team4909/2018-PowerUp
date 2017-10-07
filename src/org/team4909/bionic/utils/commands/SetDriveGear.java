package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Gear;

public class SetDriveGear extends BionicCommand {
	private BionicDrivetrain drivetrainSubsystem;
	private Gear gear;
	
	public SetDriveGear(BionicDrivetrain drivetrain, Gear gear) {
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;
		this.gear = gear;
	}
	
	protected void execute() {
		drivetrainSubsystem.setGear(gear);
    }

	protected boolean isFinished() {
		return true;
	}
}
