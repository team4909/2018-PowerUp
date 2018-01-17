package org.team4909.bionicframework.motion;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.team4909.bionicframework.hardware.BionicSRX;
import org.team4909.bionicframework.motion.PathgenUtil.TankTrajectory;
import org.team4909.bionicframework.operator.BionicAxis;
import org.team4909.bionicframework.operator.BionicF310;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class BionicDrive extends Subsystem{
	private enum DriveMode {
		PercentOutput,
		MotionProfile
	};
	
	/* Internal State */
	private DriveMode controlMode = DriveMode.PercentOutput;
	private int profileInterval = 20;
	
	/* Hardware */
	private final BionicSRX leftSRX;
	private final BionicSRX rightSRX;
	private final DifferentialDrive differentialDrive;
	
	/* OI */
	private final BionicF310 speedInputGamepad;
	private final BionicAxis speedInputAxis;
	private final double speedScaleFactor = 1.0;
	private final BionicF310 rotationInputGamepad;
	private final BionicAxis rotationInputAxis;
	private final double rotationScaleFactor = 1.0;
	
	/* Sensors */
	private Gyro bionicGyro;
//	private double gyro_p;
	private PathgenUtil pathgen;
	
	/* Hardware Initialization */
	public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
			BionicF310 speedInputGamepad, BionicAxis speedInputAxis,
			BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis,
			FeedbackDevice encoder, double encoder_p, double encoder_i, double encoder_d,
			Gyro bionicGyro, double gyro_p,
			double maxVelocity, double maxAccel, double maxJerk,
			double drivebaseWidth, double wheelDiameter) {
		this.leftSRX = leftSRX;
		this.rightSRX = rightSRX;
		
		this.leftSRX.configSelectedFeedbackSensor(encoder);
		this.rightSRX.configSelectedFeedbackSensor(encoder);
		
		// Use F of 1023 for percentVBus Feedforward (as found by @oblarg)
		this.leftSRX.configPIDF(encoder_p, encoder_i, encoder_d, 1023);
		this.rightSRX.configPIDF(encoder_p, encoder_i, encoder_d, 1023);
		
		this.leftSRX.changeMotionControlFramePeriod(profileInterval);
		this.rightSRX.changeMotionControlFramePeriod(profileInterval);
		
		this.bionicGyro = bionicGyro;
//		this.gyro_p = gyro_p;
		
		this.pathgen = new PathgenUtil(new Trajectory.Config(
				Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH,
				(double) profileInterval / 1000, 
				maxVelocity, maxAccel, maxJerk),
				drivebaseWidth, wheelDiameter);
		
		this.rotationInputGamepad = rotationInputGamepad;
		this.rotationInputAxis = rotationInputAxis;
		
		this.speedInputGamepad = speedInputGamepad;
		this.speedInputAxis = speedInputAxis;
		
		differentialDrive = new DifferentialDrive(leftSRX, rightSRX);
	}
	
	public void addFollowers(BionicSRX leftSRX, BionicSRX rightSRX) {
		this.leftSRX.addFollower(leftSRX);	
		this.rightSRX.addFollower(rightSRX);
	}
	
	public double getHeading() {
		return bionicGyro.getAngle();
	}
	
	/* Handle Control Modes */
	@Override 
	protected void initDefaultCommand() {}
	
	@Override
	public void periodic() {
		switch(controlMode) {
		case MotionProfile:
			leftSRX.runMotionProfile();
			rightSRX.runMotionProfile();
			break;
		case PercentOutput:
		default:
			double speed = speedInputGamepad.getThresholdAxis(speedInputAxis, 0.15) * speedScaleFactor;
			double rotation = rotationInputGamepad.getThresholdAxis(rotationInputAxis, 0.15) * rotationScaleFactor;
			
			differentialDrive.curvatureDrive(speed, rotation, false);
		}
	}
		
	public Command driveWaypoints(Waypoint[] points) {
		return new DriveWaypoints(points);
	}
	
	private class DriveWaypoints extends Command {
		private final TankTrajectory trajectory; 
		
		public DriveWaypoints(Waypoint[] points) {
			trajectory = pathgen.getTrajectory(points);
			
			setInterruptible(false);
		}
		
		protected void initialize() {
			controlMode = DriveMode.MotionProfile;
			
			leftSRX.initMotionProfile(trajectory.left);
			rightSRX.initMotionProfile(trajectory.right);
		}
		
		protected boolean isFinished() {
			return leftSRX.isMotionProfileFinished() && rightSRX.isMotionProfileFinished();
		}
		
		@Override
		protected void end() {
			controlMode = DriveMode.PercentOutput;
		}
	}

}
