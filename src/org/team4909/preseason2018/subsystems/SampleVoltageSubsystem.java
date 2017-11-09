package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class SampleVoltageSubsystem extends BionicVoltageSubsystem {
	public final VoltageSetpoint full = new VoltageSetpoint(this, 1.0);
	
	public SampleVoltageSubsystem(SpeedController speedController) {
		super(speedController);
	}
	
	public SampleVoltageSubsystem(SpeedController speedController, boolean inverted) {
		super(speedController, inverted);
	}
}
