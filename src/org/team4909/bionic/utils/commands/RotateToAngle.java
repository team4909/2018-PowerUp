package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.subsystem.BionicDrivetrain;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class RotateToAngle extends PIDCommand {
	private BionicDrivetrain drivetrainSubsystem;
	
	public RotateToAngle(BionicDrivetrain drivetrain, PIDConstants constants, int angle) {
		super(constants.p, constants.i, constants.d);
		
		requires(drivetrain);
		
		drivetrainSubsystem = drivetrain;

        getPIDController().setAbsoluteTolerance(0.5);
        getPIDController().setOutputRange(-constants.max, constants.max);
		setSetpoint(angle);
	}
	
	protected double returnPIDInput() {
		return drivetrainSubsystem.getAngle();
	}
	
	protected void usePIDOutput(double output) {
		double rotateValue = 0;
		
		drivetrainSubsystem.driveAutoArcade(0, rotateValue);
	}

	protected boolean isFinished() {
		return getPIDController().onTarget();
	}

}
