package org.team4909.bionicframework.subsystems.Intake;

import org.team4909.bionicframework.hardware.motor.BionicSpark;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.interfaces.Commandable;

public class IntakeSubsystem extends MotorSubsystem {

    public IntakeSubsystem(int channelA, boolean invertedA, int channelB, boolean invertedB){
        super(
        new BionicSpark(channelA, invertedA),
                new BionicSpark(channelB, invertedB));
    }

    public void periodic(){

    }
    public void initDefaultCommand(){

    }

    public Commandable cancel(){
        return super.setPercentOutput(0);
    }

    public Commandable intake(){
        return super.setPercentOutput(1);
    }

    public Commandable outtake(){
       return super.setPercentOutput(-1.0);
    }

    public Commandable outtakeSlow(){
        return super.setPercentOutput(-0.5);
    }

}
