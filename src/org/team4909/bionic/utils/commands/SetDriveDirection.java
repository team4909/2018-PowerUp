package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.subsystems.BionicDrivetrain;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Direction;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetDriveDirection extends InstantCommand {
	private BionicDrivetrain drivetrainSubsystem;
	private Direction direction;
	
	public SetDriveDirection(BionicDrivetrain drivetrain, Direction direction) {
		drivetrainSubsystem = drivetrain;
		this.direction = direction;
	}
	
	protected void initialize() {
		drivetrainSubsystem.driveDirection = direction;
    }
}
