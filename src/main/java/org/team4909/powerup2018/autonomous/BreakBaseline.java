package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class BreakBaseline extends CommandGroup {
    public BreakBaseline(BionicDrive drivetrain){
//        addParallel(Robot.intakeRotator.setPercentOutputPeriodic(.5), 1);
        addSequential(drivetrain.driveDistance(13));
    }
}
