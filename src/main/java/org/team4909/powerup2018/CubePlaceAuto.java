package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CubePlaceAuto extends CommandGroup {
    public CubePlaceAuto(
                         Command driveCommand){
       // addParallel(elevateCommand);
        addSequential(driveCommand);
        //addSequential(outtakeCommand);
    }
}
