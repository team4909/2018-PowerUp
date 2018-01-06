package org.team4909.bionicframework.hardware.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

import org.team4909.bionicframework.hardware.interfaces.BionicSpeedController;

public class BionicSRX implements BionicSpeedController {
	private TalonSRX speedController;
	
	private double setpoint;
	
	private ControlMode mode;
	private double maxOutput;
	
	private final int timeoutMs = 0;
	private final int pidIdx = 0;
	
	public BionicSRX(int deviceNumber) {
		this.speedController = new TalonSRX(deviceNumber);
	}
	
	public void setFeedbackDevice(FeedbackDevice feedbackDevice) {
		this.speedController.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
	}
	
	public void addFollower(int deviceNumber) {
		TalonSRX slaveSpeedController = new TalonSRX(deviceNumber);
		
		slaveSpeedController.follow(speedController);
	}
	
	public void setPIDF(double p, double i, double d, double f) {
		speedController.config_kP(pidIdx, p, timeoutMs);
		speedController.config_kI(pidIdx, i, timeoutMs);
		speedController.config_kD(pidIdx, d, timeoutMs);
		speedController.config_kF(pidIdx, f, timeoutMs);
	}
	
	public void setDefaultModePercentOutput() {
		mode = ControlMode.PercentOutput;
		maxOutput = 1.0;
	}

	public void setDefaultModeVelocity(double maxOutput) {	
		mode = ControlMode.Velocity;
		this.maxOutput = maxOutput;
	}
	
	public void set(double setpoint) {
		set(mode, setpoint * maxOutput);
	}
	
	public void set(ControlMode mode, double setpoint) {
		this.setpoint = setpoint;
		
		speedController.set(mode, setpoint);
	}
	
	public Command setPercentOutput(double setpoint) {
		return new SetMode(ControlMode.PercentOutput, setpoint);
	}
	
	public Command setMode(ControlMode mode, double setpoint) {
		return new SetMode(mode, setpoint);
	}
	
	private class SetMode extends InstantCommand {
		private ControlMode mode;
		private double setpoint;
		
		public SetMode(ControlMode mode, double setpoint) {
			this.mode = mode;
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			setMode(mode, setpoint);
		}
	}
	
	@Override
	public double get() {
		return setpoint;
	}

	@Override
	public void setInverted(boolean isInverted) {
		speedController.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return speedController.getInverted();
	}

	@Override
	public void disable() {
		stopMotor();
	}

	@Override
	public void stopMotor() {
		speedController.neutralOutput();
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}
}