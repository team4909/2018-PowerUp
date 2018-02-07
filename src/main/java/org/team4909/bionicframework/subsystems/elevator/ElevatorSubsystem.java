package org.team4909.bionicframework.subsystems.elevator;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;

public class ElevatorSubsystem extends Subsystem {
    private final BionicSRX bionicSRX;
    private final BionicJoystick joystick;
    private final BionicAxis axis;

    private double holdingPosition;

    public ElevatorSubsystem(BionicSRX bionicSRX, BionicJoystick joystick, BionicAxis axis, int maxPosition){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;

        bionicSRX.setNeutralMode(NeutralMode.Brake);

        //bionicSRX.enableSoftLimit(maxPosition, 0);
        bionicSRX.enableZeroOnRevLimit();
    }

    @Override
    public void periodic() {
        double moveSpeed = joystick.getSensitiveAxis(axis);

        if(moveSpeed != 0) {
            bionicSRX.set(moveSpeed);

            holdingPosition = bionicSRX.getSelectedSensorPosition(0);
        } else {
            bionicSRX.set(
                    ControlMode.Position,
                    holdingPosition
            );
        }
    }

    @Override
    protected void initDefaultCommand() {}
}
