package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import org.team4909.bionic.utils.setpoints.VoltageSetpoint;
import org.team4909.bionic.utils.subsystems.BionicVelocitySubsystem;
import com.ctre.CANTalon;

public class Shooter extends BionicVelocitySubsystem {
	public VelocitySetpoint fullSpeed  = new VelocitySetpoint(this, 3500.0);
	public VoltageSetpoint fullVoltage  = new VoltageSetpoint(this, 1.0);
	
	public Shooter(CANTalon speedController, PIDConstants pid, double f) {
		super(speedController, pid, f);
	}
	
	public Shooter(CANTalon speedController, PIDConstants pid, double f, boolean inverted) {
		super(speedController, pid, f, inverted);
	}
}