package org.team4909.bionicframework.hardware.pneumatics;

import edu.wpi.first.wpilibj.Solenoid;
import org.team4909.bionicframework.hardware.pneumatics.commandables.SingleSolenoidCommandable;
import org.team4909.bionicframework.interfaces.Commandable;

public class BionicSingleSolenoid extends Solenoid {
    public BionicSingleSolenoid(int channel) {
        super(channel);
    }

    public Commandable setState(boolean value) {
        return new SingleSolenoidCommandable(value, this);
    }
}
