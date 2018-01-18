package org.team4909.bionicframework.hardware;

import edu.wpi.first.wpilibj.Spark;

import org.team4909.bionicframework.utils.Commandable;

public class BionicSpark extends Spark {
	public BionicSpark(int channel) {
		super(channel);
	}

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
