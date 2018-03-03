package org.team4909.bionicframework.subsystems.leds.arduino;

import org.team4909.bionicframework.hardware.core.Arduino;
import org.team4909.bionicframework.interfaces.Commandable;

public class Neopixels {
    private final Arduino arduino;

    private interface Signal {
        int getInt();
    }

    public enum Pattern implements Signal {
        RainbowSegment(1),
        RainbowStrip(2),
        Fire(3),
        LevelUp(4),
        LightSaber(9),
        PingPong(10);

        public final int signal;

        Pattern(int signal) {
            this.signal = signal;
        }

        public int getInt() {
            return signal;
        }
    }

    public enum Color implements Signal {
        BionicGreen(5),
        Blue(6),
        Red(7),
        Random(8);

        public final int signal;

        Color(int signal) {
            this.signal = signal;
        }

        public int getInt() {
            return signal;
        }
    }

    public Neopixels(Arduino arduino, int pin, int pixelCount) {
        this.arduino = arduino;
    }

    public void setState(Signal signal) {
        arduino.send(signal.getInt());
    }

    public Commandable set(Signal color) {
        return new Set(color);
    }

    private class Set extends Commandable {
        Signal signal;

        public Set(Signal signal) {
            this.signal = signal;
        }

        public void initialize() {
            setState(signal);
        }
    }
}
