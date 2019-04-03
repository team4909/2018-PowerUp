package org.team4909.bionicframework.subsystems.drive.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.powerup2018.Robot;
import org.team4909.bionicframework.operator.controllers.BionicF310;


 public class AssistedDrive extends Commandable {

   // Threshold of how far off the target we can be for the drive assisted to work
  private static double thresh = 0.7;
  private static double max_offset = 23;

   public AssistedDrive() {
    requires(Robot.drivetrain);
    requires(Robot.vision);
  }

  public void initialize() {
      Robot.vision.setLights(3.0);
  }

   // Called repeatedly when this Command is scheduled to run
  protected void execute() {
      System.out.println("X offset" + Robot.vision.getXOffset());
    if (Math.abs(Robot.vision.getXOffset()) > thresh
        && Math.abs(Robot.vision.getXOffset()) < max_offset) {
        Robot.drivetrain.driveAssisted(
        Robot.driverGamepad.getSensitiveAxis(BionicF310.LY), Robot.vision.getXOffset());
        SmartDashboard.putNumber("Block", 1);

     } else if (Math.abs(Robot.vision.getXOffset()) > max_offset) {
        Robot.drivetrain.arcadeDrive(Robot.driverGamepad.getSensitiveAxis(BionicF310.LY),
         -Robot.driverGamepad.getSensitiveAxis(BionicF310.RX));
         SmartDashboard.putNumber("Block", 2);

     } else {
      Robot.drivetrain.arcadeDrive(Robot.driverGamepad.getSensitiveAxis(BionicF310.LY), 0.0);
      SmartDashboard.putNumber("Block", 3);

     }
  }

   // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return false;
  }

   // Called once after isFinished returns true
  protected void end() {
    Robot.vision.setLights(1.0);
  }

   // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
  }
}