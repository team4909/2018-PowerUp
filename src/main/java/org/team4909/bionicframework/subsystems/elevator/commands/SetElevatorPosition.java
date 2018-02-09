package org.team4909.bionicframework.subsystems.elevator.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import org.team4909.bionicframework.interfaces.Commandable;

public class SetElevatorPosition extends Commandable {
        private final double setpoint;
        private final BaseMotorController speedController;

        public SetElevatorPosition(double setpoint, BaseMotorController speedController) {
            this.setpoint = setpoint;
            this.speedController = speedController;
        }

        @Override
        public void initialize() {
            speedController.set(ControlMode.Position, setpoint);
        }

}
