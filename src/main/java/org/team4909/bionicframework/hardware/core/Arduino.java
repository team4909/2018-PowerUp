package org.team4909.bionicframework.hardware.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;

import org.team4909.bionicframework.interfaces.Commandable;

/**
 * Arduino Library for I2C Communications to be used for LEDs and sensors
 */
public class Arduino {
	private I2C i2c;
	
	/**
	 * @param address I2C Address as configured on Arduino
	 */
	public Arduino(int address) {
		i2c = new I2C(I2C.Port.kMXP, address);
	}

	public void sendSignal(int state){
	    byte[] toSend = { (byte) state };
        try {
            i2c.transaction(toSend, 1, null, 0);
        } catch(java.lang.NullPointerException e) {
            DriverStation.reportError("Could not connect to Arduino", false);
        }
	}

	/**
	 * @param state State signal to be sent to the Arduino
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable send(int state) {
		return new SendCommand(state);
	}
	
	private class SendCommand extends Commandable {
        int state;
		
		public SendCommand(int state){
			this.state = state;
		}
		
		public void initialize(){
            sendSignal(state);
		}
	}
}