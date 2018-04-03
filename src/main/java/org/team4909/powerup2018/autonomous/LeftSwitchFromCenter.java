package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class LeftSwitchFromCenter extends CommandGroup {
    public LeftSwitchFromCenter(MotorSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain) {
        addSequential(drivetrain.driveDistance(3.833));
        addSequential(drivetrain.driveRotation(-55.21));
        addSequential(drivetrain.driveDistance(6.696));
        addSequential(drivetrain.driveRotation(55.21));

        addSequential(elevator.holdPosition(11000));
        addSequential(new WaitCommand(.25));

        addSequential(drivetrain.driveDistance(0.833));

        addSequential(intake.setPercentOutput(-1.0));
        addSequential(new WaitCommand(1.5));
        addSequential(intake.setPercentOutput(0));

        addSequential(drivetrain.driveDistance(-0.833));
        addSequential(new WaitCommand(.25));
        addSequential(elevator.holdPosition(0));
    }
}
