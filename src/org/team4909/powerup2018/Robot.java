package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.team4909.bionicframework.hardware.devices.Arduino;
import org.team4909.bionicframework.hardware.devices.BionicSRX;
import org.team4909.bionicframework.hardware.devices.BionicSolenoid;
import org.team4909.bionicframework.hardware.devices.BionicSpark;
import org.team4909.bionicframework.hardware.devices.PotentiometerController;
import org.team4909.bionicframework.hardware.devices.RoboRio;
import org.team4909.bionicframework.hardware.devices.Arduino.State;
import org.team4909.bionicframework.hardware.devices.BionicDrive;
import org.team4909.bionicframework.hardware.devices.BionicPigeon;
import org.team4909.bionicframework.oi.BionicF310;

public class Robot extends RoboRio {
	/* Subsystem Initialization */
	private static Arduino arduino;
	private static BionicSolenoid flag;
	private static BionicSpark feeder;
	private static BionicSRX shooter;
	private static PotentiometerController loader;
	private static BionicDrive drivetrain;
	
	/* OI Initialization */
	private static BionicF310 driverGamepad;
	
	@Override
	protected void subsystemInit() {
		arduino = new Arduino(4);
		
		flag = new BionicSolenoid(0, 1);
		feeder = new BionicSpark(0);
		
		shooter = new BionicSRX(0);
		shooter.setFeedbackDevice(FeedbackDevice.CTRE_MagEncoder_Relative);
		shooter.setPIDF(0.22, 0, 0, 0.1097);
		
		loader = new PotentiometerController(
			new BionicSpark(1),
			new AnalogPotentiometer(0),
			0.023, 0, 0 // PID Constants
		);
		loader.setToleranceDegrees(0.25);
		loader.setMax(0.4);
		
		drivetrain = new BionicDrive(1,2);
		drivetrain.addFollowers(3,4);
		drivetrain.setShiftingSolenoid(new BionicSolenoid(2));
		drivetrain.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		drivetrain.setMotorPIDF(0,0,0,0);
		drivetrain.setGyro(new BionicPigeon(1));
		drivetrain.setGyroP(0);
	}

	@Override
	protected void mapAutos() {
		// autonomousPicker.addDefault("Do Nothing", null);
		// autonomousPicker.addObject("Sample Auto. Command", new Command());
	}

	@Override
	protected void oiInit() {
		driverGamepad = new BionicF310(0);
		
		drivetrain.setSpeedAxis(driverGamepad, BionicF310.LY, 1.0);
		drivetrain.setRotationAxis(driverGamepad, BionicF310.RX, 1.0);
		
		driverGamepad.buttonPressed(BionicF310.B, Robot.flag.setState(Value.kForward));
		driverGamepad.buttonPressed(BionicF310.X, Robot.flag.setState(Value.kForward));
		
		driverGamepad.buttonPressed(BionicF310.LT, 0.5, Robot.feeder.setPercentOutput(1.0));

		driverGamepad.buttonPressed(BionicF310.RT, 0.5, Robot.shooter.setMode(ControlMode.Velocity, 3400.0));
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
