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
    private final double speedMultiplier;

    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;
    private final double rotationMultiplier;
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
    protected void initialize() {
        super.initialize();

        leftSRX.setNeutralMode(NeutralMode.Coast);
        rightSRX.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected void execute() {
        double speed = speedInputGamepad.getSensitiveAxis(speedInputAxis) * speedMultiplier;
        double rotation = rotationInputGamepad.getSensitiveAxis(rotationInputAxis) * rotationMultiplier;
        double maxVelocityTicks = drivetrainConfig.getMaxVelocityTicks();

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

        leftSRX.set(leftMotorOutput);
        rightSRX.set(rightMotorOutput);

//        leftSRX.set(ControlMode.Velocity, maxVelocityTicks * limit(leftMotorOutput));
//        rightSRX.set(ControlMode.Velocity, maxVelocityTicks * limit(rightMotorOutput));
    }

    private double limit(double value) {
        return Math.copySign(Math.abs(value) > 1.0 ? 1.0 : value, value);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        super.end();
    }
}
