package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class BionicSRX extends WPI_TalonSRX {
	public BionicSRX(int deviceNumber) {
		super(deviceNumber);
	}
	
	public void addFollower(BionicSRX slave) {
		slave.follow(this);
	}

	/* Commands-based */
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
	
	/* Sensor Feedback */
	private final int pidIdx = 0;
	private final int timeoutMs = 0;
	
	public void configSelectedFeedbackSensor(FeedbackDevice feedbackDevice) {
		this.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
	}
	
	public void configPIDF(double p, double i, double d, double f) {
		this.config_kP(pidIdx, p, timeoutMs);
		this.config_kI(pidIdx, i, timeoutMs);
		this.config_kD(pidIdx, d, timeoutMs);
		this.config_kF(pidIdx, f, timeoutMs);
	}
	
	/* Motion Profiling */
	private final int minBufferPoints = 20;
	private final double bufferInterval = 0.005;
	
	private Notifier processMotionProfileBuffer = new Notifier(this::processMotionProfileBuffer);
	
	public void initMotionProfile(TrajectoryPoint[] points) {
		this.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
		
		this.clearMotionProfileTrajectories();
	
		for(int i = 0; i < points.length; i++) {
			this.pushMotionProfileTrajectory(points[i]);
		}
		
		processMotionProfileBuffer.startPeriodic(bufferInterval);
	}
	
	public void runMotionProfile() {
		MotionProfileStatus srxStatus = this.getMotionProfileStatus();
		
		if(srxStatus.btmBufferCnt >= minBufferPoints) {
			this.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
			
			if(srxStatus.topBufferCnt == 0) {
				processMotionProfileBuffer.stop();	
			}
		}	
	}
	
	public MotionProfileStatus getMotionProfileStatus() {
		MotionProfileStatus srxStatus = new MotionProfileStatus();
		
		this.getMotionProfileStatus(srxStatus);
		
		return srxStatus;
	}
	
	public boolean isMotionProfileFinished() {
		return (this.getMotionProfileStatus()).isLast;
	}
}
