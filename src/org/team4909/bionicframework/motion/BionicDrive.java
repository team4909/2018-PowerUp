package org.team4909.bionicframework.motion;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.team4909.bionicframework.hardware.BionicSRX;
import org.team4909.bionicframework.hardware.BionicSolenoid;
import org.team4909.bionicframework.operator.BionicAxis;
import org.team4909.bionicframework.operator.BionicF310;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class BionicDrive extends Subsystem{
	private enum DriveMode {
		PercentVBus,
		Waypoints
	};
	
	/* Internal State */
	private DriveMode controlMode = DriveMode.PercentVBus;
	private int profileInterval = 20;
	
	/* Hardware */
	private BionicSRX leftSRX;
	private BionicSRX rightSRX;
	private BionicSolenoid shiftingSolenoid;
	private DifferentialDrive differentialDrive;
	
	/* OI */
	private BionicF310 speedInputGamepad;
	private BionicAxis speedInputAxis;
	private double speedScaleFactor = 1.0;
	private BionicF310 rotationInputGamepad;
	private BionicAxis rotationInputAxis;
	private double rotationScaleFactor = 1.0;
	
	/* Sensors */
	private Gyro bionicGyro;
	private double gyro_p;
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
		this.gyro_p = gyro_p;
		
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
	
	/* Shifting */
	public void setShiftingSolenoid(BionicSolenoid shiftingSolenoid) {
		this.shiftingSolenoid = shiftingSolenoid;
	}
	
	public Command setState(DoubleSolenoid.Value value) {
		if(shiftingSolenoid != null) {
			return shiftingSolenoid.setState(value);
		}
		
		return null;
	}
	
	public double getHeading() {
		if(bionicGyro != null) {
			return bionicGyro.getAngle();
		}
		
		return 0;
	}
	
	/* Handle Control Modes */
	@Override 
	protected void initDefaultCommand() {}
	
	@Override
	public void periodic() {
		switch(controlMode) {
		case Waypoints:
			leftSRX.processMotionProfileBuffer();
			rightSRX.processMotionProfileBuffer();
			break;
		case PercentVBus:
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
		public DriveWaypoints(Waypoint[] points) {
			pathgen.getTrajectory(points);
		}

		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}
	}

}
