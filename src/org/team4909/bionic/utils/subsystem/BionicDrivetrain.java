package org.team4909.bionic.utils.subsystem;

import org.team4909.bionic.utils.commands.BionicDriveCommand;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BionicDrivetrain extends Subsystem {
	private RobotDrive robotDrive;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private GenericHID moveStick; 
	private int moveAxis;
	private GenericHID rotateStick;
	private int rotateAxis;
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, Encoder drivetrainLeftEncoder, 
			SpeedController drivetrainRightMotor, Encoder drivetrainRightEncoder, 
			double distancePerPulse,
			GenericHID moveStick, final int moveAxis, 
			GenericHID rotateStick, final int rotateAxis) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor);
		
		this.leftEncoder = drivetrainLeftEncoder;
		this.rightEncoder = drivetrainRightEncoder;
		
		this.leftEncoder.setDistancePerPulse(distancePerPulse);
		this.rightEncoder.setDistancePerPulse(distancePerPulse);
		
		this.moveStick = moveStick;
		this.moveAxis = moveAxis;
		
		this.rotateStick = rotateStick;
		this.rotateAxis = rotateAxis;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new BionicDriveCommand(this));
	}
	
	public void Drive () {
		robotDrive.arcadeDrive(moveStick, moveAxis, rotateStick, rotateAxis);
	}
}
