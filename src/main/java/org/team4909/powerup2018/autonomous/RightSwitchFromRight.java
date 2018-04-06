package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.commands.DriveDistance;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class RightSwitchFromRight extends CommandGroup {
    public RightSwitchFromRight(MotorSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain) {
//        addSequential(drivetrain.driveDistance(12.41));
//        addSequential(drivetrain.driveRotation(-90));
//
//        addSequential(elevator.holdPosition(11000));
//        addSequential(new WaitCommand(.25));
//
//        addSequential(drivetrain.driveDistance(.833));
//
//        addSequential(intake.setPercentOutput(-1.0));
//        addSequential(new WaitCommand(1.5));
//        addSequential(intake.setPercentOutput(0));
//
//        addSequential(drivetrain.driveDistance(-.833));
//        addSequential(new WaitCommand(.25));
//        addSequential(elevator.holdPosition(0));
        /******* NEW CODE *******/
        //Drive up to the right side of the switch
        addSequential(new DriveDistance(115, 0.02,0,0));
        //Rotate 90 degrees to the left
        addSequential(drivetrain.driveRotation(90,.0085,0,0),2);
        //Elevator raised
        addSequential(elevator.holdPosition(11000));
        addSequential(new WaitCommand(.25));
        //Drive up to the switch, distance is greater than it needs to be, timeout after 1 seconds
        addSequential(new DriveDistance(24, .02, 0, 0),1);
        //Shoot Cube
        addSequential(intake.setPercentOutput(-1.0));
        addSequential(new WaitCommand(1.5));
        addSequential(intake.setPercentOutput(0));
        //Back away from cube
//        addSequential(new DriveDistance(-24, .02, 0, 0));
        //Elevator down
//        addSequential(elevator.holdPosition(0));

    }
}
