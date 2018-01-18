package org.team4909.bionicframework.utils;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * InstantCommand that can be used either a function or as a handler for an operator action.
 */
public abstract class Commandable extends InstantCommand {
	/** 
	 * Method to call for function usage.
	 */
	@Override
	public abstract void initialize();
}
