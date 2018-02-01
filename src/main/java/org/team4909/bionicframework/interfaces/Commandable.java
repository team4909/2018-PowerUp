package org.team4909.bionicframework.interfaces;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * InstantCommand that can be used directly or as a handler for an operator action.
 */
public abstract class Commandable extends InstantCommand {
	/** 
	 * Method to call for function usage.
	 */
	@Override
	public abstract void initialize();
}
