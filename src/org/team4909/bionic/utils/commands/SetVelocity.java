package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import org.team4909.bionic.utils.subsystems.BionicVelocitySubsystem;

public class SetVelocity extends BionicCommand {
	private BionicVelocitySubsystem velocitySubsystem;
	private VelocitySetpoint setpoint;
	
	public SetVelocity(VelocitySetpoint setpoint) {
		this.velocitySubsystem = setpoint.getSubsystem();
		
		requires(this.velocitySubsystem);
		
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		velocitySubsystem.set(setpoint);
	}
	
	public void execute() {
		velocitySubsystem.set(setpoint);
	}
}