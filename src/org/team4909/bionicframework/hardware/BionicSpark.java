package org.team4909.bionicframework.hardware;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class BionicSpark extends Spark {
	public BionicSpark(int channel) {
		super(channel);
	}

	public Command setPercentOutput(double setpoint) {
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
