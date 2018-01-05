package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;

public interface BionicSpeedControllerSmart extends BionicSpeedController {
	void setFeedbackDevice(FeedbackDevice feedbackDevice);
	void addFollower(int deviceNumber);
	
	void setPIDF(double p, double i, double d, double f);
	
	void set(ControlMode mode, double setpoint);
	Command setMode(ControlMode mode, double setpoint);
}
