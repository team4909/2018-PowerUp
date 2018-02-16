package org.team4909.bionicframework.subsystems.Intake;

import edu.wpi.first.wpilibj.SpeedController;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.hardware.sensors.distance.LIDAR;

public class IntakeSubsystem extends MotorSubsystem {
    private final LIDAR LIDARSensor;
    private final double distanceThreshold;

    /**
     * Create a new SpeedControllerGroup with the provided SpeedControllers.
     *
     * @param LIDARSensor
     */
    public IntakeSubsystem(LIDAR LIDARSensor, double distanceThreshold, SpeedController speedController, SpeedController... speedControllers) {
        super(speedController, speedControllers);
        this.LIDARSensor = LIDARSensor;
        this.distanceThreshold = distanceThreshold;
    }

    @Override
    public void set(double speed) {
        if (!hasPossession() || speed < 0) {
            super.set(speed);
        } else {
            super.set(0);
        }
    }

    public boolean hasPossession(){
        return LIDARSensor.getDistance() < distanceThreshold;
    }

}
