package org.team4909.preseason2018.core;

import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.subsystems.Arduino;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain;
import org.team4909.preseason2018.autonomous.AutonomousMap;
import org.team4909.preseason2018.subsystems.Shooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	public static OI oi;
	public static AutonomousMap autonomousMap;

	public static BionicDrivetrain drivetrain;
	public static DoubleSolenoid flag;

	public static Arduino arduino;

	public static Shooter shooter;

	private void subsystemInit() {
		drivetrain = new BionicDrivetrain(
			new Spark(0),
			new Spark(1),
			-1.0,
			oi.driverGamepad, oi.driverGamepadDriveSpeedAxis,
			oi.driverGamepad, oi.driverGamepadDriveRotationAxis
		);

		flag = new DoubleSolenoid(0, 1);

		arduino = new Arduino(4);

		shooter = new Shooter(new CANTalon(5), new PIDConstants(0.004, 0, 0), 0.028);
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

	@Override public void autonomousInit() {
		autonomousMap.startCommand();

		arduino.sendData(LED.enabled);
	}

	@Override public void teleopInit() {
		autonomousMap.endCommand();

		arduino.sendData(LED.enabled);
	}

	@Override public void disabledInit() { arduino.sendData(LED.disabled); }

	@Override public void disabledPeriodic() { Scheduler.getInstance().run(); }
	@Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
	@Override public void teleopPeriodic() { Scheduler.getInstance().run(); }
}
