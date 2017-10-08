package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;
import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

public class SetVoltage extends BionicCommand {
	private BionicVoltageSubsystem voltageSubsystem;
	private VoltageSetpoint setpoint;
	
	public SetVoltage(BionicVoltageSubsystem voltageSubsystem, VoltageSetpoint setpoint) {
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		voltageSubsystem.set(setpoint);
	}
	
	public void execute() {
		voltageSubsystem.set(setpoint);
	}
}