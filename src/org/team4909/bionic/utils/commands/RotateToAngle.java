package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.subsystem.BionicDrivetrain;

public class RotateToAngle extends BionicCommand {
	private BionicDrivetrain drivetrainSubsystem;
	private int angle;
	
	public RotateToAngle(BionicDrivetrain drivetrain, int angle) {
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;
		this.angle = angle;
	}
	
	protected void execute() {
		double rotateValue = 0;
		
		drivetrainSubsystem.driveAutoArcade(0, rotateValue);
    }

	protected boolean isFinished() {
		return true;
	}
}
