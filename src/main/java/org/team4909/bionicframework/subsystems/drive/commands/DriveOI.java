package org.team4909.bionicframework.subsystems.drive.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.operator.controllers.BionicF310;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class DriveOI extends Command {
    private final BionicDrive bionicDrive;
    private final BionicSRX leftSRX, rightSRX;

    private final BionicF310 speedInputGamepad, rotationInputGamepad;
    private final BionicAxis speedInputAxis, rotationInputAxis;
    public double speedMultiplier, rotationMultiplier;
    private double limitedSpeed, limitedRotation;

    public DriveOI(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX,
                   BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier,
                   BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier) {
        requires(bionicDrive);
        this.bionicDrive = bionicDrive;

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.speedInputGamepad = speedInputGamepad;
        this.speedInputAxis = speedInputAxis;
        this.speedMultiplier = speedMultiplier;

        this.rotationInputGamepad = rotationInputGamepad;
        this.rotationInputAxis = rotationInputAxis;
        this.rotationMultiplier = rotationMultiplier;
    }

    @Override
    protected void execute() {
        if(!DriverStation.getInstance().isAutonomous()){
            // Calculate Change Limited Speed Value
            double speed = speedInputGamepad.getSensitiveAxis(speedInputAxis) * speedMultiplier,
                    speedDelta = speed - limitedSpeed,
                    speedDeltaLimit = bionicDrive.speedDeltaLimit;

            if (Math.abs(speedDelta) > speedDeltaLimit)
                speedDelta = Math.copySign(speedDeltaLimit, speedDelta);
            limitedSpeed += speedDelta;

            // Calculate Change Limited Rotation Value
            double rotation = rotationInputGamepad.getSensitiveAxis(rotationInputAxis) * rotationMultiplier,
                    rotationDelta = rotation - limitedRotation,
                    rotationDeltaLimit = bionicDrive.rotationDeltaLimit;

            if (Math.abs(rotationDelta) > rotationDeltaLimit)
                rotationDelta = Math.copySign(rotationDeltaLimit, rotationDelta);
            limitedRotation += rotationDelta;

            // Calculate Left/Right Percentage Output Values
            double leftMotorOutput, rightMotorOutput;

            if (limitedSpeed > 0.0) {
                if (limitedRotation > 0.0) {
                    leftMotorOutput = limitedSpeed - limitedRotation;
                    rightMotorOutput = Math.max(limitedSpeed, limitedRotation);
                } else {
                    leftMotorOutput = Math.max(limitedSpeed, -limitedRotation);
                    rightMotorOutput = limitedSpeed + limitedRotation;
                }
            } else {
                if (limitedRotation > 0.0) {
                    leftMotorOutput = -Math.max(-limitedSpeed, limitedRotation);
                    rightMotorOutput = limitedSpeed + limitedRotation;
                } else {
                    leftMotorOutput = limitedSpeed - limitedRotation;
                    rightMotorOutput = -Math.max(-limitedSpeed, -limitedRotation);
                }
            }

            // Limit Left/Right Percentage Output to -100% to 100%
            leftMotorOutput = limit(leftMotorOutput);
            rightMotorOutput = limit(rightMotorOutput);

            leftSRX.set(ControlMode.PercentOutput,  leftMotorOutput);
            rightSRX.set(ControlMode.PercentOutput, rightMotorOutput);
        }
    }

    @Override
    protected void interrupted() {
        leftSRX.set(ControlMode.PercentOutput, 0);
        rightSRX.set(ControlMode.PercentOutput, 0);
    }

    private double limit(double value) {
        return Math.copySign(Math.abs(value) > 1.0 ? 1.0 : value, value);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
