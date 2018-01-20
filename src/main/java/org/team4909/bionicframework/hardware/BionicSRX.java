package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Notifier;

import org.team4909.bionicframework.utils.Commandable;

/**
 * Wrapper Class for CTRE WPI_TalonSRX implementing BionicFramework Commandables and Other Simplifications/Abstractions
 */
public class BionicSRX extends WPI_TalonSRX {
	/**
	 * @param deviceNumber - CAN ID's of SRX Master 
	 * @param slaveNumbers - CAN ID's of SRX Followers
	 */
	public BionicSRX(int deviceNumber, int... slaveNumbers) {
		super(deviceNumber);
		
		for(int i = 0; i < slaveNumbers.length; i++) {
			addFollower(slaveNumbers[i]);
		}
	}
	
	private void addFollower(int slave) {
		(new WPI_VictorSPX(slave)).follow(this);
	}

	/* Command-based */
	
	/**
	 * @param setpoint The percent output value between [-1,1] to set
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable setPercentOutput(double setpoint) {
		return new SetMode(ControlMode.PercentOutput, setpoint);
	}
	
	/**
	 * @param mode The control mode to set as found in CTRE documentation
	 * @param setpoint The percent output value between [-1,1] to set
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable setMode(ControlMode mode, double setpoint) {
		return new SetMode(mode, setpoint);
	}
	
	private class SetMode extends Commandable {
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
	
	/**
	 * @param feedbackDevice The feedback sensor device to use for closed-loop as found in CTRE documentation
	 */
	public void configSelectedFeedbackSensor(FeedbackDevice feedbackDevice) {
		this.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
	}
	
	/**
	 * @param p Proportional Constant in PID Controller
	 * @param i Integral Constant in PID Controller
	 * @param d Derivative Constant in PID Controller
	 * @param f Feedforward in PID Controller
	 */
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
	
	/**
	 * @param points Path consisting of waypoints to follow
	 */
	public void initMotionProfile(TrajectoryPoint[] points) {
		this.setSelectedSensorPosition(0,pidIdx,timeoutMs);

		this.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
		
		this.clearMotionProfileTrajectories();
	
		for(int i = 0; i < points.length; i++) {
			this.pushMotionProfileTrajectory(points[i]);
		}
		
		processMotionProfileBuffer.startPeriodic(bufferInterval);
	}
	
	public void runMotionProfile() {
		if(isBottomLevelBufferReady()) {
			this.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
		}	
	}
	
	public void processMotionProfileBuffer() {
		if(isTopLevelBufferEmpty()) {
			processMotionProfileBuffer.stop();	
		} else {	
			super.processMotionProfileBuffer();
		}
	}
	
	private MotionProfileStatus getMotionProfileStatus() {
		MotionProfileStatus srxStatus = new MotionProfileStatus();
		
		this.getMotionProfileStatus(srxStatus);
		
		return srxStatus;
	}
	
	/**
	 * @return Returns whether the streamed motion profile has been completed or not
	 */
	public boolean isMotionProfileFinished() {
		return (this.getMotionProfileStatus()).isLast;
	}
	
	private boolean isTopLevelBufferEmpty() {
		return (this.getMotionProfileStatus()).topBufferCnt == 0;
	}
	
	private boolean isBottomLevelBufferReady() {
		return isTopLevelBufferEmpty() || (this.getMotionProfileStatus()).btmBufferCnt >= minBufferPoints;
	}
}
