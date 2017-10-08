package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetVoltage extends InstantCommand {
	private BionicVoltageSubsystem voltageSubsystem;
	private VoltageSetpoint setpoint;
	
	public SetVoltage(BionicVoltageSubsystem voltageSubsystem, VoltageSetpoint setpoint) {
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		voltageSubsystem.set(setpoint);
	}
}