package org.team4909.preseason2018.autonomous;

import org.team4909.bionic.utils.commands.DriveDistance;
import org.team4909.bionic.utils.commands.RotateToAngle;
import org.team4909.bionic.utils.commands.SetDriveDirection;
import org.team4909.bionic.utils.commands.SetDriveGear;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Direction;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain.Gear;
import org.team4909.preseason2018.core.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class PlaceCenterGear extends CommandGroup {
	public PlaceCenterGear() {
		//addParallel(new HoldGear());
		addParallel(new SetDriveDirection(Robot.drivetrain, Direction.Forward));
		addSequential(new SetDriveGear(Robot.drivetrain, Gear.Low));
		
		addSequential(new DriveDistance(Robot.drivetrain, Robot.drivetrain.drivePIDConstants, 82.5));

		//addSequential(new DropGear());
		
		addSequential(new DriveDistance(Robot.drivetrain, Robot.drivetrain.drivePIDConstants, -10));
		
		addSequential(new WaitCommand(.5));
	}
}
