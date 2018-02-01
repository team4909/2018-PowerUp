package org.team4909.bionicframework.hardware.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

import org.team4909.bionicframework.interfaces.Commandable;

/**
 * Wrapper Class for WPI Single and Double Solenoids implementing BionicFramework Commandables
 */
public class BionicSolenoid {
	private Solenoid singleSolenoid;
	private DoubleSolenoid doubleSolenoid;
	
	/**
	 * Connect to Single Solenoid via PCM
	 * 
	 * @param channel PCM Channel
	 */
	public BionicSolenoid(int channel) {
		singleSolenoid = new Solenoid(channel);
	}
	
	/**
	 * Connect to Double Solenoid via PCM
	 * 
	 * @param forwardChannel Forward PCM Channel
	 * @param reverseChannel Reverse PCM Channel
	 */
	public BionicSolenoid(int forwardChannel, int reverseChannel) {
		doubleSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
	}

	/**
	 * @param value State to set Solenoid to
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable setState(DoubleSolenoid.Value value) {
		return new SetSolenoid(value);
	}
	
	private class SetSolenoid extends Commandable  {
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
