package org.team4909.bionicframework.hardware.sensors.distance;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class LIDAR{
    private I2C i2c;
    private static byte[] distance;

    private final int LIDAR_ADDR=0x62;
    private final int LIDAR_CONFIG_REGISTER=0x00;
    private final int LIDAR_DISTANCE_REGISTER=0x8f;
    private final Notifier LIDAR_NOTIFICATION = new Notifier(this::periodic);

    public LIDAR(Port port) {
        i2c=new I2C(Port.kMXP, LIDAR_ADDR);

        distance=new byte[2];
        LIDAR_NOTIFICATION.startPeriodic(0.05);
    }
    public static int getDistance() {
        return (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
    }

    public void periodic() {
        i2c.write(LIDAR_CONFIG_REGISTER, 0x04); // Initiate measurement
        Timer.delay(0.04); // Delay for measurement to be taken

        i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance); // Read in measurement
        Timer.delay(0.01); // Delay to prevent over polling
    }



}
