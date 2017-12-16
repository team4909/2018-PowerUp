package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.subsystems.BionicDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveOI extends Command {
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
