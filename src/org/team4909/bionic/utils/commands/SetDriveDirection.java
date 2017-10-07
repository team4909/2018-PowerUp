package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Direction;

public class SetDriveDirection extends BionicCommand {
	private BionicDrivetrain drivetrainSubsystem;
	private Direction direction;
	
	public SetDriveDirection(BionicDrivetrain drivetrain, Direction direction) {
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;
		this.direction = direction;
	}
	
	protected void execute() {
		drivetrainSubsystem.driveDirection = direction;
    }

	protected boolean isFinished() {
		return true;
	}
}
