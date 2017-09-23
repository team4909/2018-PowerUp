package org.team4909.preseason2018.core;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardConfig {
	public static void init() {
		SmartDashboard.putData("Auto. Command", Robot.autonomousMap.getPicker());
	}
}
