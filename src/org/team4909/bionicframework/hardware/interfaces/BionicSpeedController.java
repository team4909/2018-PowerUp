package org.team4909.bionicframework.hardware.interfaces;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

public interface BionicSpeedController extends SpeedController {
	Command setPercentOutput(double setpoint);
}