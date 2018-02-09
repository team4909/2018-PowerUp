package org.team4909.bionicframework.hardware.pneumatics.commandables;

import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;

public class SingleSolenoidInvertCommandable extends Commandable {
    private final BionicSingleSolenoid bionicSingleSolenoid;

    public SingleSolenoidInvertCommandable(BionicSingleSolenoid bionicSingleSolenoid){
        this.bionicSingleSolenoid = bionicSingleSolenoid;
    }

    @Override
    public void initialize() {
        bionicSingleSolenoid.set(!bionicSingleSolenoid.get());
    }
}
