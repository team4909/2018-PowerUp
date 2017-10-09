package org.team4909.bionic.utils.setpoints;

public class PositionSetpoint {
	private double setpoint;
	
	public PositionSetpoint(double setpoint){
		this.setpoint = setpoint;	
	}

	public double get(){
		return setpoint;
	}	
}