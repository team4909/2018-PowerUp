package org.team4909.bionicframework.operator.controllers;

import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicButton;
import org.team4909.bionicframework.operator.generic.BionicJoystick;
import org.team4909.bionicframework.operator.generic.BionicPOV;

/**
 * BionicF310 models the Logitech F310 Gamepad and its buttons for easier mapping for the operator interface.
 */
public class BionicF310 extends BionicJoystick {
    public static BionicButton Start = new BionicButton(8);
    public static BionicButton Back = new BionicButton(7);

    public static BionicButton A = new BionicButton(1);
    public static BionicButton B = new BionicButton(2);
    public static BionicButton X = new BionicButton(3);
    public static BionicButton Y = new BionicButton(4);

    public static BionicButton L = new BionicButton(9);
    public static BionicAxis LX = new BionicAxis(0);
    public static BionicAxis LY = new BionicAxis(1);
    public static BionicAxis LT = new BionicAxis(2);
    public static BionicButton LB = new BionicButton(5);

    public static BionicButton R = new BionicButton(10);
    public static BionicAxis RX = new BionicAxis(4);
    public static BionicAxis RY = new BionicAxis(5);
    public static BionicAxis RT = new BionicAxis(3);
    public static BionicButton RB = new BionicButton(6);

    public static BionicPOV Up = new BionicPOV(0);
    public static BionicPOV Down = new BionicPOV(180);
    public static BionicPOV RightUp = new BionicPOV(45);
    public static BionicPOV Right = new BionicPOV(90);
    public static BionicPOV RightDown = new BionicPOV(135);
    public static BionicPOV LeftUp = new BionicPOV(225);
    public static BionicPOV Left = new BionicPOV(270);
    public static BionicPOV LeftDown = new BionicPOV(315);

    /**
     * @param port        Port between 0...5 the USB Joystick is configured to use.
     * @param deadzone
     * @param sensitivity
     */
    public BionicF310(int port, double deadzone, double sensitivity) {
        super(port, deadzone, sensitivity);
    }
}
