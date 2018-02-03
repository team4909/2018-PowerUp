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
    private final double speedMultiplier;

    private final BionicF310 rotationInputGamepad;
    private final BionicAxis rotationInputAxis;
    private final double rotationMultiplier;
    private final double maxSpeedDelta;

    private double lastSpeed;

    public DriveOI(BionicDrive subsystem, BionicSRX leftSRX, BionicSRX rightSRX,
                   BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier,
                   BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier) {
        requires(subsystem);
        this.maxSpeedDelta = (1/50) / subsystem.pathgen.drivetrainConfig.getSecondsFromNeutralToFull();

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
//        if(Math.abs(speed - lastSpeed) > maxSpeedDelta)
//            speed = lastSpeed + Math.copySign(maxSpeedDelta, speed);
        lastSpeed = speed;

        double rotation = rotationInputGamepad.getSensitiveAxis(rotationInputAxis) * rotationMultiplier;

        System.out.println("S, R: " + speed + "," + rotation);

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

        System.out.println("L, R: " + leftMotorOutput + "," + rightMotorOutput);
        System.out.println("L, R: " + leftSRX.getSelectedSensorVelocity(0) + "," + rightSRX.getSelectedSensorVelocity(0));

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

    @Override
    protected void end() {
        super.end();
    }
}
