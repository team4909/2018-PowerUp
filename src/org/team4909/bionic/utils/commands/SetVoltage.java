package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetVoltage extends InstantCommand {
	private BionicVoltageSubsystem voltageSubsystem;
	private VoltageSetpoint setpoint;
	
	public SetVoltage(VoltageSetpoint setpoint) {
		this.voltageSubsystem = setpoint.getSubsystem();
		
		requires(this.voltageSubsystem);
		
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		voltageSubsystem.set(setpoint);
	}
	
	public void execute() {
		voltageSubsystem.set(setpoint);
	}
}