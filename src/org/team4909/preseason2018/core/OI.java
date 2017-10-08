package org.team4909.preseason2018.core;

import org.team4909.bionic.utils.commands.SetDriveDirection;
import org.team4909.bionic.utils.commands.SetDriveGear;
import org.team4909.bionic.utils.commands.SetVoltage;
import org.team4909.bionic.utils.oi.BionicAxis;
import org.team4909.bionic.utils.oi.BionicF310;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Direction;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Gear;

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
		driverGamepad.buttonPressed(BionicF310.LB, new SetDriveGear(Robot.drivetrain, Gear.Low));
		driverGamepad.buttonPressed(BionicF310.RB, new SetDriveGear(Robot.drivetrain, Gear.High));
		driverGamepad.buttonPressed(BionicF310.Y, new SetDriveDirection(Robot.drivetrain, Direction.Forward));
		driverGamepad.buttonPressed(BionicF310.A, new SetDriveDirection(Robot.drivetrain, Direction.Reverse));
		driverGamepad.buttonHeld(BionicF310.RT, new SetVoltage(Robot.climber, Robot.climber.climb));
		
		manipulatorGamepad = new BionicF310(1);
		manipulatorGamepad.buttonHeld(BionicF310.LT, new SetVoltage(Robot.agitator, Robot.agitator.feedShooter));
	}
}
