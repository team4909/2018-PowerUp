package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import org.team4909.bionic.utils.subsystems.BionicVelocitySubsystem;
import com.ctre.CANTalon;

public class SampleVelocitySubsystem extends BionicVelocitySubsystem {
	public VelocitySetpoint velocitySetpoint  = new VelocitySetpoint(this, 1.0);
	
	public SampleVelocitySubsystem(CANTalon speedController) {
		super(speedController);
	}
	
	public SampleVelocitySubsystem(CANTalon speedController, boolean inverted) {
		super(speedController, inverted);
	}
}
