package org.team4909.bionicframework.subsystems.elevator.commands;


import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class SetElevatorPosition extends Commandable {
    private final int setpoint;
    private final ElevatorSubsystem elevator;

    public SetElevatorPosition(int setpoint, ElevatorSubsystem elevator) {
        this.setpoint = setpoint;
        this.elevator = elevator;
    }

    @Override
    public void initialize() {
        elevator.holdingPosition = setpoint;
    }
}