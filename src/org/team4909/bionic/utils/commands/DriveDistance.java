package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.subsystem.BionicDrivetrain;

public class DriveDistance extends BionicCommand {
	private BionicDrivetrain drivetrainSubsystem;
	private double distance;
	
	public DriveDistance(BionicDrivetrain drivetrain, double distance) {
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;
		this.distance = distance;
	}
	
	protected void execute() {
		double leftValue = 0;
		double rightValue = 0;
		
		drivetrainSubsystem.driveAutoTank(leftValue, rightValue);
    }

	protected boolean isFinished() {
		return true;
	}
}
