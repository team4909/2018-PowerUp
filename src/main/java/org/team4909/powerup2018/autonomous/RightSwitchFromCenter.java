package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;
import org.team4909.powerup2018.subsystems.IntakeSubsystem;

public class RightSwitchFromCenter extends CommandGroup {
    public RightSwitchFromCenter(IntakeSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain) {
        addSequential(drivetrain.driveDistance(3));
        addSequential(drivetrain.driveRotation(60), 1.5);
        addSequential(drivetrain.driveDistance(19), 2);
        addSequential(drivetrain.driveRotation(-60), 2);
        addSequential(elevator.holdPosition(11000));
        addSequential(drivetrain.driveDistance(2), 2);
        addParallel(intake.outtakeFast(), 2);
        addSequential(new WaitCommand(2));
        addSequential(drivetrain.driveDistance(-2), 3);
        addSequential(elevator.holdPosition(0));
    }
}
