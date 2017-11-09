package org.team4909.preseason2018.subsystems;

import org.team4909.bionic.utils.oi.BionicAxis;
import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.subsystems.BionicDrivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class Drivetrain extends BionicDrivetrain {
	public PIDConstants rotatePIDConstants = new PIDConstants(0.14, 0, 0.1, 0.6);
	public PIDConstants drivePIDConstants = new PIDConstants(0.08, 0, 0, 1);
	
	public Drivetrain(
			SpeedController drivetrainLeftMotor, Encoder drivetrainLeftEncoder, 
			SpeedController drivetrainRightMotor, Encoder drivetrainRightEncoder, 
			double distancePerPulse,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis)  {
		super(drivetrainLeftMotor, drivetrainLeftEncoder, drivetrainRightMotor, drivetrainRightEncoder, distancePerPulse, rotationConst,
				moveStick, moveAxis, rotateStick, rotateAxis);
	}
	
	public Drivetrain(
			SpeedController drivetrainLeftMotor, Encoder drivetrainLeftEncoder, 
			SpeedController drivetrainRightMotor, Encoder drivetrainRightEncoder, 
			double distancePerPulse,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis,
			Solenoid shiftingSolenoid) {
		super(drivetrainLeftMotor, drivetrainLeftEncoder, drivetrainRightMotor, drivetrainRightEncoder, distancePerPulse, rotationConst,
				moveStick, moveAxis, rotateStick, rotateAxis, shiftingSolenoid);
	}
	public Drivetrain(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor, Encoder drivetrainLeftEncoder, 
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, Encoder drivetrainRightEncoder, 
			double distancePerPulse,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis)  {
		super(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainLeftEncoder, drivetrainRightMotor, drivetrainRightBackMotor, drivetrainRightEncoder, distancePerPulse, rotationConst,
				moveStick, moveAxis, rotateStick, rotateAxis);
	}
	
	public Drivetrain(
			SpeedController drivetrainLeftMotor, SpeedController drivetrainLeftBackMotor, Encoder drivetrainLeftEncoder, 
			SpeedController drivetrainRightMotor, SpeedController drivetrainRightBackMotor, Encoder drivetrainRightEncoder, 
			double distancePerPulse,
			double rotationConst,
			GenericHID moveStick, BionicAxis moveAxis, 
			GenericHID rotateStick, BionicAxis rotateAxis,
			Solenoid shiftingSolenoid) {
		super(drivetrainLeftMotor, drivetrainLeftBackMotor, drivetrainLeftEncoder, drivetrainRightMotor, drivetrainRightBackMotor, drivetrainRightEncoder, distancePerPulse, rotationConst,
				moveStick, moveAxis, rotateStick, rotateAxis, shiftingSolenoid);
	}
}
