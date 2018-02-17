package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import jaci.pathfinder.Waypoint;
import openrio.powerup.MatchData;
import openrio.powerup.MatchData.GameFeature;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class CubePlaceAuto extends CommandGroup {
    public CubePlaceAuto(Command outtakeCommand, Command elevateCommand,
                         Command driveCommand){
        addParallel(elevateCommand);
        addSequential(driveCommand);
        addSequential(outtakeCommand);
    }
}
