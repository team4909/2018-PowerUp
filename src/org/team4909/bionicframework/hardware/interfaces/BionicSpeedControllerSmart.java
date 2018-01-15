package org.team4909.bionicframework.hardware.interfaces;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;

import org.team4909.bionicframework.hardware.devices.BionicSRX;

public interface BionicSpeedControllerSmart extends BionicSpeedController {
	void configSelectedFeedbackSensor(FeedbackDevice feedbackDevice);
	void addFollower(BionicSRX slave);
	
	void configPIDF(double p, double i, double d, double f);

	void set(ControlMode mode, double setpoint);
	Command setMode(ControlMode mode, double setpoint);
}
