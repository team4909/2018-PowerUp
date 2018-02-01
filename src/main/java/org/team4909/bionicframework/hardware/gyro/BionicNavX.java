package org.team4909.bionicframework.hardware.gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class BionicNavX extends AHRS implements Gyro {
    public BionicNavX(){
        super(SerialPort.Port.kMXP);
    }

    @Override
    public void calibrate() { }
}
