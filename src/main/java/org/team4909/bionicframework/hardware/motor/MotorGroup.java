package org.team4909.bionicframework.hardware.motor;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import org.team4909.bionicframework.hardware.motor.commandables.PercentOutputCommandable;
import org.team4909.bionicframework.interfaces.Commandable;

public class MotorGroup extends SpeedControllerGroup {
    /**
     * Create a new SpeedControllerGroup with the provided SpeedControllers.
     *
     * @param speedController
     * @param speedControllers The SpeedControllers to add
     */
    public MotorGroup(SpeedController speedController, SpeedController... speedControllers) {
        super(speedController, speedControllers);
    }

    /**
     * @param setpoint The percent output value between [-1,1] to set
     * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
     */
    public Commandable setPercentOutput(double setpoint) {
        return new PercentOutputCommandable(setpoint,this);
    }
}
