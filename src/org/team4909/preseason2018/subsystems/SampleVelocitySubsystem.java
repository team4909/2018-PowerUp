package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import org.team4909.bionic.utils.subsystems.BionicVelocitySubsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class SampleVelocitySubsystem extends BionicVelocitySubsystem {
	public VelocitySetpoint  VelocitySetpoint  = new VelocitySetpoint(this, 1.0);
	
	public SampleVelocitySubsystem(SpeedController speedController) {
		super(speedController);
	}
	
	public SampleVelocitySubsystem(SpeedController speedController, boolean inverted) {
		super(speedController, inverted);
	}
}
