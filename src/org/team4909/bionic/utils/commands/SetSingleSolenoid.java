package org.team4909.bionic.utils.commands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetSingleSolenoid extends InstantCommand  {
	Solenoid solenoid;
	boolean setpoint;
	
	public SetSingleSolenoid(Solenoid solenoid, boolean setpoint) {
		this.solenoid = solenoid;
		
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		solenoid.set(setpoint);
	}
}
