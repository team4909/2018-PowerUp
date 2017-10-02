package org.team4909.bionic.utils.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class BionicJoystick extends Joystick {
	private final double defaultAxisThreshold = 0.15;
	private final double defaultAxisButtonThreshold = 0.5;
	
	public BionicJoystick(int port) {
		super(port);
	}
	
	public double getThresholdAxis(BionicAxis axis){
		return getThresholdAxis(axis, defaultAxisThreshold);
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
	
	public void buttonPressed(BionicAxis axis, Command command)	{
		buttonPressed(axis, command, defaultAxisButtonThreshold);
	}
	
	public void buttonPressed(BionicAxis axis, Command command, double threshold)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.whenActive(command);
	}
	
	public void buttonHeld(BionicButton button, Command command){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.whileHeld(command);
	}

	public void buttonHeld(BionicAxis axis, Command command)	{
		buttonHeld(axis, command, defaultAxisButtonThreshold);
	}
	
	public void buttonHeld(BionicAxis axis, Command command, double threshold)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.whileActive(command);
	}
	
	public void buttonToggled(BionicButton button, Command command){
		JoystickButton newButton = new JoystickButton(this, button.getNumber());
		
		newButton.toggleWhenPressed(command);
	}

	public void buttonToggled(BionicAxis axis, Command command)	{
		buttonToggled(axis, command, defaultAxisButtonThreshold);
	}
	
	public void buttonToggled(BionicAxis axis, Command command, double threshold)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis.getNumber(), threshold);
		
		newButton.toggleWhenActive(command);
	}
}
