package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;

public class LeftScaleDeadReckon extends CommandGroup {
    public LeftScaleDeadReckon(IntakeSubsystem intake, Command elevateCommand, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(16.41));
        addSequential(drivetrain.driveRotation(50.27));
        addSequential(elevateCommand);
        addSequential(drivetrain.driveDistance(4.69));
        addSequential(drivetrain.driveRotation(-50.27));
        addSequential(drivetrain.driveDistance(2.41));
        addSequential(intake.outtake());
        addSequential(new WaitCommand(3));
        addSequential(intake.cancel());
    }
}
