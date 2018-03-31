package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.core.RioFS;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class TuneMotionProfile extends Command {
    private final BionicDrive bionicDrive;
    private final BionicSRX leftSRX, rightSRX;

    private enum MPTuningState {
        Initialization,
        TrackwidthRotation,
        TrackwidthCalculation,
        VoltageRamp,
        VoltageTelemetry,
        AccelerationTelemetry
    }

    private MPTuningState state = MPTuningState.Initialization;
    private Timer stateTimer = new Timer(),
            accelerationTimer = new Timer();
    private double throttle = 0,
            lastVelocity = 0;
    private final double voltageStep = 0.025;
    private boolean isFinished = false;

    public TuneMotionProfile(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX){
        requires(bionicDrive);

        this.bionicDrive = bionicDrive;
        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;
    }

    @Override
    protected void initialize() {
        System.out.println("MOTION PROFILE TUNING STARTED");

        RioFS.makeDir("telemetry");
        RioFS.writeFile("telemetry", "voltage", "ELAPSED TIME, LINEAR DISTANCE, OUTPUT VOLTAGE, LINEAR VELOCITY");
        RioFS.writeFile("telemetry", "acceleration", "ELAPSED TIME, OUTPUT VOLTAGE, LINEAR VELOCITY, COMPUTED ACCELERATION");
    }

    @Override
    protected void execute() {
        double voltage = (leftSRX.getMotorOutputVoltage() + rightSRX.getMotorOutputVoltage()) / 2,
                rotations = (bionicDrive.getHeading() / 360),
                distance = bionicDrive.ticksToFeet / 2 *
                        (Math.abs(leftSRX.getSelectedSensorPosition()) + Math.abs(rightSRX.getSelectedSensorPosition())),
                velocity = bionicDrive.ticksToFeet / 2 * 10 *
                        (Math.abs(leftSRX.getSelectedSensorVelocity()) + Math.abs(rightSRX.getSelectedSensorVelocity()));

        switch (state) {
            case Initialization:
                leftSRX.zeroEncoderPosition();
                rightSRX.zeroEncoderPosition();
                bionicDrive.resetHeading();

                state = MPTuningState.TrackwidthRotation;
                break;
            case TrackwidthRotation:
                if (rotations > 10){
                    leftSRX.set(ControlMode.PercentOutput, 0);
                    rightSRX.set(ControlMode.PercentOutput, 0);

                    state = MPTuningState.TrackwidthCalculation;
                } else {
                    System.out.println("ROTATIONS: " + rotations);
                    leftSRX.set(ControlMode.PercentOutput, 0.45);
                    rightSRX.set(ControlMode.PercentOutput, -0.45);
                }

                break;
            case TrackwidthCalculation:
                double trackwidth = distance / (rotations * Math.PI);

                System.out.println("EMPIRICAL TRACKWIDTH: " + trackwidth + " FEET");

                state = MPTuningState.VoltageRamp;
                stateTimer.reset();
                break;
            case VoltageRamp:
                if(stateTimer.hasPeriodPassed(2000)) {
                    state = MPTuningState.VoltageTelemetry;
                } else {
                    leftSRX.set(ControlMode.PercentOutput, throttle);
                    rightSRX.set(ControlMode.PercentOutput, -throttle);
                }

                break;
            case VoltageTelemetry:
                RioFS.appendFile("telemetry", "voltage",
                        stateTimer.get() + "," + distance + "," + voltage + "," + velocity + "\n");

                throttle += voltageStep;

                if (throttle > 1.0) {
                    state = MPTuningState.AccelerationTelemetry;
                } else {
                    state = MPTuningState.VoltageRamp;
                }

                stateTimer.reset();
                break;
            case AccelerationTelemetry:
                if (!stateTimer.hasPeriodPassed(1000)) {
                    leftSRX.set(ControlMode.PercentOutput, 0);
                    rightSRX.set(ControlMode.PercentOutput, 0);
                } else if (!stateTimer.hasPeriodPassed(5000)) {
                    leftSRX.set(ControlMode.PercentOutput, 0.6);
                    rightSRX.set(ControlMode.PercentOutput, -0.6);

                    double dt = accelerationTimer.get() / 1000.0,
                            acceleration = (velocity - lastVelocity) / (dt);
                    lastVelocity = velocity;

                    RioFS.appendFile("telemetry", "acceleration",
                            stateTimer.get() + "," + voltage + "," + velocity + "," + acceleration + "\n");
                } else {
                    isFinished = true;
                }

                accelerationTimer.reset();
                break;
        }
    }

    @Override
    protected void end() {
        System.out.println("MOTION PROFILE TUNING COMPLETE");
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }
}
