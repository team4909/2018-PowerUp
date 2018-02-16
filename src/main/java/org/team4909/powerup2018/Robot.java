package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.core.Arduino;
import org.team4909.bionicframework.hardware.core.RoboRio;
import org.team4909.bionicframework.hardware.sensors.gyro.BionicNavX;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.motor.BionicSpark;
import org.team4909.bionicframework.hardware.motor.BionicVictorSP;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;
import org.team4909.bionicframework.operator.controllers.BionicF310;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class Robot extends RoboRio {
    /* Subsystem Initialization */
    private static Arduino arduino;
    private static BionicDrive drivetrain;
    private static MotorSubsystem intake;
    private static ElevatorSubsystem elevator;
    private static MotorSubsystem winch;
    private static MotorSubsystem hookDeploy;

    /* OI Initialization */
    private static BionicF310 driverGamepad;
    private static BionicF310 manipulatorGamepad;

    /* Auto Commands */
    private static Command autoCommand;

    @Override
    public void robotInit() {
        driverGamepad = new BionicF310(0, 0.1, 0.8);
        manipulatorGamepad = new BionicF310(1, 0.1, 0.5);

        drivetrain = new BionicDrive(
                new BionicSRX(
                        2,true,
                        FeedbackDevice.QuadEncoder, true,
                        0.6,0,0,
                        1
                ),
                new BionicSRX(
                        4,false,
                        FeedbackDevice.QuadEncoder, true,
                        0.6,0,0,
                        4
                ),
                driverGamepad, BionicF310.LY, 1.0, 0.5,
                driverGamepad, BionicF310.RX, 1.0, 1.0,
                new DrivetrainConfig(
                        50, 0.5,120,
                        6.332,100,104.720,
                        0,0
                ),
                new BionicNavX(),
                new BionicSingleSolenoid(0),
                true
        );
        driverGamepad.buttonPressed(BionicF310.LT, 0.1, drivetrain.invertDirection());
        driverGamepad.buttonPressed(BionicF310.RT, 0.1, drivetrain.changeGear());

        intake = new MotorSubsystem(
                new BionicSpark(0, true),
                new BionicSpark(1, false)
        );
        manipulatorGamepad.buttonHeld(BionicF310.LT, 0.1,intake.setPercentOutput(1.0));
        manipulatorGamepad.buttonHeld(BionicF310.RT, 0.1,intake.setPercentOutput(-1.0));
        manipulatorGamepad.buttonHeld(BionicF310.B, intake.setPercentOutput(-0.5));

        winch = new MotorSubsystem(
                new BionicVictorSP(2, true),
                new BionicVictorSP(3, false)
        );
        driverGamepad.buttonHeld(BionicF310.LB, winch.setPercentOutput(-0.5));
        driverGamepad.buttonHeld(BionicF310.RB, winch.setPercentOutput(1.0));

        hookDeploy = new MotorSubsystem(
                new BionicSpark(4,false)
        );

        elevator = new ElevatorSubsystem(
                new BionicSRX(
                        3, true,
                        FeedbackDevice.CTRE_MagEncoder_Relative, false,
                        1.0,0,0
                ),
                manipulatorGamepad, BionicF310.LY,-1,
                35250
        );
        driverGamepad.buttonPressed(BionicF310.X, elevator.holdPosition(15000));
    }

    @Override
    public void teleopPeriodic() {
        hookDeploy.set(manipulatorGamepad, BionicF310.RY, 0.5);
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();

        if (autoCommand != null) {
            autoCommand.start();
        }
    }

    @Override
    public void teleopInit() {
        super.teleopInit();

        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    public void robotPeriodic() {
        drivetrain.encoderOverride = SmartDashboard.getBoolean("Drivetrain Encoder Override", false);
        SmartDashboard.putBoolean("Is High Gear?", drivetrain.getGear());

//        drivetrain.speedDeltaLimit = elevator.getCurrentPosition() * .10;

        elevator.encoderOverride = SmartDashboard.getBoolean("Elevator Encoder Override", false);
    }

    @Override
    protected void robotEnabled() {
        elevator.holdCurrentPosition();
    }

    @Override
    protected void robotDisabled() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }
}
