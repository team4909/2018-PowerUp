package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class DriveRotate extends PIDCommand {
    private final BionicDrive bionicDrive;
    private final BionicSRX leftSRX, rightSRX;
    private int direction = 0;

    public DriveRotate(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX, double angle) {
        super(.007,.00001,0);

        requires(bionicDrive);

        this.bionicDrive = bionicDrive;
        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        if(angle<0){
            direction = -1;
        }else{
            direction = 1;
        }
        //getPIDController().setSetpoint(angle / 3);
        //System.out.println("HI");
        getPIDController().setSetpoint(Math.abs(angle));
    }

    @Override
    protected void initialize() {
        bionicDrive.resetHeading();
    }

    @Override
    protected boolean isFinished() {

        return getPIDController().getError() < 1.5;
    }

    @Override
    protected double returnPIDInput() {
        return Math.abs(bionicDrive.getHeading());
    }


    @Override
    protected void usePIDOutput(double output) {
        output = output * direction;
        System.out.println("Output: " + output + " Heading: " + bionicDrive.getHeading());
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