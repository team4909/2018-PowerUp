package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.commands.DriveDistance;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class RightSwitchFromCenter extends CommandGroup {
    public RightSwitchFromCenter(MotorSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain) {
//        addSequential(drivetrain.driveDistance(0.986));
//        addSequential(drivetrain.driveRotation(39.52));
//        addSequential(drivetrain.driveDistance(8.643));
//        addSequential(drivetrain.driveRotation(-39.52));
//
//        addSequential(elevator.holdPosition(11000));
//        addSequential(new WaitCommand(.25));
//
//        addSequential(drivetrain.driveDistance(0.833));
//
//        addSequential(intake.setPercentOutput(-1.0));
//        addSequential(new WaitCommand(1.5));
//        addSequential(intake.setPercentOutput(0));
//
//        addSequential(drivetrain.driveDistance(-0.833));
//        addSequential(new WaitCommand(.25));
//        addSequential(elevator.holdPosition(0));

        //Drive far enough to get away from the wall
        addSequential(new DriveDistance(24, .02, 0, 0));
//                drivetrain.driveDistance(1.5, 1));

        //Turn to avoid the pile of cubes
        addSequential(drivetrain.driveRotation(-55,.009,0.0002,0));

        //Drive to the switch
        addSequential(new DriveDistance(4.5*12, 0.02,0,0));
//                drivetrain.d

        //turn to score
        addSequential(drivetrain.driveRotation(50, .009,0.0002,0));

        // Move elevator to height
        addSequential(elevator.holdPosition(11000));
        addSequential(new WaitCommand(.5)); //wait to get there
//
        addSequential(new DriveDistance(4*12, 0.02,0,0),2);
//                drivetrain.driveDistance(1.833));

        addSequential(intake.setPercentOutput(-1.0));
        addSequential(new WaitCommand(1.5));
        addSequential(intake.setPercentOutput(0));
//
//        addSequential(drivetrain.driveDistance(-0.833));
//        addSequential(new WaitCommand(.25));
//        addSequential(elevator.holdPosition(0));
    }
}
