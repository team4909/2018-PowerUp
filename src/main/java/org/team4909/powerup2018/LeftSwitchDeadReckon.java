package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class LeftSwitchDeadReckon extends CommandGroup {
    public LeftSwitchDeadReckon(IntakeSubsystem intake, Command elevateCommand, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(.5));
        addSequential(drivetrain.driveRotation(334.67));
        addSequential(elevateCommand);
       addSequential(drivetrain.driveDistance(5.54));
        addSequential(drivetrain.driveRotation(-334.67));
        addSequential(drivetrain.driveDistance(4.4));
        addSequential(intake.outtake());
        addSequential(new WaitCommand(3));
        addSequential(intake.cancel());

    }
}
