package org.team4909.bionic.utils.oi;

public class BionicAxisHandle {
	BionicJoystick joystick;
	BionicAxis axis;
	
	public BionicAxisHandle(BionicJoystick joystick, BionicAxis axis) {
		this.joystick = joystick;
		this.axis = axis;
	}
	
	public double getValue() {
		return joystick.getRawAxis(axis.getNumber());
	}
}
