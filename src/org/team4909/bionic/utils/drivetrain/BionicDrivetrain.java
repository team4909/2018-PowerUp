package org.team4909.bionic.utils.drivetrain;

import org.team4909.bionic.utils.commands.DriveOI;
import org.team4909.bionic.utils.oi.BionicAxisHandle;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BionicDrivetrain extends Subsystem {
	private RobotDrive robotDrive;

	private double rotationConst;
	
	private BionicAxisHandle moveAxis;
	private BionicAxisHandle rotateAxis;
	
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
			BionicAxisHandle moveAxis, 
			BionicAxisHandle rotateAxis) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor);

		this.rotationConst = rotationConst;
		
		this.moveAxis = moveAxis;
		this.rotateAxis = rotateAxis;
	}
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, 
			SpeedController drivetrainRightMotor,
			double rotationConst,
			BionicAxisHandle moveAxis, 
			BionicAxisHandle rotateAxis,
			Solenoid shiftingSolenoid) {
		this(drivetrainLeftMotor, drivetrainRightMotor, rotationConst, moveAxis, rotateAxis);
		
		this.shiftingSolenoid = shiftingSolenoid;
	}
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor,
			double rotationConst,
			BionicAxisHandle moveAxis, 
			BionicAxisHandle rotateAxis) {
		this.robotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor);
		
		this.rotationConst = rotationConst;
		
		this.moveAxis = moveAxis;
		this.rotateAxis = rotateAxis;
	}
	
	public BionicDrivetrain(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor,
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, 
			double rotationConst,
			BionicAxisHandle moveAxis, 
			BionicAxisHandle rotateAxis,
			Solenoid shiftingSolenoid) {
		this(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainRightMotor, drivetrainRightBackMotor, rotationConst, moveAxis, rotateAxis);
		
		this.shiftingSolenoid = shiftingSolenoid;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveOI(this));
	}
	
	public void driveOIArcade() {
		double moveValue = moveAxis.getValue();
		double rotateValue = rotateAxis.getValue();
		
		switch(driveDirection) {
		case Forward:
			robotDrive.arcadeDrive(moveValue, rotationConst*rotateValue);
			break;
		case Reverse:
			robotDrive.arcadeDrive(-moveValue, rotationConst*rotateValue);
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
			case Low:
				shiftingSolenoid.set(false);
				break;
			case High:
				shiftingSolenoid.set(true);
				break;
			}
		}
	}
}
