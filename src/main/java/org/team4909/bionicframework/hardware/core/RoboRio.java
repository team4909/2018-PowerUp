package org.team4909.bionicframework.hardware.core;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Generic roboRio Utilities to Abstract the Robot into Distinguishable Parts
 */
public abstract class RoboRio extends TimedRobot {
    protected PowerDistributionPanel powerDistributionPanel = new PowerDistributionPanel();
    protected SendableChooser autoChooser = new SendableChooser();
    private Command autoCommand;

    /**
     * Called from Robot Periodic to Separate all Dashboard Logging
     */
    protected void dashboardPeriodic() {
        SmartDashboard.putNumber("Time: ", DriverStation.getInstance().getMatchTime());
        SmartDashboard.putBoolean("DS", DriverStation.getInstance().isDSAttached());
        SmartDashboard.putBoolean("FMS", DriverStation.getInstance().isFMSAttached());
    }

    @Override
    public void robotInit() {
        controllerInit();
        subsystemInit();

        autoChooser.addDefault("Do Nothing", null);
        autoChooserInit();
        SmartDashboard.putData("Autonomous Mode: ", autoChooser);

    }

    protected void robotEnabled() {
    }

    protected void controllerInit() {
    }

    protected void controllerPeriodic() {
    }

    protected void subsystemInit() {
    }

    protected void autoChooserInit() {
    }

    /**
     * Called from Disabled Init
     */
    protected void robotDisabled() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();

        dashboardPeriodic();
        controllerPeriodic();
    }

    @Override
    public void autonomousInit() {
        robotEnabled();

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
        robotEnabled();

        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    public void disabledInit() {
        robotDisabled();
    }
}
