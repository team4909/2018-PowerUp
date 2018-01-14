package org.team4909.bionicframework.hardware.devices;

import edu.wpi.first.wpilibj.TimedRobot;

public abstract class RoboRio extends TimedRobot {
	protected void dashboardPeriodic() {};

	protected void robotEnabled() {};
	protected void robotDisabled() {};
	
	@Override
	public void robotPeriodic() {
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
