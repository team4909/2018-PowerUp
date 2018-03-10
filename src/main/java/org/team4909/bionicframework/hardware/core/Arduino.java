package org.team4909.bionicframework.hardware.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.leds.pcm.RGBStrip;

/**
 * Arduino Library for I2C Communications to be used for LEDs and sensors
 */
public class Arduino {
    private I2C i2c;

    public Arduino(int address) {
        i2c = new I2C(Port.kMXP, address);
    }

    public void send(int signal) {
        byte[] toSend = {(byte) signal};

        try {
            i2c.transaction(toSend, 1, new byte[] {}, 0);
        } catch (Exception e) {
            DriverStation.reportError("Could not send to Arduino", true);
        }
    }

    public Commandable sendSignal(int signal){
        return new SendSignal(signal);
    }

    private class SendSignal extends Commandable {
        private int signal;

        public SendSignal(int signal) {
            if (signal == 6){
                DriverStation.Alliance color;
                color = DriverStation.getInstance().getAlliance();
                if(color == DriverStation.Alliance.Blue){
                    this.signal = 6;
                }
                if(color == DriverStation.Alliance.Red){
                    this.signal = 7;
                }
                if(color == DriverStation.Alliance.Invalid){
                    this.signal = 5;
                }
            }   else {
                this.signal = signal;
            }
        }

        @Override
        public void initialize() {
            send(signal);
        }
    }
}

