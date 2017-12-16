package org.team4909.bionic.utils.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetDoubleSolenoid extends InstantCommand  {
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
