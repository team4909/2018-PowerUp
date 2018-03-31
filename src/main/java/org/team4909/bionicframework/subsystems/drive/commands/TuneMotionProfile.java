package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.core.RioFS;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

import java.text.DecimalFormat;

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
    private Timer stateTimer = new Timer();
    private double throttle;
    private final double voltageStep = 0.025;
    private boolean isFinished;

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
        RioFS.writeFile("telemetry", "voltage", "OUTPUT VOLTAGE,LINEAR VELOCITY\n");
        RioFS.writeFile("telemetry", "acceleration", "ELAPSED TIME,OUTPUT VOLTAGE,LINEAR VELOCITY\n");
    }

    @Override
    protected void execute() {
        double voltage = (Math.abs(leftSRX.getMotorOutputVoltage()) + Math.abs(rightSRX.getMotorOutputVoltage())) / 2,
                rotations = (bionicDrive.getHeading() / 360),
                distance = bionicDrive.ticksToFeet / 2 *
                        (Math.abs(leftSRX.getSelectedSensorPosition()) + Math.abs(rightSRX.getSelectedSensorPosition())),
                velocity = bionicDrive.ticksToFeet / 2 * 10 *
                        (Math.abs(leftSRX.getSelectedSensorVelocity()) + Math.abs(rightSRX.getSelectedSensorVelocity()));

        switch (state) {
            case Initialization:
                System.out.println("MOTION PROFILE TUNING INIT.");
                bionicDrive.resetHeading();

                state = MPTuningState.TrackwidthRotation;

                break;
            case TrackwidthRotation:
                System.out.println("MOTION PROFILE TUNING TRACKWIDTH ROTATION");
                System.out.println(" - ROTATIONS: " + rotations);

                if (rotations > 10){
                    leftSRX.set(ControlMode.PercentOutput, 0);
                    rightSRX.set(ControlMode.PercentOutput, 0);

                    state = MPTuningState.TrackwidthCalculation;
                } else {
                    leftSRX.set(ControlMode.PercentOutput, 0.45);
                    rightSRX.set(ControlMode.PercentOutput, -0.45);
                }

                break;
            case TrackwidthCalculation:
                double trackwidth = distance / (rotations * Math.PI);

                System.out.println("MOTION PROFILE TUNING TRACKWIDTH CALCULATION");
                System.out.println(" - EMPIRICAL TRACKWIDTH: " + toCSVFormat(trackwidth) + " FEET");

                state = MPTuningState.VoltageRamp;
                stateTimer.reset();
                stateTimer.start();

                break;
            case VoltageRamp:
                System.out.println("MOTION PROFILE TUNING VOLTAGE RAMP");
                System.out.println(" - THROTTLE: " + throttle);

                if(stateTimer.hasPeriodPassed(2)) {
                    state = MPTuningState.VoltageTelemetry;
                } else {
                    leftSRX.set(ControlMode.PercentOutput, throttle);
                    rightSRX.set(ControlMode.PercentOutput, -throttle);
                }

                break;
            case VoltageTelemetry:
                System.out.println("MOTION PROFILE TUNING VOLTAGE TELEMETRY");
                RioFS.appendFile("telemetry", "voltage",
                        toCSVFormat(voltage) + "," + toCSVFormat(velocity) + "\n");

                throttle += voltageStep;

                if (throttle > 1.0) {
                    state = MPTuningState.AccelerationTelemetry;
                    stateTimer.reset();
                    stateTimer.start();
                } else {
                    state = MPTuningState.VoltageRamp;
                }

                break;
            case AccelerationTelemetry:
                System.out.println("MOTION PROFILE TUNING ACCELERATION TELEMETRY");
                if (stateTimer.get() < 1) {
                    leftSRX.set(ControlMode.PercentOutput, 0);
                    rightSRX.set(ControlMode.PercentOutput, 0);
                } else if (stateTimer.get() < 5) {
                    leftSRX.set(ControlMode.PercentOutput, 0.6);
                    rightSRX.set(ControlMode.PercentOutput, -0.6);

                    RioFS.appendFile("telemetry", "acceleration",
                            toCSVFormat(stateTimer.get()) + "," + toCSVFormat(voltage) + "," + toCSVFormat(velocity) + "\n");
                } else {
                    leftSRX.set(ControlMode.PercentOutput, 0);
                    rightSRX.set(ControlMode.PercentOutput, 0);

                    isFinished = true;
                }

                break;
        }

        System.out.println(" - TIME ELAPSED: " + stateTimer.get());
    }

    public String toCSVFormat(double number){
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(4);
        return df.format(number);
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
