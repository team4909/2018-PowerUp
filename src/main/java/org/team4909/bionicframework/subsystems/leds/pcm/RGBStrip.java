package org.team4909.bionicframework.subsystems.leds.pcm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team4909.bionicframework.interfaces.Commandable;

public class RGBStrip {
    private final Solenoid red, green, blue;
    private Color currentColor = Color.Black;

    public enum Color {
        Black(false, false, false),
        White(true, true, true),
        Red(true, false, false),
        Lime(false, true, false),
        Blue(false, false, true),
        Yellow(true, true, false),
        Cyan(false, true, true),
        Magenta(true, false, true);

        public final boolean red;
        public final boolean green;
        public final boolean blue;

        Color(boolean red, boolean green, boolean blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    public RGBStrip(int redChannel, int greenChannel, int blueChannel) {
        red = new Solenoid(redChannel);
        green = new Solenoid(greenChannel);
        blue = new Solenoid(blueChannel);
    }

    public Color getCurrentColor(){
        return currentColor;
    }
    
    public void setColor(Color color){
        currentColor = color;

        red.set(color.red);
        green.set(color.green);
        blue.set(color.blue);
    }

    public void reset(){
        setColor(Color.Black);
    }

    public Commandable set(Color color) {
        return new SetColor(color);
    }
    private class SetColor extends Commandable {
        Color color;

        public SetColor(Color color){
            this.color = color;
        }

        public void initialize(){
            setColor(color);
        }
    }

    public Commandable setAllianceColor() {
        return new setAllianceColor();
    }

    private class setAllianceColor extends Commandable {
        public void initialize(){
            switch (DriverStation.getInstance().getAlliance()){
                case Red:
                    setColor(Color.Red);
                    break;
                case Blue:
                    setColor(Color.Blue);
                    break;
                default:    
                    setColor(Color.Lime);
                    break;
            }
        }
    }
}
