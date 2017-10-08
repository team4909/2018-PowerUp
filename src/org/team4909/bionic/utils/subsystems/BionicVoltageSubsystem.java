package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.setpoints.VoltageSetpoint;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BionicVoltageSubsystem extends Subsystem {
	public final VoltageSetpoint stop = new VoltageSetpoint(0);
	
	public SpeedController speedController;
	
	public BionicVoltageSubsystem(SpeedController speedController) {
		this.speedController = speedController;
	}
	
	public BionicVoltageSubsystem(SpeedController speedController, boolean inverted) {
		this(speedController);
		
		this.speedController.setInverted(inverted);
	}

	// No Default Voltage
	protected void initDefaultCommand() {}
	
	public void set(VoltageSetpoint setpoint) {
		speedController.set(setpoint.get());
	}
}
