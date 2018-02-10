package org.team4909.bionicframework.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
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

    private double holdingPosition;

    public ElevatorSubsystem(BionicSRX bionicSRX, BionicJoystick joystick, BionicAxis axis, int forwardLimit, int reverseLimit){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;

        bionicSRX.setNeutralMode(NeutralMode.Brake);

        //bionicSRX.enableSoftLimits(forwardLimit, reverseLimit);
        bionicSRX.enableZeroOnRevLimit();
    }

    @Override
    public void periodic() {
        double moveSpeed = joystick.getSensitiveAxis(axis);

        if(moveSpeed != 0) {
            bionicSRX.set(moveSpeed);

            holdCurrentPosition();
            System.out.println("PID from :" + holdingPosition);
        } else {
            System.out.println("PID to :" + holdingPosition);

            bionicSRX.set(ControlMode.Position, holdingPosition);
        }
    }

    public void holdCurrentPosition(){
        holdingPosition = bionicSRX.getSelectedSensorPosition();
    }

    public Commandable holdPosition(double position){
        return new SetElevatorPosition(position, bionicSRX);
    }

    @Override
    protected void initDefaultCommand() {}
}
