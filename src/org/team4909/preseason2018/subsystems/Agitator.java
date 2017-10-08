package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVoltageSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class Agitator extends BionicVoltageSubsystem {
	public final VoltageSetpoint feedShooter = new VoltageSetpoint(0.5);
	
	public Agitator(SpeedController speedController) {
		super(speedController);
	}
	
	public Agitator(SpeedController speedController, boolean inverted) {
		super(speedController, inverted);
	}
}
