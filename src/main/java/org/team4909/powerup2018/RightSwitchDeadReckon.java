package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class RightSwitchDeadReckon extends CommandGroup {
    public RightSwitchDeadReckon(Command outtakeCommand, Command elevateCommand, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(.41));
        addSequential(drivetrain.driveRotation(24.94));
        addSequential(elevateCommand);
        addSequential(drivetrain.driveDistance(5.63));
        addSequential(drivetrain.driveRotation(-24.94));
        addSequential(drivetrain.driveDistance(4.4));
        addSequential(outtakeCommand);
    }
}
