package org.team4909.bionic.utils.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class BionicJoystick extends Joystick {
	private final double defaultThreshold = 0.5;
	
	public BionicJoystick(int port) {
		super(port);
	}
	
	public double getThresholdAxis(int axis){
		return getThresholdAxis(axis, 0.15);
	}
	
	public double getThresholdAxis(int axis, double deadzone){
		if(Math.abs(this.getRawAxis(axis)) > Math.abs(deadzone))
			return this.getRawAxis(axis);
		else
			return 0.0;
	}
	
	public void buttonPressed(int button, Command command){
		JoystickButton newButton = new JoystickButton(this, button);
		
		newButton.whenPressed(command);
	}
	
	public void axisButtonPressed(int axis, Command command)	{
		axisButtonPressed(axis, command, defaultThreshold);
	}
	
	public void axisButtonPressed(int axis, Command command, double threshold)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis, threshold);
		
		newButton.whenActive(command);
	}
	
	public void buttonHeld(int button, Command command){
		JoystickButton newButton = new JoystickButton(this, button);
		
		newButton.whileHeld(command);
	}

	public void axisButtonHeld(int axis, Command command)	{
		axisButtonHeld(axis, command, defaultThreshold);
	}
	
	public void axisButtonHeld(int axis, Command command, double threshold)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis, threshold);
		
		newButton.whileActive(command);
	}
	
	public void buttonToggled(int button, Command command){
		JoystickButton newButton = new JoystickButton(this, button);
		
		newButton.toggleWhenPressed(command);
	}

	public void axisButtonToggled(int axis, Command command)	{
		axisButtonToggled(axis, command, defaultThreshold);
	}
	
	public void axisButtonToggled(int axis, Command command, double threshold)	{
		BionicJoystickAxisButton newButton = new BionicJoystickAxisButton(this, axis, threshold);
		
		newButton.toggleWhenActive(command);
	}
}
