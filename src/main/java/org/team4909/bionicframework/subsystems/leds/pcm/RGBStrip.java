package org.team4909.bionicframework.subsystems.leds.pcm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team4909.bionicframework.interfaces.Commandable;

public class RGBStrip extends Subsystem {
    private Solenoid red, green, blue;

    public enum Colors {
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

        Colors(boolean red, boolean green, boolean blue) {
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

    public void setColor(Colors color){
        red.set(color.red);
        green.set(color.green);
        blue.set(color.blue);
    }

    public void reset(){
        setColor(Colors.Black);
    }

    public Commandable set(Colors color) {
        return new SetColor(color);
    }
    private class SetColor extends Commandable {
        Colors color;

        public SetColor(Colors color){
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
                    setColor(Colors.Red);
                    break;
                case Blue:
                    setColor(Colors.Blue);
                    break;
                default:    
                    setColor(Colors.Lime);
                    break;
            }
        }
    }

    @Override
    protected void initDefaultCommand() {

    }
}
