package org.team4909.bionic.utils.drivetrain;

import org.team4909.bionic.utils.commands.DriveOI;
import org.team4909.bionic.utils.oi.BionicAxisHandle;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BionicDrivetrain extends Subsystem {
	private IBionicDriveBase driveBase;

	private BionicAxisHandle moveAxis;
	private BionicAxisHandle rotateAxis;
	
	private Solenoid shiftingSolenoid;
	
	public static enum Gear { Low, High }
	private Gear driveGear = Gear.Low;
	
	public static enum Direction { Forward, Reverse }
	public Direction driveDirection = Direction.Forward;
	
	public BionicDrivetrain(IBionicDriveBase driveBase, BionicAxisHandle moveAxis, BionicAxisHandle rotateAxis) {
		this.driveBase = driveBase;

		this.moveAxis = moveAxis;
		this.rotateAxis = rotateAxis;
	}
	
	public BionicDrivetrain(IBionicDriveBase driveBase, BionicAxisHandle moveAxis, BionicAxisHandle rotateAxis, Solenoid shiftingSolenoid) {
		this(driveBase, moveAxis, rotateAxis);
		
		this.shiftingSolenoid = shiftingSolenoid;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveOI(this));
	}
	
	public void arcadeDriveScaled() {
		double moveValue = moveAxis.getValue();
		double rotateValue = rotateAxis.getValue();
		
		if(driveDirection == Direction.Reverse) moveValue *= -1;

		driveBase.arcadeDriveScaled(moveValue, rotateValue);
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
