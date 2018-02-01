package org.team4909.bionicframework.hardware.core;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Generic roboRio Utilities to Abstract the Robot into Distinguishable Parts 
 */
public abstract class RoboRio extends TimedRobot {
	/**
	 * Called from Robot Periodic to Separate all Dashboard Logging
	 */
	protected void dashboardPeriodic() {};

	/**
	 * Called from Auto/Teleop Init
	 */
	protected void robotEnabled() {};
	
	/**
	 * Called from Disabled Init
	 */
	protected void robotDisabled() {};

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();

		dashboardPeriodic();
	}

	@Override
	public void autonomousInit() {
		robotEnabled();
	}
	
	@Override
	public void teleopInit() {
		robotEnabled();
	}
	
	@Override
	public void disabledInit() {
		robotDisabled();
	}
}
