package org.team4909.bionicframework.subsystems.Underglow;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Underglow extends Subsystem{
    private Solenoid red, green, blue;

    public Underglow (int redChannel, int greenChannel, int blueChannel){
        this.red = new Solenoid(redChannel);
        this.green = new Solenoid(greenChannel);
        this.blue = new Solenoid(blueChannel);
    }
    public void reset(){
        red.set(false);
        green.set(false);
        blue.set(false);

    }

    public void setRed(){
        reset();
        red.set(true);
    }

    public void setGreen(){
        reset();
        green.set(true);
    }

    public void setBlue(){
        reset();
        blue.set(true);
    }

    public void setPurple(){
        reset();
        blue.set(true);
        red.set(true);
    }

    public void setWhite(){
        reset();
        blue.set(true);
        red.set(true);
        green.set(true);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
