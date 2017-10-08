package org.team4909.preseason2018.core;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.team4909.preseason2018.autonomous.AutonomousMap;
import org.team4909.preseason2018.subsystems.Agitator;
import org.team4909.preseason2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	public static final OI oi = new OI();
	public static AutonomousMap autonomousMap;
	
	public static Drivetrain drivetrain;
	public static Agitator agitator;
	
	@Override
	public void robotInit() {
		autonomousMap = new AutonomousMap();
		
		/*
		 * To Instantiate Basic Drivetrain 
		 */
		drivetrain = new Drivetrain(
			new VictorSP(0), // Drive Left Motor
			new Encoder(0, 1, true, EncodingType.k4X), // Drive Left Encoder
	
			new VictorSP(1), // Drive Right Motor
			new Encoder(2, 3, true, EncodingType.k4X), // Drive Right Encoder
			
			1.0, // Distance per Encoder Pulse - Wheel Circumference / Pulses per Revolution - Allows for Encoder.getDistance(); on Output Shaft
			
			oi.driverGamepad, oi.driverGamepadSpeedAxis,	// Move Stick/Axis
			oi.driverGamepad, oi.driverGamepadRotAxis, 	// Rotate Stick/Axis
			
			new Solenoid(0) // Shifting Solenoid
		);
		
		agitator = new Agitator(new Spark(3), true);
		
		/* 
		 * To Create Compressor Object (to see pressure feedback)
		 * 
		 * Var. Declaration:
		 * 	public static Compressor compressor;
		 * 
		 * In robotInit:
		 * 	compressor = new Compressor();
		 */
		
		/* 
		 * To Create PDP Object (to see voltage/temp/current feedback)
		 * 
		 * Var. Declaration:
		 * 	public static PowerDistributionPanel pdp;
		 * 
		 * In robotInit:
		 * 	pdp = new PowerDistributionPanel();
		 */
		
		DashboardConfig.init();
	}

	@Override public void autonomousInit() { autonomousMap.startCommand(); }
	@Override public void teleopInit() { autonomousMap.endCommand(); }

	@Override public void disabledPeriodic() { Scheduler.getInstance().run(); }
	@Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	@Override public void teleopPeriodic() { Scheduler.getInstance().run(); }
}
