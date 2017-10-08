package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class Climber extends BionicVoltageSubsystem {
	public final VoltageSetpoint climb = new VoltageSetpoint(this, 1.0);
	
	public Climber(SpeedController speedController) {
		super(speedController);
	}
	
	public Climber(SpeedController speedController, boolean inverted) {
		super(speedController, inverted);
	}
}
