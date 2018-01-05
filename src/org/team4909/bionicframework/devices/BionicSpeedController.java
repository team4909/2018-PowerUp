package org.team4909.bionicframework.devices;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class BionicSpeedController {
	protected SpeedController speedController;
	
	public BionicSpeedController(SpeedController speedController) {
		this.speedController = speedController;
	}

	public void setDirect(double setpoint) {
		speedController.set(setpoint);
	}
	
	public Command set(double setpoint) {
		return new Set(setpoint);
	}
	
	private class Set extends InstantCommand {
		private double setpoint;
		
		public Set(double setpoint) {
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			set(setpoint);
		}
	}
}
