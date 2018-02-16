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
    private final double oiMultiplier;

    public ElevatorSubsystem(BionicSRX bionicSRX,
                             BionicJoystick joystick, BionicAxis axis, double oiMultiplier,
                             int forwardLimit){
        this.bionicSRX = bionicSRX;
        this.joystick = joystick;
        this.axis = axis;

        this.oiMultiplier = oiMultiplier;

        bionicSRX.enableFwdSoftLimit(forwardLimit);
    }

    @Override
    public void periodic() {
        if(bionicSRX.getSensorCollection().isRevLimitSwitchClosed()){
            holdingPosition = 0;

            bionicSRX.setSelectedSensorPosition(holdingPosition,0,0);
        }

        double moveSpeed = joystick.getSensitiveAxis(axis) * oiMultiplier;

        if(moveSpeed == 0 && !encoderOverride) {
            bionicSRX.set(ControlMode.Position, holdingPosition);
        } else {
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
