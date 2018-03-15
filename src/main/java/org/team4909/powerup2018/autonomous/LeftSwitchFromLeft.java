package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class LeftSwitchFromLeft extends CommandGroup {
    public LeftSwitchFromLeft(MotorSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain) {
        addSequential(drivetrain.driveDistance(14));
        addSequential(drivetrain.driveRotation(90), 2);
        addSequential(elevator.holdPosition(11000));
        addSequential(new WaitCommand(.25));
        addSequential(drivetrain.driveDistance(2), 2);
        addSequential(intake.setPercentOutput(-1.0), 1.5);
        addSequential(new WaitCommand(1.5));
        addSequential(intake.setPercentOutput(0));
        addSequential(drivetrain.driveDistance(-2));
        addSequential(new WaitCommand(.25));
        addSequential(elevator.holdPosition(0));
    }
}
