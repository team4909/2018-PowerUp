package org.team4909.preseason2018.core;

import org.team4909.bionic.utils.commands.SetDoubleSolenoid;
import org.team4909.bionic.utils.commands.SetDriveDirection;
import org.team4909.bionic.utils.oi.BionicAxis;
import org.team4909.bionic.utils.oi.BionicF310;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Direction;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class OI {
	public final BionicF310 driverGamepad;
	public final BionicAxis driverGamepadDriveSpeedAxis;
	public final BionicAxis driverGamepadDriveRotationAxis;

	public OI() {
		driverGamepad = new BionicF310(0);
		driverGamepadDriveSpeedAxis = BionicF310.LY;
		driverGamepadDriveRotationAxis = BionicF310.RX;	
	}
	
	public void initButtons() {
		driverGamepad.buttonPressed(BionicF310.A, new SetDriveDirection(Robot.drivetrain, Direction.Forward));
		driverGamepad.buttonPressed(BionicF310.Y, new SetDriveDirection(Robot.drivetrain, Direction.Reverse));	
		driverGamepad.buttonPressed(BionicF310.B, new SetDoubleSolenoid(Robot.flag, Value.kForward));
		driverGamepad.buttonPressed(BionicF310.X, new SetDoubleSolenoid(Robot.flag, Value.kReverse));
	}
}
