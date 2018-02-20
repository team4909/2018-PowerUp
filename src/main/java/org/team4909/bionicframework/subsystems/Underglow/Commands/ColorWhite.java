package org.team4909.bionicframework.subsystems.Underglow.Commands;

import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.Underglow.Underglow;

public class ColorWhite extends Commandable {
    private Underglow leds;

    public ColorWhite(Underglow underglow){
        leds = underglow;
    }
    public void initialize() {
        leds.setWhite();
    }
}
