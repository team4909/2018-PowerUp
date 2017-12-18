package org.team4909.bionic.utils.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

import org.team4909.bionic.utils.drivetrain.BionicDrivetrain;
import org.team4909.bionic.utils.drivetrain.BionicDrivetrain.Gear;

public class SetDriveGear extends InstantCommand {
	private BionicDrivetrain drivetrainSubsystem;
	private Gear gear;
	
	public SetDriveGear(BionicDrivetrain drivetrain, Gear gear) {
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;
		this.gear = gear;
	}
	
	protected void initialize() {
		drivetrainSubsystem.setGear(gear);
    }
}
