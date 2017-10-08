package org.team4909.bionic.utils.setpoints;

import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

public class VoltageSetpoint {
	private BionicVoltageSubsystem voltageSubsystem;
	private double setpoint;
	
	public VoltageSetpoint(BionicVoltageSubsystem voltageSubsystem, double setpoint){
		this.voltageSubsystem = voltageSubsystem;
		this.setpoint = setpoint;
	}

	public BionicVoltageSubsystem getSubsystem(){
		return voltageSubsystem;
	}
	
	public double getValue(){
		return setpoint;
	}	
}