package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public abstract class BionicVelocitySubsystem extends Subsystem {
	
	public CANTalon speedController;
	
	public BionicVelocitySubsystem(CANTalon speedController) {
		this.speedController = speedController;
			  /* first choose the sensor */
			  this.speedController.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			  this.speedController.reverseSensor(false);

			  /* set the peak and nominal outputs, 12V means full */
			  this.speedController.configNominalOutputVoltage(+0.0f, -0.0f );
			  this.speedController.configPeakOutputVoltage(+12.0f , -12.0f );

			  /* set closed loop gains in slot0 */
			  this.speedController.setProfile(0);
			  this.speedController.setF(0.1097d);
			  this.speedController.setP(0.22d);
			  this.speedController.setI(0d);
			  this.speedController.setD(0d);
	}
	
	public BionicVelocitySubsystem(CANTalon speedController, boolean inverted) {
		this(speedController);
		this.speedController.setInverted(inverted);
	}

	// No Default Voltage
	protected void initDefaultCommand() {}
	
	public void set(VelocitySetpoint setpoint) {
		 this.speedController.changeControlMode(TalonControlMode.Speed);
		this.speedController.set(setpoint.getValue());
	}
	
	
	
}
