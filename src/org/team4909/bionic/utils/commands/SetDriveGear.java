package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.subsystems.BionicDrivetrain;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Gear;

import edu.wpi.first.wpilibj.command.InstantCommand;

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
