package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class RightSwitchDeadReckon extends CommandGroup {
    public RightSwitchDeadReckon(IntakeSubsystem intake, Command elevateCommand, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(0));
        addSequential(drivetrain.driveRotation(0.422));
        /*
        addSequential(elevateCommand);
        */
        addSequential(drivetrain.driveDistance(0));
        addSequential(drivetrain.driveRotation(-0.422));
        /*
        addSequential(drivetrain.driveDistance(4.4));
        addSequential(intake.outtake());
        addSequential(new WaitCommand(3));
        addSequential(intake.cancel());
        */
    }
}