package org.team4909.bionicframework.hardware;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import org.team4909.bionicframework.utils.Commandable;

/**
 * Generic Potentiometer Subsystem
 */
public class PotentiometerController extends PIDSubsystem {
	private SpeedController motor;
	private AnalogPotentiometer potentiometer;
	private double multiplier;

	/**
	 * @param motor Motor Controller for PID Output
	 * @param potentiometer AnalogPotentiometer for Sensor Feedback
	 * @param p Proportional Constant in PID Controller
	 * @param i Integral Constant in PID Controller
	 * @param d Derivative Constant in PID Controller
	 */
	public PotentiometerController(SpeedController motor, AnalogPotentiometer potentiometer, double p, double i, double d) {
		super(p, i, d);

		this.motor = motor;
		this.potentiometer = potentiometer;
		getPIDController().setContinuous(false);

		getPIDController().setAbsoluteTolerance(0);
		getPIDController().enable();
	}
	
	protected void initDefaultCommand() {}

	protected double returnPIDInput() {
		return potentiometer.get();
	}

	protected void usePIDOutput(double output) {
		motor.set(output * multiplier);
	}
	
	/**
	 * @param setpoint The desired degree setpoint
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable setPosition(double setpoint) {
		return new SetPosition(setpoint);
	}
	
	private class SetPosition extends Commandable {
		private double setpoint;
		
		public SetPosition(double setpoint) {
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			setSetpoint(setpoint);
		}
	}
			
	/**
	 * @param multiplier Sets multiplier for PIDOutput, should be [-1,1]
	 */
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
}