package org.team4909.bionic.utils.oi;

public class BionicF310 extends BionicJoystick {
	public BionicF310(int port) {
		super(port);
	}

	public static BionicButton A = new BionicButton(1);
	public static BionicButton B = new BionicButton(2);
	public static BionicButton X = new BionicButton(3);
	public static BionicButton Y = new BionicButton(4);

	public static BionicButton Start = new BionicButton(8);
	public static BionicButton Back = new BionicButton(7);
	
	public static BionicButton L = new BionicButton(9);
	public static BionicButton R = new BionicButton(10);
	
	public static BionicAxis LX = new BionicAxis(0);
	public static BionicAxis LY = new BionicAxis(1);
	
	public static BionicAxis RX = new BionicAxis(4);
	public static BionicAxis RY = new BionicAxis(5);
	
	public static BionicAxis LT = new BionicAxis(2);
	public static BionicAxis RT = new BionicAxis(3);

	public static BionicButton LB = new BionicButton(5);
	public static BionicButton RB = new BionicButton(6);
}
