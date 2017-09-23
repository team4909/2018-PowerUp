package org.team4909.preseason2018.core;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.team4909.bionic.utils.core.BionicRobot;
import org.team4909.bionic.utils.subsystem.BionicDrivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotMap {
	/*
	 * To Instantiate Basic Drivetrain 
	 */
	public final BionicDrivetrain drivetrain = new BionicDrivetrain(
		new VictorSP(0), // Drive Left Motor
		new Encoder(0, 1, true, EncodingType.k4X), // Drive Left Encoder

		new VictorSP(1),  // Drive Right Motor
		new Encoder(2, 3, true, EncodingType.k4X), // Drive Right Encoder
		
		1.0, // Distance per Encoder Pulse - Wheel Circumference / Pulses per Revolution - Allows for Encoder.getDistance(); on Output Shaft
		
		BionicRobot.oi.driverGamepad, BionicRobot.oi.driverGamepadSpeedAxis, 	// Move Stick/Axis
		BionicRobot.oi.driverGamepad, BionicRobot.oi.driverGamepadRotAxis 	// Rotate Stick/Axis
	);
	
	/* 
	 * To Create Compressor Object (to see pressure feedback)
	 * public final Compressor compressor = new Compressor();
	 */
}
