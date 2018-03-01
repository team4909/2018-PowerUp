package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;
public class LeftScaleFromLeft extends CommandGroup {
    public LeftScaleFromLeft(MotorSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain) {
        addSequential(drivetrain.driveDistance(27));
        addSequential(drivetrain.driveRotation(90), 3);
        addSequential(elevator.holdPosition(34000));
        addSequential(new WaitCommand(3));
        addSequential(drivetrain.driveDistance(2), 2);
        addParallel(intake.setPercentOutput(-1.0), 1.5);
        addSequential(new WaitCommand(1.5));
        addSequential(drivetrain.driveDistance(-2), 2);
        addSequential(elevator.holdPosition(0));
    }
}
