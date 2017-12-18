package org.team4909.bionic.utils.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

import org.team4909.bionic.utils.drivetrain.BionicDrivetrain;
import org.team4909.bionic.utils.drivetrain.BionicDrivetrain.Direction;

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
