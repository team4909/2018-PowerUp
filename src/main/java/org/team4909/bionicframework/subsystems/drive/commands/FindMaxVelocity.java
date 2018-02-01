package org.team4909.bionicframework.subsystems.drive.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

private class FindMaxVelocity extends Command {
    double startTime;
    double runningAverage;

    public FindMaxVelocity(BionicDrive subsystem) {
        requires(subsystem);
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        leftSRX.set(ControlMode.PercentOutput, 0.5);
        rightSRX.set(ControlMode.PercentOutput, 0.5);

        leftSRX.enableVoltageCompensation(true);
        rightSRX.enableVoltageCompensation(true);

        double timeSinceStart = Timer.getFPGATimestamp() - startTime;
        double currentVelocityTicks = (leftSRX.getSelectedSensorVelocity(0) + rightSRX.getSelectedSensorVelocity(0)) * 10;

        if(timeSinceStart > 10) {
            if (runningAverage != 0) {
                runningAverage = (runningAverage + currentVelocityTicks) / 2;
            } else {
                runningAverage = currentVelocityTicks;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return (Timer.getFPGATimestamp() - startTime) > 30;
    }

    @Override
    protected void end() {
        System.out.println("Max Velocity (ticks/s): " + runningAverage);
    }
}