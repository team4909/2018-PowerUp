package org.team4909.bionicframework.hardware.pneumatics.commandables;

import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;

public class SingleSolenoidCommandable extends Commandable {
    private final boolean setpoint;
    private final BionicSingleSolenoid bionicSingleSolenoid;

    public SingleSolenoidCommandable(boolean setpoint, BionicSingleSolenoid bionicSingleSolenoid){
        this.setpoint = setpoint;
        this.bionicSingleSolenoid = bionicSingleSolenoid;
    }

    @Override
    public void initialize() {
        bionicSingleSolenoid.set(setpoint);
    }
}
