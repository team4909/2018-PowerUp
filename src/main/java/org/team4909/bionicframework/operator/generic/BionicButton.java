package org.team4909.bionicframework.operator.generic;

/**
 * Helper Class for Joystick Buttons to be Used with BionicJoystick
 */
public class BionicButton {
	private int button;
	
	/**
	 * Constructs New Button Mapping
	 * 
	 * @param button Button number fron 1...N as seen in driver station.
	 */
	public BionicButton(int button) {
		this.button = button;
	}
	
	/**
	 * Get Internal Button Number
	 * 
	 * @return Returns internal button number.
	 */
	public int getNumber(){
		return button;
	}
}