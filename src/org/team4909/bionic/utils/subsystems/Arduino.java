package org.team4909.bionic.utils.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arduino extends Subsystem{
	static I2C i2c;
	byte[] toSend = new byte[1];

	protected void initDefaultCommand() {}
	
	// This class creates a new Arduino on the given port. The Arduino should
	// be set up as a slave device on this same port. 
	public void start(int address) {
		i2c = new I2C(I2C.Port.kOnboard, address);
	}
	
	// SendData sends the byte to the Arduino, and expects no response. The 
	// Arduino then takes the number and runs it through a switch to figure 
	// out what to make the lights do.
	public void sendData(int data) {
		toSend[0] = (byte) data;
		i2c.transaction(toSend, 1, null, 0);
	}
}
