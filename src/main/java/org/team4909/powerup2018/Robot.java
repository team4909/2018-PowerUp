package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.DriverStation;
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
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.Underglow.Commands.*;
import org.team4909.bionicframework.subsystems.Underglow.Underglow;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class Robot extends RoboRio {
    /* Subsystem Initialization */
    private static Arduino arduino;
    private static BionicDrive drivetrain;
    private static IntakeSubsystem intake;
    private static ElevatorSubsystem elevator;
    private static MotorSubsystem winch;
    private static MotorSubsystem hookDeploy;
    private static Underglow underglow;

    /* OI Initialization */
    private static BionicF310 driverGamepad;
    private static BionicF310 manipulatorGamepad;

    /* Auto Commands */
    private static SendableChooser autoChooser;
    private static Command autoCommand;

    @Override
    public void robotInit() {
        driverGamepad = new BionicF310(0, 0.1, 0.8);
        manipulatorGamepad = new BionicF310(1, 0.1, 0.5);
        arduino = new Arduino(4);
        underglow = new Underglow(3, 5, 4);

        drivetrain = new BionicDrive(
                new BionicSRX(
                        2,false,
                        FeedbackDevice.QuadEncoder, false,
                        1,0,0, // P:1.7 I:0 D:7
                        1
                ),
                new BionicSRX(
                        4,true,
                        FeedbackDevice.QuadEncoder, false,
                        1,0,0,
                        4
                ),
                driverGamepad, BionicF310.LY, -1.0, 0.10,
                driverGamepad, BionicF310.RX, -1.0, 0.10,
                new DrivetrainConfig(
                        25, 0.5,360,    // ticksPerRev: 120
                        12.000,11.126,117.809,
                        3,2.74
                ),
                new BionicNavX(),
                new BionicSingleSolenoid(0)
        );
        driverGamepad.buttonPressed(BionicF310.LT, 0.1, drivetrain.invertDirection());
        driverGamepad.buttonPressed(BionicF310.RT, 0.1, drivetrain.changeGear());

        intake = new IntakeSubsystem(0,true,1,false);

        manipulatorGamepad.buttonHeld(BionicF310.LT, 0.1,intake.intake());
        manipulatorGamepad.buttonHeld(BionicF310.RT, 0.1,intake.outtake());
        manipulatorGamepad.buttonHeld(BionicF310.B, intake.outtakeSlow());

        winch = new MotorSubsystem(
                new BionicVictorSP(2, true),
                new BionicVictorSP(3, false)
        );
        driverGamepad.buttonHeld(BionicF310.LB, winch.setPercentOutput(-0.5));
        driverGamepad.buttonHeld(BionicF310.RB, winch.setPercentOutput(1.0));
        driverGamepad.buttonPressed(BionicF310.B, new ColorRed(underglow));
        driverGamepad.buttonPressed(BionicF310.X, new ColorBlue(underglow));
        driverGamepad.buttonPressed(BionicF310.A, new ColorGreen(underglow));
        driverGamepad.buttonPressed(BionicF310.Y, new ColorWhite(underglow));
        driverGamepad.buttonPressed(BionicF310.Start, new ColorPurple(underglow));

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
                33150
        );
        SmartDashboard.putNumber("Time: ", DriverStation.getInstance().getMatchTime());
        SmartDashboard.putBoolean("DS", DriverStation.getInstance().isDSAttached());
        SmartDashboard.putBoolean("FMS", DriverStation.getInstance().isFMSAttached());
        SmartDashboard.putBoolean("Brownout", DriverStation.getInstance().isBrownedOut());


        autoChooser = new SendableChooser();
        autoChooser.addDefault("Do Nothing", null);
        autoChooser.addObject("Break Baseline", new  BreakBaseline(drivetrain));
        autoChooser.addObject("Center Switch L/R", new GameFeatureSide(
                GameFeature.SWITCH_NEAR,
                new LeftSwitchDeadReckon(
                        intake,
                        elevator,
                        drivetrain
                ),
                new RightSwitchDeadReckon(
                        intake,
                        elevator,
                        drivetrain
                )
        ));
        autoChooser.addObject("Left Scale/Switch", new GameFeatureSide(
                GameFeature.SCALE,
                new LeftScaleDeadReckon(
                        intake,
                        elevator,
                        drivetrain
                ),
                new GameFeatureSide(
                        GameFeature.SWITCH_NEAR,
                        new LeftSwitchFromLeft(
                                intake,
                                elevator,
                                drivetrain
                        ),
                        new  BreakBaseline(drivetrain)
                        )
                )
        );
        autoChooser.addObject("Right Scale/Switch", new GameFeatureSide(
                GameFeature.SCALE,
                new GameFeatureSide(
                        GameFeature.SWITCH_NEAR,
                        new  BreakBaseline(drivetrain),
                        new RightSwitchFromRight(
                                intake,
                                elevator,
                                drivetrain
                        )
                ),
                new RightScaleDeadReckon(
                        intake,
                        elevator,
                        drivetrain
                )
        ));
        autoChooser.addObject("DEBUG ONLY: Rotate 90 Degrees", drivetrain.driveRotation(90));
        autoChooser.addObject("DEBUG ONLY: Do Rotation Test", drivetrain.driveRotationTest());
        SmartDashboard.putData( "autochooser", autoChooser);
    }

    @Override
    public void robotPeriodic(){
        super.robotPeriodic();
        //System.out.println(drivetrain.getHeading());
    }
    @Override
    public void teleopPeriodic() {
        //System.out.println(drivetrain.getHeading());

        hookDeploy.set(manipulatorGamepad, BionicF310.RY, 0.5);
    }


    @Override
    public void autonomousInit() {
        super.autonomousInit();

        if (autoCommand != null) {
            autoCommand.cancel();
        }

        autoCommand = (Command) autoChooser.getSelected();
        if (autoCommand != null) {
            autoCommand.start();
        }
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
        try {
            arduino.send(Arduino.State.enabled);
        }catch(Exception e){
            System.out.println("No Arduino");
        }
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    protected void dashboardPeriodic() {
        drivetrain.profiling = SmartDashboard.getBoolean("Drivetrain Profiling", false);
        SmartDashboard.putBoolean("Drivetrain Profiling", drivetrain.profiling);

        drivetrain.encoderOverride = SmartDashboard.getBoolean("Drivetrain Encoder Override", false);
        SmartDashboard.putBoolean("Drivetrain Encoder Override", drivetrain.encoderOverride);

        SmartDashboard.putBoolean("Is High Gear?", drivetrain.getGear());

//        drivetrain.speedDeltaLimit = elevator.getCurrentPosition() * .10;

        elevator.encoderOverride = SmartDashboard.getBoolean("Elevator Encoder Override", false);
        SmartDashboard.putBoolean("Elevator Encoder Override", elevator.encoderOverride);
    }

    @Override
    protected void robotEnabled() {
        drivetrain.resetProfiling();
        elevator.holdCurrentPosition();
    }

    @Override
    protected void robotDisabled() {
        underglow.setGreen();
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }
}
