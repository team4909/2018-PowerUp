package org.team4909.bionic.utils.setpoints;

public class VelocitySetpoint {
	private double setpoint;
	
	public VelocitySetpoint(double setpoint){
		this.setpoint = setpoint;	
	}

	public double get(){
		return setpoint;
	}	
}