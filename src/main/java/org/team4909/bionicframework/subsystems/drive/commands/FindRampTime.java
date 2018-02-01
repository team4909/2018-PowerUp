package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class FindRampTime extends Command {
    private final BionicDrive bionicDrive;
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;

    double startTime;

    public FindRampTime(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX) {
        requires(bionicDrive);
        setInterruptible(false);

        this.bionicDrive = bionicDrive;
        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;
    }

    @Override
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        leftSRX.set(ControlMode.PercentOutput, 0.5);
        rightSRX.set(ControlMode.PercentOutput, 0.5);
    }

    @Override
    protected boolean isFinished() {
        double currentVelocityTicks = (leftSRX.getSelectedSensorVelocity(0) + rightSRX.getSelectedSensorVelocity(0)) * 10;

        return currentVelocityTicks > bionicDrive.pathgen.drivetrainConfig.getMaxVelocityTicks();
    }

    @Override
    protected void end() {
        System.out.println("Seconds from Neutral: " + (Timer.getFPGATimestamp() - startTime));
    }
}
