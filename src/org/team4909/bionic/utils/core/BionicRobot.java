package org.team4909.bionic.utils.core;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team4909.preseason2018.autonomous.AutonomousMap;
import org.team4909.preseason2018.core.DashboardConfig;
import org.team4909.preseason2018.core.OI;
import org.team4909.preseason2018.core.RobotMap;

public class BionicRobot extends IterativeRobot {
	public static RobotMap robotMap;
	public static OI oi;
	public static AutonomousMap autonomousMap;

	@Override
	public void robotInit() {
		robotMap = new RobotMap();
		oi = new OI();
		autonomousMap = new AutonomousMap();
		
		SmartDashboard.putData("Auto. Command", autonomousMap.getPicker());
		
		DashboardConfig.init();
	}

	@Override public void autonomousInit() { autonomousMap.startCommand(); }
	@Override public void teleopInit() { autonomousMap.endCommand(); }

	@Override public void disabledPeriodic() { Scheduler.getInstance().run(); }
	@Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	@Override public void teleopPeriodic() { Scheduler.getInstance().run(); }
}
