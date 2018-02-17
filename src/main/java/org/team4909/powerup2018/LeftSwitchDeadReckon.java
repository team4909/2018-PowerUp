package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class LeftSwitchDeadReckon extends CommandGroup {
    public LeftSwitchDeadReckon(BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(.41));
        addSequential(drivetrain.driveRotation(-65.06));
        addSequential(drivetrain.driveDistance(5.63));
        addSequential(drivetrain.driveRotation(65.06));
        addSequential(drivetrain.driveDistance(4.4));
    }
}
