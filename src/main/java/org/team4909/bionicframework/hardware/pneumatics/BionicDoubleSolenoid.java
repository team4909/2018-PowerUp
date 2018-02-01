package org.team4909.bionicframework.hardware.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.team4909.bionicframework.hardware.pneumatics.commandables.DoubleSolenoidCommandable;
import org.team4909.bionicframework.interfaces.Commandable;

public class BionicDoubleSolenoid extends DoubleSolenoid {
    public BionicDoubleSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public Commandable setState(DoubleSolenoid.Value value) {
        return new DoubleSolenoidCommandable(value, this);
    }
}
