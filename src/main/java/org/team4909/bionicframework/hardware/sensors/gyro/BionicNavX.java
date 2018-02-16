package org.team4909.bionicframework.hardware.sensors.gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class BionicNavX extends AHRS implements Gyro {
    public BionicNavX(){
        super(SPI.Port.kMXP);
    }

    @Override
    public void calibrate() { }
}
