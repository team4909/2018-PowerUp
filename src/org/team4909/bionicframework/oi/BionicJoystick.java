package org.team4909.bionicframework.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

public class BionicJoystick extends Joystick {
	public BionicJoystick(int port) {
		super(port);
	}
	
	public double getThresholdAxis(BionicAxis axis, double deadzone){
		if(Math.abs(this.getRawAxis(axis.getNumber())) > Math.abs(deadzone))
			return this.getRawAxis(axis.getNumber());
		else
			return 0.0;
	}
	
	public void buttonPressed(BionicButton button, Command command){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.whenPressed(command);
	}
	
	public void buttonPressed(BionicAxis axis, double threshold, Command command)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.whenActive(command);
	}
	
	public void buttonHeld(BionicButton button, Command command){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.whileHeld(command);
	}

	public void buttonHeld(BionicAxis axis, double threshold, Command command)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.whileActive(command);
	}
	
	public void buttonToggled(BionicButton button, Command command){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.toggleWhenPressed(command);
	}
	
	public void buttonToggled(BionicAxis axis, double threshold, Command command)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.toggleWhenActive(command);
	}
	
	private class BionicJoystickAxisButton extends Trigger {
		private BionicJoystick inputJoystick;
		private int axisNumber;
		
		private double thresholdValue;
		
		public BionicJoystickAxisButton(BionicJoystick joystick, int axis, double minThreshold) {
			inputJoystick = joystick;
			
			axisNumber = axis;
			
			thresholdValue = minThreshold;
		}
		
		public boolean get() {
			return inputJoystick.getRawAxis(axisNumber) > thresholdValue;
		}
	}
}
