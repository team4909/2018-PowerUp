package org.team4909.powerup2018;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.Arduino;
import org.team4909.bionicframework.hardware.BionicNavX;
import org.team4909.bionicframework.hardware.BionicSRX;
import org.team4909.bionicframework.hardware.RoboRio;
import org.team4909.bionicframework.hardware.Arduino.State;
import org.team4909.bionicframework.motion.BionicDrive;
import org.team4909.bionicframework.operator.BionicF310;

import jaci.pathfinder.Waypoint;

public class Robot extends RoboRio {
    /* Subsystem Initialization */
    private static Arduino arduino;
    private static BionicDrive drivetrain;

    /* OI Initialization */
    private static BionicF310 driverGamepad;

    /* Auto Commands */
    private static Command autoCommand;

    @Override
    public void robotInit() {
        driverGamepad = new BionicF310(0);

        arduino = new Arduino(4);

        drivetrain = new BionicDrive(new BionicSRX(6/*,4*/), new BionicSRX(5/*,3*/),
                driverGamepad, BionicF310.LY, driverGamepad, BionicF310.RX,
                FeedbackDevice.QuadEncoder, 0.6, 0, 0,
                new BionicNavX(),
                1.0, 2.0, 60.0,
                24.43, 0.5);

        autoCommand = drivetrain.driveWaypoints(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(5, 0, 0)
        });
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
