package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.setpoints.PositionSetpoint;
import org.team4909.bionic.utils.subsystems.BionicPositionalSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetPosition extends InstantCommand {
	private BionicPositionalSubsystem positionSubsystem;
	private PositionSetpoint setpoint;
	
	public SetPosition(PositionSetpoint setpoint) {
		this.positionSubsystem = setpoint.getSubsystem();
		
		requires(this.positionSubsystem);
		
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		positionSubsystem.set(setpoint);
	}
	
	public void execute() {
		positionSubsystem.set(setpoint);
	}
}