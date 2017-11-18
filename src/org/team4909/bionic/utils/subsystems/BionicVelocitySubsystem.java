package org.team4909.bionic.utils.subsystems;

import org.team4909.bionic.utils.setpoints.VelocitySetpoint;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public abstract class BionicVelocitySubsystem extends Subsystem {
	
	public SpeedController speedController;
	CANTalon _talon = new CANTalon(0);
	
	public BionicVelocitySubsystem(SpeedController speedController) {
		this.speedController = speedController;
		
		((MotorSafety) this.speedController).setSafetyEnabled(true);
		}
		
		public void robotInit() {
			  /* first choose the sensor */
			  _talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			  _talon.reverseSensor(false);

			  /* set the peak and nominal outputs, 12V means full */
			  _talon.configNominalOutputVoltage(+0.0f, -0.0f );
			  _talon.configPeakOutputVoltage(+12.0f , -12.0f );

			  /* set closed loop gains in slot0 */
			  _talon.setProfile(0);
			  _talon.setF(0.1097);
			  _talon.setP(0.22);
			  _talon.setI(0);
			  _talon.setD(0);
			  
			 }
			 
			 public void teleopPeriodic() {
			  _talon.changeControlMode(TalonControlMode.Speed);
			  _talon.set(1500); /* 1500 RPM in either direction */
			 }
	
	public BionicVelocitySubsystem(SpeedController speedController, boolean inverted) {
		this(speedController);
		
		this.speedController.setInverted(inverted);
	}

	// No Default Voltage
	protected void initDefaultCommand() {}
	
	public void set(VelocitySetpoint setpoint) {

		speedController.set(setpoint.getValue());
	}
	
	
	
}
