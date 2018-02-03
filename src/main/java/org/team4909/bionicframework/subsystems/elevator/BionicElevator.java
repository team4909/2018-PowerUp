package org.team4909.bionicframework.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;

public class BionicElevator extends Subsystem {
    private final BionicSRX bionicSRX;
    private final BionicJoystick joystick;
    private final BionicAxis axis;

    private double holdingPosition;

    public BionicElevator(BionicSRX bionicSRX, BionicJoystick joystick, BionicAxis axis){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;
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

    @Override
    protected void initDefaultCommand() {}
}
