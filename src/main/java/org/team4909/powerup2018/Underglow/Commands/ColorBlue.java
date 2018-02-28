package org.team4909.bionicframework.subsystems.Underglow.Commands;

import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.Underglow.Underglow;

public class ColorBlue extends Commandable {
    private Underglow leds;

    public ColorBlue(Underglow underglow){
        leds = underglow;
    }
    public void initialize() {
        leds.setBlue();
    }
}
