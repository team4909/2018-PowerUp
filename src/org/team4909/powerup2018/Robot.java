package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.team4909.bionicframework.hardware.devices.Arduino;
import org.team4909.bionicframework.hardware.devices.RoboRio;
import org.team4909.bionicframework.hardware.devices.Arduino.State;
import org.team4909.bionicframework.hardware.devices.BionicDrive;
import org.team4909.bionicframework.hardware.devices.BionicPigeon;
import org.team4909.bionicframework.oi.BionicF310;

public class Robot extends RoboRio {
	/* Subsystem Initialization */
	private static Arduino arduino;
	private static BionicDrive drivetrain;
	
	/* OI Initialization */
	private static BionicF310 driverGamepad;
	
	@Override
	public void robotInit() {
		driverGamepad = new BionicF310(0);
		
		arduino = new Arduino(4);
		
		drivetrain = new BionicDrive(6,5,
				driverGamepad, BionicF310.RY, driverGamepad, BionicF310.LX, 
				FeedbackDevice.QuadEncoder, 0, 0, 0, 0,
				new BionicPigeon(1), 0,
				null, 24.43);
//		drivetrain.addFollowers(3,4);
	}

	@Override
	protected void robotEnabled() {
		arduino.sendSignal(State.enabled);	
	}

	@Override
	protected void robotDisabled() {
		arduino.sendSignal(State.disabled);
	}
}
