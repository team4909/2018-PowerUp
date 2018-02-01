package org.team4909.bionicframework.operator.generic;

/**
 * Helper Class for Joystick Axes to be Used with BionicJoystick
 */
public class BionicAxis {
	private int axis;
	
	/**
	 * Constructs New Axis Mapping
	 * 
	 * @param axis Axis number fron 0...5 as seen in driver station.
	 */
	public BionicAxis(int axis){
		this.axis = axis;	
	}
	
	/**
	 * Get Internal Axis Number
	 * 
	 * @return Returns internal axis number.
	 */
	public int getNumber(){
		return axis;
	}	
}