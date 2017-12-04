package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;

import edu.wpi.first.wpilibj.Solenoid;

public class SetSingleSolenoid extends BionicCommand  {
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
