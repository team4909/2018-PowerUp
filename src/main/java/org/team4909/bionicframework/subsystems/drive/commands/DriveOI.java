package org.team4909.bionicframework.subsystems.drive.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.operator.controllers.BionicF310;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;

public class DriveOI extends Command {
    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;

    private final BionicF310 speedInputGamepad;
    private final BionicAxis speedInputAxis;
    public double speedMultiplier;

    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;
    public double rotationMultiplier;

    private final DrivetrainConfig drivetrainConfig;

    public DriveOI(BionicDrive subsystem, BionicSRX leftSRX, BionicSRX rightSRX,
                   BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier,
                   BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier) {
        requires(subsystem);
        this.drivetrainConfig = subsystem.pathgen.drivetrainConfig;

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
        double speed = speedInputGamepad.getSensitiveAxis(speedInputAxis) * speedMultiplier;
        double rotation = rotationInputGamepad.getSensitiveAxis(rotationInputAxis) * rotationMultiplier;
        double maxVelocity = drivetrainConfig.getMaxVelocity();
        double maxAccel = drivetrainConfig.getMaxAcceleration();
        double t = drivetrainConfig.getProfileIntervalMs();
        private double lastVelocity = 0;
        private double lastAcceleration = 0;
        private double currentVelocity = getVelocity();
        double currentAcceleration = (currentVelocity - lastVelocity) / t;
        double currentJerk = (currentAcceleration - lastAcceleration) / t;
        double leftMotorOutput;
        double rightMotorOutput;

        if (speed > 0.0) {
            if (rotation > 0.0) {
                leftMotorOutput = speed - rotation;
                rightMotorOutput = Math.max(speed, rotation);
            } else {
                leftMotorOutput = Math.max(speed, -rotation);
                rightMotorOutput = speed + rotation;
            }
        } else {
            if (rotation > 0.0) {
                leftMotorOutput = -Math.max(-speed, rotation);
                rightMotorOutput = speed + rotation;
            } else {
                leftMotorOutput = speed - rotation;
                rightMotorOutput = -Math.max(-speed, -rotation);
            }
        }

        leftMotorOutput = limit(leftMotorOutput);
        rightMotorOutput = limit(rightMotorOutput);

        double leftVelocity = maxVelocity * leftMotorOutput;
        double rightVelocity = maxVelocity * rightMotorOutput;

        if (currentAcceleration >= maxAccel) {
          currentAcceleration = maxAccel;
            leftVelocity = leftVelocity + (currentAcceleration * t);
            rightVelocity = rightVelocity + (currentAcceleration * t);
        }


        leftSRX.set(ControlMode.Velocity, leftVelocity);
        rightSRX.set(ControlMode.Velocity, rightVelocity);
    }

    public double getVelocity(){
        double currentLeftVelocity = (leftSRX.getSelectedSensorVelocity(0) * 10 * drivetrainConfig.getTicksToFeet());
        double currentRightVelocity = (rightSRX.getSelectedSensorVelocity(0) * 10 * drivetrainConfig.getTicksToFeet());

        return (currentLeftVelocity + currentRightVelocity)/2;
    }

    private double limit(double value) {
        return Math.copySign(Math.abs(value) > 1.0 ? 1.0 : value, value);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }


}
