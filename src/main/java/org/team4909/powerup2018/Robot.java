package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.core.Arduino;
import org.team4909.bionicframework.hardware.core.Arduino.State;
import org.team4909.bionicframework.hardware.core.RoboRio;
import org.team4909.bionicframework.hardware.gyro.BionicNavX;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.motor.BionicMotorGroup;
import org.team4909.bionicframework.hardware.motor.BionicVictorSP;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.hardware.sensors.BionicSRXEncoder;
import org.team4909.bionicframework.subsystems.drive.MotionProfileConfig;
import org.team4909.bionicframework.operator.BionicF310;

public class Robot extends RoboRio {
    /* Subsystem Initialization */
    private static Arduino arduino;
    private static BionicDrive drivetrain;
    private static BionicMotorGroup intake;
    private static BionicSRX elevator;

    /* OI Initialization */
    private static BionicF310 driverGamepad;

    /* Auto Commands */
    private static Command autoCommand;

    @Override
    public void robotInit() {
        driverGamepad = new BionicF310(0, 0.15, 0.8);

        arduino = new Arduino(4);

        drivetrain = new BionicDrive(new BionicSRX(2,1), new BionicSRX(4,4),
                driverGamepad, BionicF310.LY, driverGamepad, BionicF310.RX,
                new BionicSRXEncoder(FeedbackDevice.QuadEncoder, true, 0.6,0,0),
                new MotionProfileConfig(
                        10,0.8,0.5,
                        12915,1.2,
                        5,2),
                new BionicNavX());

        intake = new BionicMotorGroup(
                new BionicVictorSP(0),
                new BionicVictorSP(1)
        );

        elevator = new BionicSRX(3);

        driverGamepad.buttonHeld(BionicF310.LB, intake.setPercentOutput(-1.0));
        driverGamepad.buttonHeld(BionicF310.RB, intake.setPercentOutput(1.0));

        driverGamepad.buttonHeld(BionicF310.LT, 0.15, elevator.setPercentOutput(-1.0));
        driverGamepad.buttonHeld(BionicF310.RT, 0.15, elevator.setPercentOutput(1.0));

        autoCommand = drivetrain.driveRotationTest();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();

        autoCommand.start();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();

        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    protected void robotEnabled() {
        arduino.send(State.enabled).initialize();
    }

    @Override
    protected void robotDisabled() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }

        arduino.send(State.disabled).initialize();
    }
}
