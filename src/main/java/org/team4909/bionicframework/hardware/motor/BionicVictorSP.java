package org.team4909.bionicframework.hardware.motor;

import edu.wpi.first.wpilibj.VictorSP;
import org.team4909.bionicframework.hardware.motor.commandables.PercentOutputCommandable;
import org.team4909.bionicframework.interfaces.Commandable;

/**
 * Wrapper Class for WPI Sparks implementing BionicFramework Commandables
 */
public class BionicVictorSP extends VictorSP {
    /**
     * @param channel PWM Channel
     */
    public BionicVictorSP(int channel) {
        super(channel);

        this.setSafetyEnabled(true);
    }

    /**
     * @param setpoint The percent output value between [-1,1] to set
     * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
     */
    public Commandable setPercentOutput(double setpoint) {
        return new PercentOutputCommandable(setpoint,this);
    }
}
