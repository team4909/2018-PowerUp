package org.team4909.bionicframework.hardware.motor;

import edu.wpi.first.wpilibj.VictorSP;
import org.team4909.bionicframework.hardware.motor.commandables.PercentOutputCommandable;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;

/**
 * Wrapper Class for WPI Sparks implementing BionicFramework Commandables
 */
public class BionicVictorSP extends VictorSP {
    /**
     * @param channel PWM Channel
     */
    public BionicVictorSP(int channel, boolean inverted){
        super(channel);

        setInverted(inverted);
    }
}
