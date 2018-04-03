package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import openrio.powerup.MatchData.GameFeature;
import org.team4909.bionicframework.hardware.core.Arduino;
import org.team4909.bionicframework.hardware.core.RoboRio;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.motor.BionicSpark;
import org.team4909.bionicframework.hardware.motor.BionicVictorSP;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.hardware.sensors.gyro.BionicNavX;
import org.team4909.bionicframework.operator.controllers.BionicF310;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.commands.TuneMotionProfile;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;
import org.team4909.bionicframework.subsystems.leds.arduino.Neopixels;
import org.team4909.bionicframework.subsystems.leds.pcm.RGBStrip;
import org.team4909.powerup2018.autonomous.*;

public class Robot extends RoboRio {
    /* Controller Initialization */
    private static BionicF310 driverGamepad;
    private static BionicF310 manipulatorGamepad;
    private static BionicF310 debugGamepad;

    /* Subsystem Initialization */
    private static BionicDrive drivetrain;
    private static ElevatorSubsystem elevator;
    private static MotorSubsystem intake;
    private static MotorSubsystem winch;
    private static MotorSubsystem hookDeploy;

    /* Cosmetic Subsystems */
    private static Arduino arduino;
    private static Neopixels lightSaberNeopixels;
    private static RGBStrip underglowLEDs;
    private static SendableChooser underglowChooser = new SendableChooser();

    @Override
    protected void controllerInit() {
        driverGamepad = new BionicF310(0, 0.1, 0.8);
        manipulatorGamepad = new BionicF310(1, 0.1, 0.5);
        debugGamepad = new BionicF310(2, 0.1, 0.5);
    }

    @Override
    public void controllerPeriodic() {
        hookDeploy.set(manipulatorGamepad, BionicF310.RY, 0.5);
    }

