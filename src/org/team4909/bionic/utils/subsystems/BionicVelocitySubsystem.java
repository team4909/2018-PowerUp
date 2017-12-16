package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.setpoints.PIDConstants;
import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import org.team4909.bionic.utils.setpoints.VoltageSetpoint;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public abstract class BionicVelocitySubsystem extends BionicVoltageSubsystem {
	public CANTalon speedController;
	
	public BionicVelocitySubsystem(CANTalon speedController, PIDConstants pid, double f) {
		super(speedController);
		
		this.speedController = speedController;
		
		this.speedController.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		this.speedController.configNominalOutputVoltage(+0.0f, -0.0f );
		this.speedController.configPeakOutputVoltage(+12.0f , -12.0f );

		this.speedController.setProfile(0);
		
		this.speedController.setP(pid.p);
		this.speedController.setI(pid.i);
		this.speedController.setD(pid.d);
		
		this.speedController.setF(f);
	}
	
	public BionicVelocitySubsystem(CANTalon speedController, PIDConstants pid, double f, boolean inverted) {
		this(speedController, pid, f);
		
		this.speedController.reverseSensor(inverted);
	}

	// No Default Voltage
	protected void initDefaultCommand() {}
	
	public void set(VelocitySetpoint setpoint) {
		this.speedController.changeControlMode(TalonControlMode.Speed);
		this.speedController.set(setpoint.getValue());
	}
	
	public void set(VoltageSetpoint setpoint) {
		this.speedController.changeControlMode(TalonControlMode.PercentVbus);
		this.speedController.set(setpoint.getValue());
	}
}
