package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public abstract class BionicVelocitySubsystem extends Subsystem {
	public CANTalon speedController;
	
	public BionicVelocitySubsystem(CANTalon speedController, double F, double P, double I, double D) {
		this.speedController = speedController;
		
		this.speedController.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		this.speedController.configNominalOutputVoltage(+0.0f, -0.0f );
		this.speedController.configPeakOutputVoltage(+12.0f , -12.0f );

		this.speedController.setProfile(0);
		this.speedController.setF(0.1097d);
		this.speedController.setP(0.22d);
		this.speedController.setI(0d);
		this.speedController.setD(0d);
	}
	
	public BionicVelocitySubsystem(CANTalon speedController, boolean inverted) {
		this(speedController, 0, 0, 0, 0);
		
		this.speedController.reverseSensor(inverted);
	}

	// No Default Voltage
	protected void initDefaultCommand() {}
	
	public void set(VelocitySetpoint setpoint) {
		this.speedController.changeControlMode(TalonControlMode.Speed);
		this.speedController.set(setpoint.getValue());
	}
	
	
	
}
