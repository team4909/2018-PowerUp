package org.team4909.bionic.utils.subsystem;

import org.team4909.bionic.utils.commands.DriveOI;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
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
	
	private Solenoid shiftingSolenoid;
	
	public static enum Gear {
		Low, High
	}
	private Gear driveGear = Gear.Low;
	
	public static enum Direction {
		Forward, Reverse
	}
	public Direction driveDirection = Direction.Forward;
	
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
		setDefaultCommand(new DriveOI(this));
	}
	
	public void driveOIArcade() {
		double moveValue = moveStick.getRawAxis(moveAxis);
		double rotateValue = rotateStick.getRawAxis(rotateAxis);
		
		switch(driveDirection) {
		case Forward:
			robotDrive.arcadeDrive(moveValue, rotateValue);
			break;
		case Reverse:
			robotDrive.arcadeDrive(-moveValue, rotateValue);
			break;
		}
	}
	
	public void driveAutoArcade(double move, double rotate) {
		robotDrive.arcadeDrive(move, rotate);
	}
	
	public void driveAutoTank(double leftValue, double rightValue) {
		robotDrive.tankDrive(leftValue, rightValue);
	}

	public Gear getGear() {
		return driveGear;
	}
	
	public void setGear(Gear gear) {
		driveGear = gear;
		
		if(shiftingSolenoid != null) {
			switch(driveGear) {
			case Low:
				shiftingSolenoid.set(false);
				break;
			case High:
				shiftingSolenoid.set(true);
				break;
			}
		}
	}
	
	public double getAngle() {
		return 0;
	}
}
