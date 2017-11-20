package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import org.team4909.bionic.utils.subsystems.BionicVelocitySubsystem;
import com.ctre.CANTalon;

public class SampleVelocitySubsystem extends BionicVelocitySubsystem {
	public VelocitySetpoint fullSpeed  = new VelocitySetpoint(this, 3500.0);
	
	public SampleVelocitySubsystem(CANTalon speedController, double p, double i, double d, double f) {
		super(speedController, p, i, d, f);
	}
	
	public SampleVelocitySubsystem(CANTalon speedController, double p, double i, double d, double f, boolean inverted) {
		super(speedController, p, i, d, f, inverted);
	}
}
