package org.team4909.powerup2018.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team4909.bionicframework.hardware.motor.MotorSubsystem;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.commands.DriveDistance;
import org.team4909.bionicframework.subsystems.elevator.ElevatorSubsystem;

public class DoubleRightSwitchFromCenter extends CommandGroup{
    public DoubleRightSwitchFromCenter(MotorSubsystem intake, ElevatorSubsystem elevator, BionicDrive drivetrain){
        //Drive far enough to get away from the wall
        addSequential(new DriveDistance(24, .02, 0, 0));

        //Turn to avoid the pile of cubes
        addSequential(drivetrain.driveRotation(-55,.0075,0.0002,0));

        //Drive to the switch
        addSequential(new DriveDistance(4.25*12, 0.02,0,0));

        //Turn to score
        addSequential(drivetrain.driveRotation(50, .009,0.0002,0));

        //Move elevator to height
        addSequential(elevator.holdPosition(11000));
        addSequential(new WaitCommand(.2)); //wait to get there
        addSequential(new DriveDistance(5*12, 0.02,0,0),1.5);
        //Drop off first cube
        addSequential(intake.setPercentOutput(-1.0));
        addSequential(new WaitCommand(.5));
        //Back up
        addSequential(new DriveDistance(-3*12,.02,0,0));
        addSequential(intake.setPercentOutput(0));
        //elevator down
        addSequential(elevator.holdPosition(0));
        //turn left
        addSequential(drivetrain.driveRotation(40,.00095,0.0002, 0));
        //Intake on
        addSequential(intake.setPercentOutput(1));
        //forward 4 feet
        addSequential(new DriveDistance(5*12,.02,0,0),3);
        //Make sure cube is in
        addSequential(intake.setPercentOutput(-0.5));
        addSequential(new WaitCommand(.2));
        addSequential(intake.setPercentOutput(1));
        addSequential(new WaitCommand(.5));
        //intake off
        addSequential(intake.setPercentOutput(0));
        //back up
        addSequential(new DriveDistance(-5*12,.02,0,0));
        //turn right
        addSequential(drivetrain.driveRotation(-70,0.0085,0,0),1);
        //Elevator up
        addSequential(elevator.holdPosition(11000));
        addSequential(new WaitCommand(.5));
        //drive forward 4 feet
        addSequential(new DriveDistance(5*12,.02,0,0),2);
        //Shoot
        addSequential(intake.setPercentOutput(-1.0));
        addSequential(new WaitCommand(.75));
        addSequential(intake.setPercentOutput(0));
        //reset
        addSequential(new DriveDistance(-24,.02,0,0));
        addSequential(elevator.holdPosition(0));
    }
}
