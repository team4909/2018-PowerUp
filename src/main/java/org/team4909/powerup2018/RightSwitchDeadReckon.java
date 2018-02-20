package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class RightSwitchDeadReckon extends CommandGroup {

    public final double toMeters = 0.3048;

    public RightSwitchDeadReckon(IntakeSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(1));
        addSequential(drivetrain.driveRotation(60),1.5);
        addSequential(drivetrain.driveDistance(19 * toMeters),2);
        addSequential(drivetrain.driveRotation(-60),2);
        addSequential(elevator.holdPosition(11000));
        addSequential(drivetrain.driveDistance(2 * toMeters),2);
        addSequential(intake.outtake());
        addSequential(new WaitCommand(2));
        addSequential(intake.cancel());
        addSequential(drivetrain.driveDistance(-2 * toMeters),3);
        addSequential(elevator.holdPosition(0));
    }
}
