package org.team4909.bionicframework.subsystems.drive.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.operator.BionicF310;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class DriveOI extends Command {
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;

    private final BionicF310 speedInputGamepad;
    private final BionicAxis speedInputAxis;
    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;

    public DriveOI(BionicDrive subsystem, BionicSRX leftSRX, BionicSRX rightSRX,
                   BionicF310 speedInputGamepad, BionicAxis speedInputAxis,
                   BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis) {
        requires(subsystem);

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.speedInputGamepad = speedInputGamepad;
        this.speedInputAxis = speedInputAxis;
        this.rotationInputGamepad = rotationInputGamepad;
        this.rotationInputAxis = rotationInputAxis;
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
