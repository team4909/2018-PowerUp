package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class RightScaleDeadReckon extends CommandGroup {
    public final double toMeters = 0.3048;

    public RightScaleDeadReckon(IntakeSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(27 * toMeters));
        addSequential(drivetrain.driveRotation(-90), 3);
        addSequential(elevator.holdPosition(34000));
        addSequential(new WaitCommand(3));
        addSequential(drivetrain.driveDistance(2 * toMeters), 2);
        addSequential(intake.outtake());
        addSequential(new WaitCommand(1.5));
        addSequential(intake.cancel());
        addSequential(drivetrain.driveDistance(-2 * toMeters), 2);
        addSequential(elevator.holdPosition(0));
    }
}
