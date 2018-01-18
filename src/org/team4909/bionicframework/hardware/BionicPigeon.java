package org.team4909.bionicframework.hardware;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Wrapper Class for CTRE Pigeon implementing WPILib Gyro
 */
public class BionicPigeon extends PigeonIMU implements Gyro {
	/**
	 * @param address CAN Address as configured on roboRio dashboard
	 */
	public BionicPigeon(int address) {
		super(address);
	}

	/**
	 * Return Current Heading in Degrees [0, 360)
	 */
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
