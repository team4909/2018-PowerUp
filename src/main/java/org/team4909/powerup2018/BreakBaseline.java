package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class BreakBaseline extends CommandGroup {
    public final double toMeters = 0.3048;

    public BreakBaseline(BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(13* toMeters));

    }
}
