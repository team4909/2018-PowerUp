package org.team4909.powerup2018.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.interfaces.Commandable;

public class IntakeSubsystem extends MotorSubsystem {

    public IntakeSubsystem(SpeedController speedController, SpeedController... speedControllers) {
        super(speedController, speedControllers);
    }

    public Commandable intakeFast() {
        return setPercentOutput(1.0);
    }

    public Commandable outtakeFast() {
        return setPercentOutput(-1.0);
    }

    public Commandable outtakeSlow() {
        return setPercentOutput(-0.5);
    }
}
