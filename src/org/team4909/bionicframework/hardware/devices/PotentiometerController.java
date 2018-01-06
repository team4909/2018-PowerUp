package org.team4909.bionicframework.hardware.devices;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PotentiometerController extends PIDSubsystem {
	private SpeedController motor;
	private AnalogPotentiometer potentiometer;
	private double max;

	public PotentiometerController(SpeedController motor, AnalogPotentiometer potentiometer, double p, double i, double d) {
		super(p, i, d);

		this.motor = motor;
		this.potentiometer = potentiometer;

		getPIDController().setContinuous(false);
		
		getPIDController().enable();
	}
	
	protected void initDefaultCommand() {}

	protected double returnPIDInput() {
		return potentiometer.get();
	}

	protected void usePIDOutput(double output) {
		motor.set(output * max);
	}
	
	public Command setPosition(double setpoint) {
		return new SetPosition(setpoint);
	}
	
	private class SetPosition extends InstantCommand {
		private double setpoint;
		
		public SetPosition(double setpoint) {
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			setSetpoint(setpoint);
		}
	}

	public void setToleranceDegrees(double tolerance) {
		getPIDController().setAbsoluteTolerance(tolerance);
	}
			
	public void setMax(double max) {
		this.max = max;
	}
}