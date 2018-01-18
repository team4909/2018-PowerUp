package org.team4909.bionicframework.operator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

import org.team4909.bionicframework.utils.Commandable;

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
	
	public void buttonPressed(BionicButton button, Commandable commandable){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.whenPressed(commandable);
	}
	
	public void buttonPressed(BionicAxis axis, double threshold, Commandable commandable)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.whenActive(commandable);
	}
	
	public void buttonHeld(BionicButton button, Commandable commandable){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.whileHeld(commandable);
	}

	public void buttonHeld(BionicAxis axis, double threshold, Commandable commandable)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.whileActive(commandable);
	}
	
	public void buttonToggled(BionicButton button, Commandable commandable){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.toggleWhenPressed(commandable);
	}
	
	public void buttonToggled(BionicAxis axis, double threshold, Commandable commandable)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.toggleWhenActive(commandable);
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
