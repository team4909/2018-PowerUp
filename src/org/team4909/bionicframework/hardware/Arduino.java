package org.team4909.bionicframework.hardware;

import edu.wpi.first.wpilibj.I2C;

import org.team4909.bionicframework.utils.Commandable;

/**
 * Arduino Library for I2C Communications to be used for LEDs and sensors
 */
public class Arduino {
	 /**
	 * Enum of State Signals to send to an Arduino
	 */
	public static enum State {
		 disabled(7),
		 enabled(6);
		 
		 public final int signal;
		 State(int signal) {
			 this.signal = signal;
		 }
	}
	 
	private I2C i2c;
	
	/**
	 * @param address I2C Address as configured on Arduino
	 */
	public Arduino(int address) {
		i2c = new I2C(I2C.Port.kOnboard, address);
	}
	
	/**
	 * @param state State signal to be sent to the Arduino
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable send(State state) {
		return new SendCommand(state);
	}
	
	private class SendCommand extends Commandable {
		State state;
		
		public SendCommand(State state){
			this.state = state;
		}
		
		public void initialize(){
			byte[] toSend = { (byte) state.signal };
			i2c.transaction(toSend, 1, null, 0);
		}
	}
}