package org.team4909.bionicframework.hardware.motor.commandables;

import edu.wpi.first.wpilibj.SpeedController;
import org.team4909.bionicframework.interfaces.Commandable;

public class PercentOutputCommandable extends Commandable {
    private final double setpoint;
    private final SpeedController speedController;

    public PercentOutputCommandable(double setpoint, SpeedController speedController) {
        this.setpoint = setpoint;
        this.speedController = speedController;
    }

    @Override
    public void initialize() {
        speedController.set(setpoint);
    }

    @Override
    public synchronized void cancel() {
        speedController.set(0);

        super.cancel();
    }
}
