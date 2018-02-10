package org.team4909.bionicframework.subsystems.elevator.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.interfaces.Commandable;

public class SetElevatorPosition extends Commandable {
        private final double setpoint;
        private final BionicSRX speedController;

        public SetElevatorPosition(double setpoint, BionicSRX speedController) {
            this.setpoint = setpoint;
            this.speedController = speedController;
        }

        @Override
        public void initialize() {
//            speedController.set(ControlMode.Position, setpoint);
        }
}
