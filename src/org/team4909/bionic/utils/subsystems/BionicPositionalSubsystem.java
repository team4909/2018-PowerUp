package org.team4909.bionic.utils.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.setpoints.PositionSetpoint;

public class BionicPositionalSubsystem extends PIDSubsystem {
	private SpeedController motor;
	private AnalogPotentiometer potentiometer;

	public BionicPositionalSubsystem(SpeedController motor, AnalogPotentiometer potentiometer, PIDConstants pid) {
		super(pid.p, pid.i, pid.d);

		this.motor = motor;
		this.potentiometer = potentiometer;

		getPIDController().setContinuous(false);
		getPIDController().setAbsoluteTolerance(0.25); // 1/4 Degree Should be Fine for Our Use Case
		getPIDController().enable();
	}

	protected void initDefaultCommand() {}

	protected double returnPIDInput() {
		return getAngle();
	}

	protected void usePIDOutput(double output) {
		motor.set(output);
	}

	public void set(PositionSetpoint setpoint) {
		setSetpoint(setpoint.getValue());
	}

	public double getAngle() {
		return potentiometer.get();
	}
}