package org.team4909.bionicframework.subsystems.elevator.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class SetElevatorPosition extends Commandable {
        private final double setpoint;
        private final ElevatorSubsystem elevator;

        public SetElevatorPosition(double setpoint, ElevatorSubsystem elevator) {
            this.setpoint = setpoint;
            this.elevator = elevator;
        }

        @Override
        public void initialize() {
            elevator.holdPosition(setpoint);
        }
}
