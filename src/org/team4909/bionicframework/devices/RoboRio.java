package org.team4909.bionicframework.devices;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class RoboRio extends TimedRobot {
	private SendableChooser<Command> autonomousPicker = new SendableChooser<>();
	private Command autonomousCommand;
	
	protected abstract void subsystemInit();
	
	protected void mapAutos(SendableChooser<Command> autonomousPicker) {};
	
	protected abstract void oiInit();
	
	protected void dashboardPeriodic() {};

	protected void robotEnabled() {};
	protected void robotDisabled() {};

	@Override
	public void robotInit() {
		subsystemInit();

		oiInit();
		
		mapAutos(autonomousPicker);
		
		SmartDashboard.putData("Auto. Command", autonomousPicker);
	}
	
	@Override
	public void robotPeriodic() {
		dashboardPeriodic();
	}

	@Override
	public void autonomousInit() { 
		autonomousCommand = autonomousPicker.getSelected();

		if (autonomousCommand != null)
			autonomousCommand.start();

		robotEnabled();
	}
	
	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		robotEnabled();
	}
	
	@Override
	public void disabledInit() {
		robotDisabled();
	}
}
