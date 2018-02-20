package org.team4909.powerup2018;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.Intake.IntakeSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class LeftScaleDeadReckon extends CommandGroup {

    public final double toMeters = 0.3048;

    public LeftScaleDeadReckon(IntakeSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain){
        addSequential(drivetrain.driveDistance(27 * toMeters)); //Old Value 16.41
        addSequential(drivetrain.driveRotation(90), 3); // Old Value 50.27
        addSequential(elevator.holdPosition(34000));
        addSequential(new WaitCommand(3));
        addSequential(drivetrain.driveDistance(0.5 * toMeters), 2);
        addSequential(intake.outtake());
        addSequential(new WaitCommand(1.5));
        addSequential(intake.cancel());
        addSequential(drivetrain.driveDistance(-2 * toMeters), 2);
        addSequential(elevator.holdPosition(0));
    }
}
