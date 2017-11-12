package org.team4909.bionic.utils.setpoints;

import org.team4909.bionic.utils.subsystems.BionicVelocitySubsystem;

public class VelocitySetpoint {
	private double setpoint;
	private BionicVelocitySubsystem velocitySubsystem;
	
	public VelocitySetpoint(BionicVelocitySubsystem velocitySubsystem, double setpoint){
		this.velocitySubsystem = velocitySubsystem;
		this.setpoint = setpoint;
	}

	public BionicVelocitySubsystem getSubsystem(){
		return velocitySubsystem;
	}
	
	public double getValue(){
		return setpoint;
	}	
}