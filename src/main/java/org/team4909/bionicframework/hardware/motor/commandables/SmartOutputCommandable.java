package org.team4909.bionicframework.hardware.motor.commandables;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import org.team4909.bionicframework.interfaces.Commandable;

public class SmartOutputCommandable extends Commandable {
    private ControlMode mode;
    private double setpoint;
    private BaseMotorController speedController;

    public SmartOutputCommandable(ControlMode mode, double setpoint, BaseMotorController speedController) {
        this.mode = mode;
        this.setpoint = setpoint;
        this.speedController = speedController;
    }

    public void initialize() {
        speedController.set(mode, setpoint);
    }
}