package org.team4909.bionicframework.hardware.pneumatics.commandables;

import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;

public class SingleSolenoidCommandable extends Commandable {
    private final BionicSingleSolenoid bionicSingleSolenoid;

    public SingleSolenoidCommandable(BionicSingleSolenoid bionicSingleSolenoid){
        this.bionicSingleSolenoid = bionicSingleSolenoid;
    }

    @Override
    public void initialize() {
        bionicSingleSolenoid.set(true);
    }

    @Override
    public synchronized void cancel() {
        bionicSingleSolenoid.set(false);
    }
}
