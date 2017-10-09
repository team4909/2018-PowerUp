package org.team4909.bionic.utils.oi;

public class BionicF310 extends BionicJoystick {
	public BionicF310(int port) {
		super(port);
	}

	public static BionicButton A = new BionicButton(0);
	public static BionicButton B = new BionicButton(0);
	public static BionicButton X = new BionicButton(0);
	public static BionicButton Y = new BionicButton(0);

	public static BionicButton Start = new BionicButton(0);
	public static BionicButton Back = new BionicButton(0);
	
	public static BionicButton L = new BionicButton(0);
	public static BionicButton R = new BionicButton(0);
	
	public static BionicAxis LX = new BionicAxis(0);
	public static BionicAxis LY = new BionicAxis(0);
	
	public static BionicAxis RX = new BionicAxis(0);
	public static BionicAxis RY = new BionicAxis(0);
	
	public static BionicAxis LT = new BionicAxis(0);
	public static BionicAxis RT = new BionicAxis(0);

	public static BionicButton LB = new BionicButton(0);
	public static BionicButton RB = new BionicButton(0);
}
