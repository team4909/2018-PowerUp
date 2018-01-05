package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class BionicSRX {
	private TalonSRX speedController;
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
	
	public void setDirect(ControlMode mode, double setpoint) {
		speedController.set(mode, setpoint);
	}
	
	public Command set(ControlMode mode, double setpoint) {
		return new Set(mode, setpoint);
	}
	
	private class Set extends InstantCommand {
		private ControlMode mode;
		private double setpoint;
		
		public Set(ControlMode mode, double setpoint) {
			this.mode = mode;
			this.setpoint = setpoint;
		}
		
		public void initialize() {
			setDirect(mode, setpoint);
		}
	}
}
