package org.team4909.bionic.utils.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.team4909.bionic.utils.drivetrain.BionicDrivetrain;

public class DriveOI extends Command {
	private BionicDrivetrain drivetrainSubsystem;
	
	public DriveOI(BionicDrivetrain bionicDrivetrain) {
		requires(bionicDrivetrain);
		
		drivetrainSubsystem = bionicDrivetrain;
	}
	
	protected void execute() {
		drivetrainSubsystem.arcadeDriveScaled();
    }

	protected boolean isFinished() {
		return false;
	}
}
