package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class RightScaleDeadReckon extends CommandGroup {
    public RightScaleDeadReckon(Command outtakeCommand, Command elevateCommand, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(.41));
        addSequential(drivetrain.driveRotation(65.06));
        addSequential(drivetrain.driveDistance(5.63));
        addSequential(drivetrain.driveRotation(-65.06));
        addParallel(elevateCommand);
        addSequential(drivetrain.driveDistance(4.4));
        addSequential(outtakeCommand);
    }
}
