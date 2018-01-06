package org.team4909.bionicframework.hardware.devices;

import com.ctre.phoenix.sensors.PigeonIMU;

import org.team4909.bionicframework.hardware.interfaces.BionicGyro;

public class BionicPigeon implements BionicGyro {
	private PigeonIMU pigeon;
	
	public BionicPigeon(int deviceNumber) {
		this.pigeon = new PigeonIMU(deviceNumber);
	}

	@Override
	public double getAngle() {
		return pigeon.getAbsoluteCompassHeading();
	}
}
