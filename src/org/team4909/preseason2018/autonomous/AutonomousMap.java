package org.team4909.preseason2018.autonomous;

import org.team4909.bionic.utils.core.BionicAutoMap;

public class AutonomousMap extends BionicAutoMap {
	public void setAutoCommands(){
		picker.addDefault("Do Nothing", null);
		picker.addObject("Sample Auto. Command", new SampleAutoCommand());
	}
}
