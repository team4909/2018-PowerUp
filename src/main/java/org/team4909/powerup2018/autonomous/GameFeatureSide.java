package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import openrio.powerup.MatchData;

public class GameFeatureSide extends ConditionalCommand {
    MatchData.GameFeature gameFeature;

    public GameFeatureSide(MatchData.GameFeature gameFeature, Command onLeft, Command onRight) {
        super(onLeft, onRight);

        this.gameFeature = gameFeature;
    }

    @Override
    protected boolean condition() {
        System.out.println(MatchData.getOwnedSide(gameFeature));
        return MatchData.getOwnedSide(gameFeature) == MatchData.OwnedSide.LEFT;
    }
}