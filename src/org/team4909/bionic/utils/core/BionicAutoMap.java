package org.team4909.bionic.utils.core;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public abstract class BionicAutoMap {
	Command autonomousCommand;
	protected SendableChooser<Command> picker = new SendableChooser<>();
	
	public BionicAutoMap(){
		setAutoCommands();
	}
		
	protected abstract void setAutoCommands();
	
	public void startCommand() {
		autonomousCommand = picker.getSelected();

		if (autonomousCommand != null)
			autonomousCommand.start();
	}
	
	public void endCommand() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}
	
	public SendableChooser<Command> getPicker() {
		return picker;
	}
}