    @Override
    protected void subsystemInit() {
        /*** Drivetrain ***/
        drivetrain = new BionicDrive(
                new BionicSRX(
                        2, false,
                        FeedbackDevice.QuadEncoder, false,
                        1
                ),
                new BionicSRX(
                        4, true,
                        FeedbackDevice.QuadEncoder, false,
                        4
                ),
                driverGamepad, BionicF310.LY, -1.0, 0.10,
                driverGamepad, BionicF310.RX, -0.75, 0.10,
                new BionicNavX(),
                0.5,360,
                2.5024,
                12.19,25.3945, 673.3811,
                0.8201,4.0998,1.4683,
                0.8,0
        );
        driverGamepad.buttonPressed(BionicF310.LT, 0.1, drivetrain.invertDirection());
        driverGamepad.buttonPressed(BionicF310.RT, 0.1, drivetrain.changeGear());

        /*** Elevator ***/
        elevator = new ElevatorSubsystem(
                new BionicSRX(
                        3, true,
                        FeedbackDevice.CTRE_MagEncoder_Relative, false,
                        1.0, 0, 0,
                        3
                ),
                manipulatorGamepad, BionicF310.LY, -1,
                33150
        );
        manipulatorGamepad.povActive(BionicF310.Top, elevator.holdPosition(28400));
        manipulatorGamepad.povActive(BionicF310.Left, elevator.holdPosition(11000));
        manipulatorGamepad.povActive(BionicF310.Right, elevator.holdPosition(11000));
        manipulatorGamepad.povActive(BionicF310.Bottom, elevator.holdPosition(1410));

        /*** Intake ***/
        intake = new MotorSubsystem(
                new BionicSpark(0, true),
                new BionicSpark(1, false)
        );
        manipulatorGamepad.buttonHeld(BionicF310.LT, 0.1, intake.setPercentOutput(1.0));
        manipulatorGamepad.buttonHeld(BionicF310.RT, 0.1, intake.setPercentOutput(-1.0));
        manipulatorGamepad.buttonHeld(BionicF310.B, intake.setPercentOutput(-0.5));

        /*** Winch ***/
        winch = new MotorSubsystem(
                new BionicVictorSP(2, true),
                new BionicVictorSP(3, true)
        );
        debugGamepad.buttonHeld(BionicF310.LB, winch.setPercentOutput(-0.5));
        driverGamepad.buttonHeld(BionicF310.RB, winch.setPercentOutput(1.0));

        /*** Hook Deploy ***/
        hookDeploy = new MotorSubsystem(
                new BionicSpark(4, false)
        );

        /*** Climber LEDs ***/
        arduino = new Arduino(4);
        lightSaberNeopixels = new Neopixels(arduino, 5, 32);
        driverGamepad.povActive(BionicF310.Top, lightSaberNeopixels.set(Neopixels.Pattern.LightSaber));
        driverGamepad.povActive(BionicF310.Bottom, lightSaberNeopixels.set(Neopixels.Color.BionicGreen));
        driverGamepad.povActive(BionicF310.TopRight, lightSaberNeopixels.set(Neopixels.Pattern.LevelUp));
        driverGamepad.povActive(BionicF310.TopLeft, lightSaberNeopixels.set(Neopixels.Pattern.PingPong));
        driverGamepad.povActive(BionicF310.Right, lightSaberNeopixels.set(Neopixels.Pattern.RainbowSegment));
        driverGamepad.povActive(BionicF310.Left, lightSaberNeopixels.set(Neopixels.Pattern.RainbowStrip));
        driverGamepad.povActive(BionicF310.BottomRight, lightSaberNeopixels.set(Neopixels.Pattern.Fire));
        driverGamepad.povActive(BionicF310.BottomLeft, lightSaberNeopixels.set(Neopixels.Color.Random));

        /*** Underglow LEDs ***/
        underglowLEDs = new RGBStrip(3, 5, 4);
        driverGamepad.buttonPressed(BionicF310.Back, underglowLEDs.setAllianceColor());
        driverGamepad.buttonPressed(BionicF310.Start, underglowLEDs.set(RGBStrip.Color.Magenta));
        driverGamepad.buttonPressed(BionicF310.A, underglowLEDs.set(RGBStrip.Color.Lime));
        driverGamepad.buttonPressed(BionicF310.B, underglowLEDs.set(RGBStrip.Color.White));
        driverGamepad.buttonPressed(BionicF310.Y, underglowLEDs.set(RGBStrip.Color.Yellow));
        driverGamepad.buttonPressed(BionicF310.X, underglowLEDs.set(RGBStrip.Color.Cyan));

        underglowChooser.addDefault("Alliance Color", underglowLEDs.setAllianceColor());
        underglowChooser.addObject("Black", underglowLEDs.set(RGBStrip.Color.Black));
        underglowChooser.addObject("White", underglowLEDs.set(RGBStrip.Color.White));
        underglowChooser.addObject("Lime", underglowLEDs.set(RGBStrip.Color.Lime));
        underglowChooser.addObject("Yellow", underglowLEDs.set(RGBStrip.Color.Yellow));
        underglowChooser.addObject("Cyan", underglowLEDs.set(RGBStrip.Color.Cyan));
        underglowChooser.addObject("Magenta", underglowLEDs.set(RGBStrip.Color.Magenta));
        SmartDashboard.putData("Underglow Color: ", underglowChooser);

        /*** Camera Server ***/
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    protected void autoChooserInit() {
        autoChooser.addObject("Break Baseline", new BreakBaseline(drivetrain));
        autoChooser.addObject("Left Start - Scale PREFERRED", new GameFeatureSide(
                GameFeature.SCALE,
                new LeftScaleFromLeft(intake, elevator, drivetrain),
                new GameFeatureSide(
                        GameFeature.SWITCH_NEAR,
                        new LeftSwitchFromLeft(intake, elevator, drivetrain),
                        new BreakBaseline(drivetrain)
                )
        ));
        autoChooser.addObject("Left Start - Switch PREFERRED", new GameFeatureSide(
                GameFeature.SWITCH_NEAR,
                new LeftSwitchFromLeft(intake, elevator, drivetrain),
                new GameFeatureSide(
                        GameFeature.SCALE,
                        new LeftScaleFromLeft(intake, elevator, drivetrain),
                        new BreakBaseline(drivetrain)
                )
        ));
        autoChooser.addObject("Left Start - Switch ONLY", new GameFeatureSide(
                GameFeature.SWITCH_NEAR,
                new LeftSwitchFromLeft(intake, elevator, drivetrain),
                new BreakBaseline(drivetrain)
        ));
        autoChooser.addObject("Center Start - Switch ONLY", new GameFeatureSide(
                GameFeature.SWITCH_NEAR,
                new LeftSwitchFromCenter(intake, elevator, drivetrain),
                new RightSwitchFromCenter(intake, elevator, drivetrain)
        ));
        autoChooser.addObject("Right Start - Switch ONLY", new GameFeatureSide(
                GameFeature.SWITCH_NEAR,
                new BreakBaseline(drivetrain),
                new RightSwitchFromRight(intake, elevator, drivetrain)
        ));
        autoChooser.addObject("Right Start - Switch PREFERRED", new GameFeatureSide(
                GameFeature.SWITCH_NEAR,
                new GameFeatureSide(
                        GameFeature.SCALE,
                        new BreakBaseline(drivetrain),
                        new RightScaleFromRight(intake, elevator, drivetrain)
                ),
                new RightSwitchFromRight(intake, elevator, drivetrain)
        ));
        autoChooser.addObject("Right Start - Scale PREFERRED", new GameFeatureSide(
                GameFeature.SCALE,
                new GameFeatureSide(
                        GameFeature.SWITCH_NEAR,
                        new BreakBaseline(drivetrain),
                        new RightSwitchFromRight(intake, elevator, drivetrain)
                ),
                new RightScaleFromRight(intake, elevator, drivetrain)
        ));
        autoChooser.addObject("DEBUG: Tune Motion Profile", drivetrain.tuneMotionProfile());
    }

    @Override
    protected void dashboardPeriodic() {
        super.dashboardPeriodic();

        drivetrain.encoderOverride = SmartDashboard.getBoolean("Drivetrain Encoder Override", false);
        SmartDashboard.putBoolean("Drivetrain Encoder Override", drivetrain.encoderOverride);
        SmartDashboard.putBoolean("Is High Gear?", drivetrain.getGear());

        elevator.encoderOverride = SmartDashboard.getBoolean("Elevator Encoder Override", false);
        SmartDashboard.putBoolean("Elevator Encoder Override", elevator.encoderOverride);
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();

        ((Command) underglowChooser.getSelected()).start();
    }

    @Override
    public void robotPeriodic() {
        super.robotPeriodic();

        double elevatorCoefficient = (.02 / 34000);

        if (elevator.getCurrentPosition() > 20000) {
            drivetrain.speedDeltaLimit = 0.0085;
        } else {
            drivetrain.speedDeltaLimit = 0.04 - (elevatorCoefficient * elevator.getCurrentPosition());
        }

        drivetrain.rotationDeltaLimit = 0.04 - (elevatorCoefficient * elevator.getCurrentPosition());
    }

    @Override
    protected void robotEnabled() {
        super.robotEnabled();

        elevator.holdCurrentPosition();
    }
}
