package org.team4909.bionicframework.hardware.devices;

import com.ctre.phoenix.sensors.PigeonIMU;

import org.team4909.bionicframework.hardware.interfaces.BionicGyro;

public class BionicPigeon extends PigeonIMU implements BionicGyro {
	public BionicPigeon(int deviceNumber) {
		super(deviceNumber);
	}

	@Override
	public double getAngle() {
		return this.getAbsoluteCompassHeading();
	}
}
