package org.team4909.bionic.utils.commands;

import org.team4909.bionic.utils.subsystems.Arduino;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ArduinoSend extends InstantCommand {
	private Arduino arduino;
	private int toSend;
	
	// This command is called when a button is pressed, sensor is aligned, 
	// game piece sensed, ect. This class could also be used for other sensors 
	// and such in the future, but would need ArduinoReceive to work.
	public ArduinoSend(Arduino arduino, int data){
		requires(arduino);
		this.toSend = data;
		this.arduino = arduino;
	}
	
	protected void initialize(){ arduino.sendData(toSend); }
	protected boolean isFinished() { return true; }
}
