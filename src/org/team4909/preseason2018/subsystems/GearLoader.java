package org.team4909.preseason2018.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;

import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.setpoints.PositionSetpoint;
import org.team4909.bionic.utils.subsystems.BionicPositionalSubsystem;

public class GearLoader extends BionicPositionalSubsystem {
	public PositionSetpoint positionA  = new PositionSetpoint(this, 720.0);
	public PositionSetpoint positionB  = new PositionSetpoint(this, 2880.0);
	
	public GearLoader(SpeedController motor, AnalogPotentiometer potentiometer, PIDConstants pid) {
		super(motor, potentiometer, pid);
	}
}
