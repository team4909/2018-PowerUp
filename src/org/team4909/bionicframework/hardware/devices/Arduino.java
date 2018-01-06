package org.team4909.bionicframework.hardware.devices;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class Arduino {
	 public static enum State {
		 disabled(7),
		 enabled(6);
		 
		 public final int signal;
		 State(int signal) {
			 this.signal = signal;
		 }
	}
	 
	private I2C i2c;
	
	public Arduino(int address) {
		i2c = new I2C(I2C.Port.kOnboard, address);
	}
	
	public void sendSignal(State state) {
		byte[] toSend = { (byte) state.signal };
		i2c.transaction(toSend, 1, null, 0);
	}
	
	public Command send(State state) {
		return new SendCommand(state);
	}
	
	private class SendCommand extends InstantCommand {
		State state;
		
		public SendCommand(State state){
			this.state = state;
		}
		
		protected void initialize(){
			sendSignal(state);
		}
	}
}