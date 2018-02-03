package org.team4909.bionicframework.hardware.motor;

import edu.wpi.first.wpilibj.Spark;

import org.team4909.bionicframework.hardware.motor.commandables.PercentOutputCommandable;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;

/**
 * Wrapper Class for WPI Sparks implementing BionicFramework Commandables
 */
public class BionicSpark extends Spark implements BionicMotor {
	/**
	 * @param channel PWM Channel
	 */
	public BionicSpark(int channel) {
		super(channel);
	}

	public BionicSpark(int channel, boolean inverted){
		this(channel);

		setInverted(inverted);
	}

    @Override
    public void set(BionicJoystick joystick, BionicAxis axis) {
        set(joystick.getSensitiveAxis(axis));
    }

    /**
	 * @param setpoint The percent output value between [-1,1] to set
	 * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
	 */
	public Commandable setPercentOutput(double setpoint) {
	    return new PercentOutputCommandable(setpoint,this);
	}
}
