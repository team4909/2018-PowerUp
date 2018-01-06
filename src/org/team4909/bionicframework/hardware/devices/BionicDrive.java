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

public class BionicDrive extends Subsystem {
	/* Hardware */
	private BionicSRX leftSRX;
	private BionicSRX rightSRX;
	private BionicSolenoid shiftingSolenoid;
	private DifferentialDrive differentialDrive;
	
	/* OI */
	private BionicF310 speedInputGamepad;
	private BionicAxis speedInputAxis;
	private double speedScaleFactor;
	private BionicF310 rotationInputGamepad;
	private BionicAxis rotationInputAxis;
	private double rotationScaleFactor;
	
	/* Sensors */
	private BionicGyro bionicGyro;
	private double gyroP;
	private double gyroI;
	private double gyroD;
	private double gyroF;
	private Trajectory.Config pathfinderConfig;
	private double drivebaseWidth;
	
	/* Hardware Initialization */
	public BionicDrive(int srxLeftDeviceNumber, int srxRightDeviceNumber) {
		leftSRX = new BionicSRX(srxLeftDeviceNumber);
		rightSRX = new BionicSRX(srxRightDeviceNumber);
		differentialDrive = new DifferentialDrive(leftSRX, rightSRX);
	}

	public void addFollowers(int srxLeftDeviceNumber, int srxRightDeviceNumber) {
		leftSRX.addFollower(srxLeftDeviceNumber);
		rightSRX.addFollower(srxRightDeviceNumber);
	}
	
	/* Operator Interface Initialization */
	public void setSpeedAxis(BionicF310 speedInputGamepad, BionicAxis speedInputAxis) {
		setSpeedAxis(speedInputGamepad, speedInputAxis, 1.0);
	}

	public void setSpeedAxis(BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedScaleFactor) {
		this.speedInputGamepad = speedInputGamepad;
		this.speedInputAxis = speedInputAxis;
		this.speedScaleFactor = speedScaleFactor;
	}

	public void setRotationAxis(BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis) {
		setRotationAxis(rotationInputGamepad, rotationInputAxis, 1.0);
	}
	
	public void setRotationAxis(BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationScaleFactor) {
		this.rotationInputGamepad = rotationInputGamepad;
		this.rotationInputAxis = rotationInputAxis;
		this.rotationScaleFactor = rotationScaleFactor;
	}
	
	/* Shifting */
	public void setShiftingSolenoid(BionicSolenoid shiftingSolenoid) {
		this.shiftingSolenoid = shiftingSolenoid;
	}
	
	public Command setState(DoubleSolenoid.Value value) {
		return shiftingSolenoid.setState(value);
	}
	
	/* Sensor Initialization */
	public void setFeedbackDevice(FeedbackDevice feedbackDevice) {
		leftSRX.setFeedbackDevice(feedbackDevice);
		rightSRX.setFeedbackDevice(feedbackDevice);
	}
	
	public void setMotorPIDF(double p, double i, double d, double f) {
		leftSRX.setPIDF(p,i,d,f);
		rightSRX.setPIDF(p,i,d,f);
	}

	public void setGyro(BionicGyro bionicGyro) {
		this.bionicGyro = bionicGyro;
	}
	
	public void setGyroPIDF(double p, double i, double d, double f) {
		gyroP = p;
		gyroI = i;
		gyroD = d;
		gyroF = f;
	}
	
	public double getHeading() {
		return bionicGyro.getAngle();
	}
	
	public void setPathfinderConfig(Trajectory.Config config, double drivebaseWidth) {
		this.pathfinderConfig = config;
		this.drivebaseWidth = drivebaseWidth;
	}
	
	/* Handle Control Modes */
	@Override protected void initDefaultCommand() {
		setDefaultCommand(new DriveOI(this));
	}
	
	private class DriveOI extends Command {
		private BionicDrive bionicDrive;
		
		public DriveOI(BionicDrive bionicDrive) {
			this.bionicDrive = bionicDrive;
		}

		@Override
		public void initialize() {
			requires(bionicDrive);
		}
	
		public void execute() {
			double speed = speedInputGamepad.getThresholdAxis(speedInputAxis, 0.15) * speedScaleFactor;
			double rotation = rotationInputGamepad.getThresholdAxis(rotationInputAxis, 0.15) * rotationScaleFactor;
			
			differentialDrive.curvatureDrive(speed, rotation, false);
		}

		@Override
		protected boolean isFinished() {
			return false;
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
		public void initialize() {
			requires(bionicDrive);
		}
		
		public void execute() {
			
		}
		
		@Override
		protected boolean isFinished() {
			return false;
		}
	}
}
