package org.team4909.bionic.utils.setpoints;

import org.team4909.bionic.utils.subsystems.BionicPositionalSubsystem;

public class PositionSetpoint {
	private BionicPositionalSubsystem positionalSubsystem;
	private double setpoint;
	
	public PositionSetpoint(BionicPositionalSubsystem positionalSubsystem, double setpoint){
		this.positionalSubsystem = positionalSubsystem;
		this.setpoint = setpoint;	
	}

	public BionicPositionalSubsystem getSubsystem(){
		return positionalSubsystem;
	}
	
	public double getValue(){
		return setpoint;
	}	
}