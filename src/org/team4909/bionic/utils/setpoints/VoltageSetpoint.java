package org.team4909.bionic.utils.setpoints;

public class VoltageSetpoint {
	private double setpoint;
	
	public VoltageSetpoint(double setpoint){
		this.setpoint = setpoint;	
	}

	public double get(){
		return setpoint;
	}	
}