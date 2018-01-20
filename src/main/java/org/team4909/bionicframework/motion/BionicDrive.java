package org.team4909.bionicframework.motion;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DriverStation;
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

/**
 * BionicDrive abstracts away much of the underlying drivetrain functionality
 */
public class BionicDrive extends Subsystem {
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
	/**
	 * @param leftSRX Left Drivetrain SRX
	 * @param rightSRX Right Drivetrain SRX
	 * @param speedInputGamepad Speed Input Gamepad/Joystick
	 * @param speedInputAxis Speed Input Axis
	 * @param rotationInputGamepad Rotation Input Gamepad/Joystick
	 * @param rotationInputAxis Rotation Input Axis
	 * @param encoder Encoder type plugged into SRXs
	 * @param encoder_p Proportional Constant in Encoder PID Controller
	 * @param encoder_i Integral Constant in Encoder PID Controller
	 * @param encoder_d Derivative Constant in Encoder PID Controller
	 * @param bionicGyro Gyro to Use for Closed-Loop
	 * @param gyro_p Proportional Constant in Gyro PID Controller
	 * @param maxVelocity Max Velocity used for Path Generation
	 * @param maxAccel Max Acceleration used for Path Generation
	 * @param maxJerk Max Jerk used for Path Generation
	 * @param drivebaseWidth Drivebase width between center of wheels
	 * @param wheelDiameter Wheel diameter from end to end
	 */
	public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
			BionicF310 speedInputGamepad, BionicAxis speedInputAxis,
			BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis,
			FeedbackDevice encoder, double encoder_p, double encoder_i, double encoder_d,
			Gyro bionicGyro, double gyro_p,
			double maxVelocity, double maxAccel, double maxJerk,
			double drivebaseWidth, double wheelDiameter) {
		super();
		
		this.leftSRX = leftSRX;
		this.rightSRX = rightSRX;
		
		this.leftSRX.configSelectedFeedbackSensor(encoder);
		this.rightSRX.configSelectedFeedbackSensor(encoder);

		this.leftSRX.setSensorPhase(true);
        this.rightSRX.setSensorPhase(true);
		
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
	
	/**
	 * @return Returns Robot's Current Heading [0, 360)
	 */
	public double getHeading() {
		return bionicGyro.getAngle();
	}
	
	/* Handle Control Modes */
	@Override 
	protected void initDefaultCommand() {}
	
	/**
	 * Called from WPILib Scheduler
	 */
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
			
			differentialDrive.arcadeDrive(speed, rotation, false);
		}
	}
		
	/**
	 * @param points Path consisting of waypoints to follow
	 * @return Returns a Command that can be used by the operator and autonomous CommandGroups.
	 */
	public Command driveWaypoints(Waypoint[] points) {
		return new DriveWaypoints(points);
	}
	
	private class DriveWaypoints extends Command {
		private final TankTrajectory trajectory; 
		
		public DriveWaypoints(Waypoint[] points) {
            DriverStation.reportWarning("Generating Path", false);
            trajectory = pathgen.getTrajectory(points);

			setInterruptible(false);
//		}
//
//		@Override
//		protected void initialize(){
            DriverStation.reportWarning("Initializing Path Follower", false);
            leftSRX.setInverted(false);
            rightSRX.setInverted(true);

            leftSRX.setNeutralMode(NeutralMode.Brake);
            rightSRX.setNeutralMode(NeutralMode.Brake);

            DriverStation.reportWarning("Disabling Drivetrain Motor Safety for MP", false);
            differentialDrive.setSafetyEnabled(false);

            controlMode = DriveMode.MotionProfile;

            DriverStation.reportWarning("Initializing SRX MP", false);
            leftSRX.initMotionProfile(trajectory.left);
            rightSRX.initMotionProfile(trajectory.right);
        }

        @Override
		protected boolean isFinished() {
		    return leftSRX.isMotionProfileFinished() && rightSRX.isMotionProfileFinished();
		}
		
		@Override
		protected void end() {
            leftSRX.setInverted(false);
            rightSRX.setInverted(false);

            leftSRX.setNeutralMode(NeutralMode.Coast);
            rightSRX.setNeutralMode(NeutralMode.Coast);

            DriverStation.reportWarning("Re-enabling Drivetrain Motor Safety for MP", false);
            differentialDrive.setSafetyEnabled(true);

		    controlMode = DriveMode.PercentOutput;
		}
	}

}
