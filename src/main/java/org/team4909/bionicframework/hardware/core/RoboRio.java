package org.team4909.bionicframework.hardware.core;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Generic roboRio Utilities to Abstract the Robot into Distinguishable Parts 
 */
public abstract class RoboRio extends TimedRobot {
    protected static Command autoCommand;

    protected void robotEnabled(){}

    @Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
        robotEnabled();

		if (autoCommand != null) {
			autoCommand.start();
		}
	}
	
	@Override
	public void teleopInit() {
        robotEnabled();

		if (autoCommand != null) {
			autoCommand.cancel();
		}
	}
	
	@Override
	public void disabledInit() {
		if (autoCommand != null) {
			autoCommand.cancel();
		}
	}
}
