package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class LeftSwitchDeadReckon extends CommandGroup {

    public final double toMeters = 0.3048;

    public LeftSwitchDeadReckon(IntakeSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(.5));
        addSequential(drivetrain.driveRotation(-60),1);
        addSequential(drivetrain.driveDistance(17 * toMeters),2);
        addSequential(drivetrain.driveRotation(35),1);
        addSequential(elevator.holdPosition(11000));
        addSequential(drivetrain.driveDistance(4.4 * toMeters),2);
        addSequential(intake.outtake());
        addSequential(new WaitCommand(3));
        addSequential(intake.cancel());
    }
}
