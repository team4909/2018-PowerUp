package org.team4909.bionicframework.hardware.pneumatics.commandables;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.team4909.bionicframework.hardware.pneumatics.BionicDoubleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;

public class DoubleSolenoidCommandable extends Commandable {
    private final DoubleSolenoid.Value setpoint;
    private final BionicDoubleSolenoid bionicDoubleSolenoid;

    public DoubleSolenoidCommandable(DoubleSolenoid.Value setpoint, BionicDoubleSolenoid bionicDoubleSolenoid){
        this.setpoint = setpoint;
        this.bionicDoubleSolenoid = bionicDoubleSolenoid;
    }

    @Override
    public void initialize() {
        bionicDoubleSolenoid.set(setpoint);
    }
}
