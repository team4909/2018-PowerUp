package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.MotorSafety;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class BionicVelocitySubsystem extends Subsystem {
	public SpeedController speedController;
	
	public BionicVelocitySubsystem(SpeedController speedController) {
		this.speedController = speedController;
		
		((MotorSafety) this.speedController).setSafetyEnabled(true);
	}
	
	public BionicVelocitySubsystem(SpeedController speedController, boolean inverted) {
		this(speedController);
		
		this.speedController.setInverted(inverted);
	}

	// No Default Voltage
	protected void initDefaultCommand() {}
	
	public void set(VelocitySetpoint setpoint) {

		speedController.set(setpoint.getValue());
	}
}
