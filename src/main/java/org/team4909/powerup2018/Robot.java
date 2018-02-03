package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.sun.xml.internal.ws.server.DraconianValidationErrorHandler;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.core.Arduino;
import org.team4909.bionicframework.hardware.core.Arduino.State;
import org.team4909.bionicframework.hardware.core.RoboRio;
import org.team4909.bionicframework.hardware.gyro.BionicNavX;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.motor.BionicSpark;
import org.team4909.bionicframework.hardware.motor.MotorGroup;
import org.team4909.bionicframework.hardware.motor.BionicVictorSP;
import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.hardware.sensors.SRXEncoder;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;
import org.team4909.bionicframework.operator.BionicF310;

public class Robot extends RoboRio {
    /* Drivetrain Config */
    private static DrivetrainConfig practiceBotConfig = new DrivetrainConfig(
            10,0.7,
            0.5, 360,
            12915,1.2,
            5,2);
    private static DrivetrainConfig competetitionBotConfig = new DrivetrainConfig(
            10,0.7,
            0.5, 360 / 3,
            12915,1.2,
            5,2);

    /* Subsystem Initialization */
    private static Arduino arduino;
    private static BionicDrive drivetrain;
    private static MotorGroup intake;
    private static BionicSRX elevator;
    private static MotorGroup winch;
    private static MotorGroup hookDeploy;

    /* OI Initialization */
    private static BionicF310 driverGamepad;
    private static BionicF310 manipulatorGamepad;

    /* Auto Commands */
    private static Command autoCommand;

    @Override
    public void robotInit() {
        driverGamepad = new BionicF310(0, 0.15, 0.8);
        manipulatorGamepad = new BionicF310(1, 0.15, 0.5);

        drivetrain = new BionicDrive(
                new BionicSRX(
                        2,true,
                        FeedbackDevice.QuadEncoder, true,
                        0.6,0,0,1023,
                        1
                ),
                new BionicSRX(
                        4,false,
                        FeedbackDevice.QuadEncoder, false,
                        0.6,0,0,1023,
                        4
                ),
                driverGamepad, BionicF310.LY, 1.0,
                driverGamepad, BionicF310.RX, -1.0,
                competetitionBotConfig,
                new BionicNavX(),
                new BionicSingleSolenoid(0)
        );
        driverGamepad.buttonPressed(BionicF310.A, drivetrain.shiftGear(false));
        driverGamepad.buttonPressed(BionicF310.B, drivetrain.shiftGear(true));

        intake = new MotorGroup(
                new BionicSpark(0, true),
                new BionicSpark(1, false)
        );
        driverGamepad.buttonHeld(BionicF310.LB, intake.setPercentOutput(1.0));
        driverGamepad.buttonHeld(BionicF310.RB, intake.setPercentOutput(-1.0));

        winch = new MotorGroup(
                new BionicVictorSP(2),
                new BionicVictorSP(3)
        );
        driverGamepad.buttonHeld(BionicF310.RT, winch.setPercentOutput(1.0));
        driverGamepad.buttonHeld(BionicF310.LT, winch.setPercentOutput(-0.5));

        hookDeploy = new MotorGroup(
                new BionicSpark(4)
        );

        elevator = new BionicSRX(3, true);
    }

    @Override
    public void teleopPeriodic() {
        elevator.set(manipulatorGamepad, BionicF310.LY);
        hookDeploy.set(manipulatorGamepad, BionicF310.RY);
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
    protected void robotDisabled() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }
}
