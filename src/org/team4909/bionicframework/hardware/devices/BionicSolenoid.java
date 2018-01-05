package org.team4909.bionicframework.hardware.devices;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class BionicSolenoid {
	private Solenoid singleSolenoid;
	private DoubleSolenoid doubleSolenoid;
	
	public BionicSolenoid(int channel) {
		singleSolenoid = new Solenoid(channel);
	}
	
	public BionicSolenoid(int forwardChannel, int reverseChannel) {
		doubleSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
	}

	public Command setState(DoubleSolenoid.Value value) {
		return new SetSolenoid(value);
	}
	
	private class SetSolenoid extends InstantCommand  {
		DoubleSolenoid.Value setpoint;
		
		public SetSolenoid(DoubleSolenoid.Value setpoint) {
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			if(singleSolenoid != null) {
				singleSolenoid.set(setpoint == Value.kForward);
			} else {
				doubleSolenoid.set(setpoint);
			}
		}
	}
}
