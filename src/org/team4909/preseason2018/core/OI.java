package org.team4909.preseason2018.core;

import org.team4909.bionic.utils.commands.SetDoubleSolenoid;
import org.team4909.bionic.utils.commands.SetPosition;
import org.team4909.bionic.utils.commands.SetVelocity;
import org.team4909.bionic.utils.commands.SetVoltage;
import org.team4909.bionic.utils.oi.BionicAxis;
import org.team4909.bionic.utils.oi.BionicF310;

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
		driverGamepad.buttonPressed(BionicF310.A, new SetPosition(Robot.gearLoader.positionA));
		driverGamepad.buttonPressed(BionicF310.Y, new SetPosition(Robot.gearLoader.positionB));
		
		driverGamepad.buttonPressed(BionicF310.RT, new SetVelocity(Robot.shooter.fullSpeed));
		driverGamepad.buttonPressed(BionicF310.LT, new SetVoltage(Robot.shooter.fullVoltage));
		
		driverGamepad.buttonPressed(BionicF310.B, new SetDoubleSolenoid(Robot.flag, Value.kForward));
		driverGamepad.buttonPressed(BionicF310.X, new SetDoubleSolenoid(Robot.flag, Value.kReverse));
	}
}
