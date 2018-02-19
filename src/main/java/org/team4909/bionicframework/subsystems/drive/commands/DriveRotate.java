package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class DriveRotate extends PIDCommand {
    private final BionicDrive bionicDrive;
    private final BionicSRX leftSRX, rightSRX;

    public DriveRotate(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX, double angle) {
        super(.0065,.000005,0); // Old d Value .2 p .7

        requires(bionicDrive);

        this.bionicDrive = bionicDrive;
        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        //getPIDController().setSetpoint(angle / 3);
        //System.out.println("HI");
        getPIDController().setSetpoint(angle);
    }

    @Override
    protected void initialize() {
        bionicDrive.resetProfiling();
//        getPIDController().reset();
//        getPIDController().setSetpoint(mAngle);
//        System.out.println("INIT");
//        System.out.println("P " + this.getPIDController().getP() + " I " + this.getPIDController().getI() + " D " + this.getPIDController().getD());
    }

    @Override
    protected boolean isFinished() {

        return getPIDController().getError() < 0.06;
    }

    @Override
    protected double returnPIDInput() {
        return bionicDrive.getHeading();
    }


    @Override
    protected void usePIDOutput(double output) {
        double limitedSpeed = 0;
        double limitedRotation = -output;

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

    private double limit(double value) {
        return Math.copySign(Math.abs(value) > 1.0 ? 1.0 : value, value);
    }

    @Override
    protected void end() {
        leftSRX.set(0);
        rightSRX.set(0);
    }
}