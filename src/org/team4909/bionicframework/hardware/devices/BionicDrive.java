package org.team4909.bionicframework.hardware.devices;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.team4909.bionicframework.hardware.interfaces.BionicGyro;
import org.team4909.bionicframework.oi.BionicAxis;
import org.team4909.bionicframework.oi.BionicF310;

public class BionicDrive extends Subsystem {
	/* Hardware */
	private BionicSRX leftSRX;
	private BionicSRX rightSRX;
	private BionicSolenoid shiftingSolenoid;
	
	/* OI */
	private BionicF310 speedInputGamepad;
	private BionicAxis speedInputAxis;
	private double speedScaleFactor;
	private BionicF310 rotationInputGamepad;
	private BionicAxis rotationInputAxis;
	private double rotationScaleFactor;
	
	/* Sensors */
	private BionicGyro bionicGyro;
	
	/* Hardware Initialization */
	public BionicDrive(int srxLeftDeviceNumber, int srxRightDeviceNumber) {
		this.leftSRX = new BionicSRX(srxLeftDeviceNumber);
		this.rightSRX = new BionicSRX(srxRightDeviceNumber);
	}

	public void addFollowers(int srxLeftDeviceNumber, int srxRightDeviceNumber) {
		this.leftSRX.addFollower(srxLeftDeviceNumber);
		this.rightSRX.addFollower(srxRightDeviceNumber);
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
		
	}
	
	/* Handle Control Mode */
	@Override
	public void periodic() {
		
	}
	
	@Override protected void initDefaultCommand() {}
}
