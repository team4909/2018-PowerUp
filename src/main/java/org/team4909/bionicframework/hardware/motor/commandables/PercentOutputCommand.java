package org.team4909.bionicframework.hardware.motor.commandables;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

public class PercentOutputCommand extends Command {
    private final double setpoint;
    private final SpeedController speedController;

    public PercentOutputCommand(double setpoint, SpeedController speedController) {
        this.setpoint = setpoint;
        this.speedController = speedController;
    }

    @Override
    public void initialize() {
        speedController.set(setpoint);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public void end() {
        speedController.set(0);
    }
}
