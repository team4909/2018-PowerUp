package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class BionicPigeon extends PigeonIMU implements Gyro {
	public BionicPigeon(int deviceNumber) {
		super(deviceNumber);
	}

	@Override
	public double getAngle() {
		return this.getAbsoluteCompassHeading();
	}

	@Override
	public void calibrate() {
		this.enterCalibrationMode(CalibrationMode.BootTareGyroAccel, 0);
	}

	@Override
	public void reset() {
		this.setCompassAngle(0, 0);
	}

	@Override
	public double getRate() {
		return 0;
	}

	@Override
	public void free() {}
}
