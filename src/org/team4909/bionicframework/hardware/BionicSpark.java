package org.team4909.bionicframework.hardware;

import edu.wpi.first.wpilibj.Spark;

import org.team4909.bionicframework.utils.Commandable;

/**
 * Wrapper Class for WPI Sparks implementing BionicFramework Commandables
 */
public class BionicSpark extends Spark {
	/**
	 * @param channel PWM Channel
	 */
	public BionicSpark(int channel) {
		super(channel);
	}

	/**
	 * @param setpoint The percent output value between [-1,1] to set
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable setPercentOutput(double setpoint) {
		return new Set(setpoint);
	}
	
	private class Set extends Commandable {
		private double setpoint;
		
		public Set(double setpoint) {
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			set(setpoint);
		}
	}
}
