package org.team4909.bionicframework.subsystems.Underglow.Commands;

import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.Underglow.Underglow;

public class ColorRed extends Commandable {
    private Underglow leds;

    public ColorRed(Underglow underglow){
        leds = underglow;
    }
    public void initialize() {
        leds.setRed();
    }
}
