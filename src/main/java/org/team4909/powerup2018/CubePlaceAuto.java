package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import jaci.pathfinder.Waypoint;
import openrio.powerup.MatchData;
import openrio.powerup.MatchData.GameFeature;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class CubePlaceAuto extends CommandGroup {
    public CubePlaceAuto(Command intakeCommand, Command outtakeCommand,
                         Command elevateCommand,
                         GameFeature side, BionicDrive drivetrain,
                         Waypoint[] leftPath, Waypoint[] rightPath){
        addParallel(intakeCommand);
        addParallel(elevateCommand);
        addSequential(new GameFeatureSide(
                side,
                drivetrain.driveWaypoints(leftPath),
                drivetrain.driveWaypoints(rightPath)
        ));
        addSequential(outtakeCommand);
    }

    private class GameFeatureSide extends ConditionalCommand {
        GameFeature gameFeature;

        public GameFeatureSide(GameFeature gameFeature, Command onLeft, Command onRight) {
            super(onLeft, onRight);

            this.gameFeature = gameFeature;
        }

        @Override
        protected boolean condition() {
            return MatchData.getOwnedSide(gameFeature) == MatchData.OwnedSide.LEFT;
        }
    }

}
