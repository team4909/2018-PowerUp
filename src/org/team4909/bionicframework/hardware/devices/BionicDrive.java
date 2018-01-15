package org.team4909.bionicframework.hardware.devices;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.team4909.bionicframework.hardware.interfaces.BionicGyro;
import org.team4909.bionicframework.oi.BionicAxis;
import org.team4909.bionicframework.oi.BionicF310;

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
	private int profileInterval = 10;
	
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
	private BionicGyro bionicGyro;
	private double gyro_p;
	private Trajectory.Config pathfinderConfig;
	private double drivebaseWidth;
	
	/* Hardware Initialization */
	public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
			BionicF310 speedInputGamepad, BionicAxis speedInputAxis,
			BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis,
			FeedbackDevice encoder, double encoder_p, double encoder_i, double encoder_d, double encoder_f,
			BionicGyro bionicGyro, double gyro_p,
			double maxVelocity, double maxAccel, double maxJerk, double drivebaseWidth) {
		this.leftSRX = leftSRX;
		this.rightSRX = rightSRX;
		
		this.leftSRX.configSelectedFeedbackSensor(encoder);
		this.rightSRX.configSelectedFeedbackSensor(encoder);
		this.leftSRX.configPIDF(encoder_p, encoder_i, encoder_d, encoder_f);
		this.rightSRX.configPIDF(encoder_p, encoder_i, encoder_d, encoder_f);
		
		this.leftSRX.changeMotionControlFramePeriod(profileInterval);
		this.rightSRX.changeMotionControlFramePeriod(profileInterval);
		
		this.bionicGyro = bionicGyro;
		this.gyro_p = gyro_p;
		
		this.pathfinderConfig = new Trajectory.Config(
				Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH,
				(double) profileInterval / 1000, maxVelocity, maxAccel, maxJerk);
		this.drivebaseWidth = drivebaseWidth;
		
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
			break;
		case PercentVBus:
		default:
			double speed = speedInputGamepad.getThresholdAxis(speedInputAxis, 0.15) * speedScaleFactor;
			double rotation = rotationInputGamepad.getThresholdAxis(rotationInputAxis, 0.15) * rotationScaleFactor;
			
			differentialDrive.curvatureDrive(speed, rotation, false);
		}
	}
		
	public Command driveWaypoints(Waypoint[] points) {
		return new DriveWaypoints(points, this);
	}
	
	private class DriveWaypoints extends Command {
		private BionicDrive bionicDrive;
		
		private Trajectory left;
		private Trajectory right;
		
		public DriveWaypoints(Waypoint[] points, BionicDrive bionicDrive) {
			this.bionicDrive = bionicDrive;
			
			Trajectory trajectory = Pathfinder.generate(points, pathfinderConfig);
			
			TankModifier modifier = new TankModifier(trajectory).modify(drivebaseWidth);
			
			left = modifier.getLeftTrajectory();
			right = modifier.getRightTrajectory();
		}

		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}
	}

}
