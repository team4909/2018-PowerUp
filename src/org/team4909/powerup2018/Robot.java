package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.team4909.bionicframework.devices.Arduino;
import org.team4909.bionicframework.devices.BionicSRX;
import org.team4909.bionicframework.devices.BionicSolenoid;
import org.team4909.bionicframework.devices.BionicSpeedController;
import org.team4909.bionicframework.devices.PotentiometerController;
import org.team4909.bionicframework.devices.RoboRio;
import org.team4909.bionicframework.devices.Arduino.State;
import org.team4909.bionicframework.oi.BionicF310;

public class Robot extends RoboRio {
	/* Subsystem Initialization */
	private static Arduino arduino;
	private static BionicSolenoid flag;
	private static BionicSpeedController feeder;
	private static BionicSRX shooter;
	private static PotentiometerController loader;
	// private static BionicDrive drivetrain;
	
	/* OI Initialization */
	private static BionicF310 driverGamepad;
	
	@Override
	protected void subsystemInit() {
		arduino = new Arduino(4);
		
		flag = new BionicSolenoid(0, 1);
		feeder = new BionicSpeedController(new Spark(0));
		
		shooter = new BionicSRX(0);
		shooter.setFeedbackDevice(FeedbackDevice.CTRE_MagEncoder_Relative);
		shooter.setPIDF(0.22, 0, 0, 0.1097);
		
		loader = new PotentiometerController(
			new BionicSpeedController(new Spark(1)),
			new AnalogPotentiometer(0),
			0.023, 0, 0 // PID Constants.
		);
		loader.setToleranceDegrees(0.25);
		loader.setMax(0.4);
		
		/*
		 * drivetrain = new BionicDrivetrain(TalonSRX, TalonSRX)
		 * 
		 * drivetrain.addRightSlave(TalonSRX)
		 * drivetrain.addLeftSlave(TalonSRX)
		 * 
		 * drivetrain.setFeedbackDevice(QuadEncoder)
		 * drivetrain.setGyro(new PigeonImu(1)) or drivetrain.setGyro(new AHRS()) 
		 * 
		 * drivetrain.setShiftingSolenoid(SingleSolenoid)
		 */
	}

	@Override
	protected void mapAutos(SendableChooser<Command> autonomousPicker) {
		// autonomousPicker.addDefault("Do Nothing", null);
		// autonomousPicker.addObject("Sample Auto. Command", new Command());
	}

	@Override
	protected void oiInit() {
		driverGamepad = new BionicF310(0);
		
		// drivetrain.setSpeedAxis(oi.driverGamepad, BionicF310.LY, 1.0)
		// drivetrain.setRotationAxis(oi.driverGamepad, BionicF310.RX, 1.0)
		
		driverGamepad.buttonPressed(BionicF310.B, Robot.flag.setState(Value.kForward));
		driverGamepad.buttonPressed(BionicF310.X, Robot.flag.setState(Value.kForward));
		
		driverGamepad.buttonPressed(BionicF310.LT, 0.5, Robot.feeder.set(1.0));

		driverGamepad.buttonPressed(BionicF310.RT, 0.5, Robot.shooter.set(ControlMode.Velocity, 3400.0));
		driverGamepad.buttonPressed(BionicF310.A, Robot.loader.setPosition(75.2));
	}

	@Override
	protected void robotEnabled() {
		arduino.sendSignal(State.enabled);	
	}

	@Override
	protected void robotDisabled() {
		arduino.sendSignal(State.disabled);
	}
}
