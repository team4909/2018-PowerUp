package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.core.BionicCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SetDoubleSolenoid extends BionicCommand  {
	DoubleSolenoid solenoid;
	DoubleSolenoid.Value setpoint;
	
	public SetDoubleSolenoid(DoubleSolenoid solenoid, DoubleSolenoid.Value setpoint) {
		this.solenoid = solenoid;
		
		this.setpoint = setpoint;
	}
	
	public void initialize() {
		solenoid.set(setpoint);
	}
}
