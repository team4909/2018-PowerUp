package org.team4909.preseason2018.autonomous;

import org.team4909.bionic.utils.core.BionicAutoMap;
import org.team4909.bionic.utils.core.BionicCommand;

public class AutonomousMap extends BionicAutoMap {
	public void setAutoCommands(){
		picker.addDefault("Do Nothing", new BionicCommand());
		picker.addObject("Break Baseline", new PassBaseline());
		picker.addObject("Place Boiler Gear", new PlaceBoilerGear());
		picker.addObject("Place Center Gear", new PlaceCenterGear());
		picker.addObject("Place Loader Gear", new PlaceLoaderGear());
	}
}
