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
	private double gyro_P;
	private Trajectory.Config pathfinderConfig;
	private double drivebaseWidth;
	
	/* Hardware Initialization */
	public BionicDrive(int srxLeftDeviceNumber, int srxRightDeviceNumber,
			BionicF310 speedInputGamepad, BionicAxis speedInputAxis,
			BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis,
			FeedbackDevice encoder, double encoder_p, double encoder_i, double encoder_d, double encoder_f,
			BionicGyro bionicGyro, double gyro_p,
			Trajectory.Config pathfinderConfig, double drivebaseWidth) {
		leftSRX = new BionicSRX(srxLeftDeviceNumber);
		rightSRX = new BionicSRX(srxRightDeviceNumber);
		
		leftSRX.setFeedbackDevice(encoder);
		rightSRX.setFeedbackDevice(encoder);
		leftSRX.setPIDF(encoder_p, encoder_i, encoder_d, encoder_f);
		rightSRX.setPIDF(encoder_p, encoder_i, encoder_d, encoder_f);
		
		this.bionicGyro = bionicGyro;
		this.gyro_P = gyro_p;
		
		this.pathfinderConfig = pathfinderConfig;
		this.drivebaseWidth = drivebaseWidth;
		
		this.rotationInputGamepad = rotationInputGamepad;
		this.rotationInputAxis = rotationInputAxis;
		
		this.speedInputGamepad = speedInputGamepad;
		this.speedInputAxis = speedInputAxis;
		
		differentialDrive = new DifferentialDrive(leftSRX, rightSRX);
	}

	public void addFollowers(int srxLeftDeviceNumber, int srxRightDeviceNumber) {
		leftSRX.addFollower(srxLeftDeviceNumber);
		rightSRX.addFollower(srxRightDeviceNumber);
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
	}

}
