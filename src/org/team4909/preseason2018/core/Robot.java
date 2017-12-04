package org.team4909.preseason2018.core;

import org.team4909.preseason2018.autonomous.AutonomousMap;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	public static OI oi;
	public static AutonomousMap autonomousMap;
	
	// TODO:  Create Drivetrain

	// TODO:  Create Pneumatics

	private void subsystemInit() {
		// TODO: Initialize Drivetrain
		
		// TODO: Initialize Pneumatics
	}
	
	@Override
	public void robotInit() {
		// Initialize Operator Input
		oi = new OI();
		
		// Initialize Subsystems
		subsystemInit();

		// Initialize Commands
		oi.initButtons();
		autonomousMap = new AutonomousMap();
		
		// Initialize Dashboard
		DashboardConfig.init();
	}

	@Override public void autonomousInit() { autonomousMap.startCommand(); }
	@Override public void teleopInit() { autonomousMap.endCommand(); }

	@Override public void disabledPeriodic() { Scheduler.getInstance().run(); }
	@Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	@Override public void teleopPeriodic() { Scheduler.getInstance().run(); }
}
