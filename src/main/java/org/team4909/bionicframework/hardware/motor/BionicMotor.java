package org.team4909.bionicframework.hardware.motor;

import edu.wpi.first.wpilibj.SpeedController;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;

public interface BionicMotor extends SpeedController {
    void set(BionicJoystick joystick, BionicAxis axis);
    Commandable setPercentOutput(double setpoint);
}
