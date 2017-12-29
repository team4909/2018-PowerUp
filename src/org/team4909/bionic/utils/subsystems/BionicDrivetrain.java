package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.commands.DriveOI;
import org.team4909.bionic.utils.oi.BionicAxis;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BionicDrivetrain extends Subsystem {
	private RobotDrive robotDrive;

	private double rotationConst;
	
	private GenericHID moveStick; 
	private BionicAxis moveAxis;
	private GenericHID rotateStick;
	private BionicAxis rotateAxis;
	
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
			SpeedController drivetrainLeftMotor,
			SpeedController drivetrainRightMotor,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor);

		this.rotationConst = rotationConst;
		
		this.moveStick = moveStick;
		this.moveAxis = moveAxis;
		
		this.rotateStick = rotateStick;
		this.rotateAxis = rotateAxis;
	}
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, 
			SpeedController drivetrainRightMotor,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis,
			Solenoid shiftingSolenoid) {
		this(drivetrainLeftMotor, drivetrainRightMotor, rotationConst, moveStick, moveAxis, rotateStick, rotateAxis);
		
		this.shiftingSolenoid = shiftingSolenoid;
	}
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor);
		
		this.rotationConst = rotationConst;
		
		this.moveStick = moveStick;
		this.moveAxis = moveAxis;
		
		this.rotateStick = rotateStick;
		this.rotateAxis = rotateAxis;
	}
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, 
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis,
			Solenoid shiftingSolenoid) {
		this(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor, rotationConst, 
			moveStick, moveAxis, rotateStick, rotateAxis);
		
		this.shiftingSolenoid = shiftingSolenoid;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveOI(this));
	}
	
	public void driveOIArcade() {
		double moveValue = moveStick.getRawAxis(moveAxis.getNumber());
		double rotateValue = rotateStick.getRawAxis(rotateAxis.getNumber());
		
		switch(driveDirection) {
		case Reverse:
			robotDrive.arcadeDrive(-moveValue, rotationConst*rotateValue);
			break;
		default:
			robotDrive.arcadeDrive(moveValue, rotationConst*rotateValue);
			break;
		}
	}

	public Gear getGear() {
		return driveGear;
	}
	
	public void setGear(Gear gear) {
		driveGear = gear;
		
		if(shiftingSolenoid != null) {
			switch(driveGear) {
			case High:
				shiftingSolenoid.set(true);
				break;
			default:
				shiftingSolenoid.set(false);
				break;
			}
		}
	}
}
