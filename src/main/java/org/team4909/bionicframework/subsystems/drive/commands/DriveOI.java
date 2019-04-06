package org.team4909.bionicframework.subsystems.drive.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team4909.powerup2018.Robot;
import org.team4909.bionicframework.operator.controllers.BionicF310;

public class DriveOI extends Command {
    public DriveOI() {
        requires(Robot.drivetrain);
    }

    public void execute() {
        // Calls tank function using left Y and right Y
        // Negated since Y Axis is scaled -1 to +1 top to bottom (counterintuively)
        Robot.drivetrain.arcadeDrive(Robot.driverGamepad.getSensitiveAxis(BionicF310.LY),
                Robot.driverGamepad.getSensitiveAxis(BionicF310.RX));
        System.out.println("here1");

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
