package org.team4909.bionic.utils.oi;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class BionicJoystickAxisButton extends Trigger {
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
