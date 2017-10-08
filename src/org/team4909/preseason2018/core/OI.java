package org.team4909.preseason2018.core;

import org.team4909.bionic.utils.commands.SetVoltage;
import org.team4909.bionic.utils.oi.BionicAxis;
import org.team4909.bionic.utils.oi.BionicF310;

public class OI {
	// Driver Gamepad
	public final BionicF310 driverGamepad;
	public final BionicAxis driverGamepadSpeedAxis;
	public final BionicAxis driverGamepadRotAxis;

	// Manipulator Gamepad
	public final BionicF310 manipulatorGamepad;
	
	public OI() {
		driverGamepad = new BionicF310(0);
		driverGamepadSpeedAxis = BionicF310.LY;
		driverGamepadRotAxis = BionicF310.RX;
		
		manipulatorGamepad = new BionicF310(1);
		manipulatorGamepad.buttonHeld(BionicF310.LT, new SetVoltage(Robot.agitator, Robot.agitator.feedShooter));
	}
}
