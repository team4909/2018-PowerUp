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

    public int holdingPosition;
    public boolean encoderOverride;
    private final double joystickMultiplier;

    public ElevatorSubsystem(BionicSRX bionicSRX,
                             BionicJoystick joystick, BionicAxis axis, double joystickMultiplier,
                             int forwardLimit){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;

        this.joystickMultiplier = joystickMultiplier;

        bionicSRX.enableFwdSoftLimit(forwardLimit);
        bionicSRX.configReverseSoftLimitEnable(false, 0);
    }

    @Override
    public void periodic() {
        double moveSpeed = joystick.getSensitiveAxis(axis) * joystickMultiplier;

        if(moveSpeed == 0 && !encoderOverride) {
            bionicSRX.set(ControlMode.Position, holdingPosition);
        } else if(moveSpeed > 0){
            bionicSRX.set(ControlMode.PercentOutput, moveSpeed);
            holdCurrentPosition();
        } else if(bionicSRX.getSensorCollection().isRevLimitSwitchClosed()){
            holdingPosition = 0;
            bionicSRX.setSelectedSensorPosition(holdingPosition,0,0);
        } else if(moveSpeed < 0){
            bionicSRX.set(ControlMode.PercentOutput, moveSpeed);
            holdCurrentPosition();
        }
    }

    public int getCurrentPosition(){
        return (int) bionicSRX.getSelectedSensorPosition();
    }

    public Commandable holdPosition(int position){
        return new SetElevatorPosition(position,this);
    }

    public void holdCurrentPosition(){
        holdingPosition = getCurrentPosition();
    }

    @Override
    protected void initDefaultCommand() {}
}
