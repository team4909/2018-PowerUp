package org.team4909.bionicframework.subsystems.drive.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

private class DriveOI extends Command {
    public DriveOI(BionicDrive subsystem) {
        requires(subsystem);
    }

    @Override
    protected void initialize() {
        super.initialize();

        leftSRX.setNeutralMode(NeutralMode.Coast);
        rightSRX.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected void execute() {
        double speed = speedInputGamepad.getSensitiveAxis(speedInputAxis);
        double rotation = rotationInputGamepad.getSensitiveAxis(rotationInputAxis);

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotation)), speed);

        if (speed >= 0.0 && rotation >= 0.0) {
            leftMotorOutput = maxInput;
            rightMotorOutput = speed - rotation;
        } else if (speed >= 0.0 && rotation < 0.0) {
            leftMotorOutput = speed + rotation;
            rightMotorOutput = maxInput;
        } else if (speed < 0.0 && rotation >= 0.0) {
            leftMotorOutput = speed + rotation;
            rightMotorOutput = maxInput;
        } else { // speed < 0.0 &&  rotation < 0.0
            leftMotorOutput = maxInput;
            rightMotorOutput = speed - rotation;
        }

        leftSRX.set(ControlMode.PercentOutput, limit(leftMotorOutput));
        rightSRX.set(ControlMode.PercentOutput, limit(rightMotorOutput));
    }

    private double limit(double value) {
        return Math.copySign(Math.abs(value) > 1.0 ? 1.0 : value, value);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
