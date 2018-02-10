package org.team4909.bionicframework.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;
import org.team4909.bionicframework.subsystems.elevator.commands.SetElevatorPosition;

public class ElevatorSubsystem extends Subsystem {
    private final BionicSRX bionicSRX;
    private final BionicJoystick joystick;
    private final BionicAxis axis;

    public double holdingPosition;

    public ElevatorSubsystem(BionicSRX bionicSRX, BionicJoystick joystick, BionicAxis axis, int forwardLimit, int reverseLimit){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;

        bionicSRX.enableSoftLimits(forwardLimit, reverseLimit);
        bionicSRX.enableZeroOnRevLimit();
    }

    @Override
    public void periodic() {
        double moveSpeed = joystick.getSensitiveAxis(axis);

        if(moveSpeed == 0) {
            bionicSRX.set(ControlMode.Position, holdingPosition);
        } else {
            bionicSRX.set(ControlMode.PercentOutput, moveSpeed);
            holdingPosition = bionicSRX.getSelectedSensorPosition(0);
        }
    }

    public Commandable holdPosition(double position){
        return new SetElevatorPosition(position,this);
    }

    @Override
    protected void initDefaultCommand() {}
}
