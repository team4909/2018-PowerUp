package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class BionicSRX extends WPI_TalonSRX {
	public BionicSRX(int deviceNumber) {
		super(deviceNumber);
	}

	private final int pidIdx = 0;
	private final int timeoutMs = 0;
	
	public void configSelectedFeedbackSensor(FeedbackDevice feedbackDevice) {
		this.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
	}
	
	public void addFollower(BionicSRX slave) {
		slave.follow(this);
	}
	
	public void configPIDF(double p, double i, double d, double f) {
		this.config_kP(pidIdx, p, timeoutMs);
		this.config_kI(pidIdx, i, timeoutMs);
		this.config_kD(pidIdx, d, timeoutMs);
		this.config_kF(pidIdx, f, timeoutMs);
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
}
