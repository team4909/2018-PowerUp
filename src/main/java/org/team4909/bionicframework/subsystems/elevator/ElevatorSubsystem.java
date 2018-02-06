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

    public ElevatorSubsystem(BionicSRX bionicSRX, BionicJoystick joystick, BionicAxis axis, int maxPosition){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;

        bionicSRX.setNeutralMode(NeutralMode.Brake);

        bionicSRX.enableSoftLimit(0, maxPosition);
        bionicSRX.enableZeroOnRevLimit();
    }

    @Override
    public void periodic() {
        double moveSpeed = joystick.getSensitiveAxis(axis);

        if(moveSpeed != 0 ) {
            bionicSRX.set(
                    ControlMode.PercentOutput,
                    moveSpeed
            );
        } else {
            bionicSRX.set(
                    ControlMode.Position,
                    bionicSRX.getSelectedSensorPosition(0)
            );
        }
    }

    @Override
    protected void initDefaultCommand() {}
}
