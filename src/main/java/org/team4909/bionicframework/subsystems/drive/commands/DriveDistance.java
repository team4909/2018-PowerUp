package org.team4909.bionicframework.subsystems.drive.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import com.ctre.phoenix.motorcontrol.ControlMode;
import org.team4909.powerup2018.Robot;

public class DriveDistance extends PIDCommand {

    double direction;

    public DriveDistance(double distance, double kp, double ki, double kd)
    {

        super(kp,ki,kd);
        requires(Robot.drivetrain);
        if(distance<0){
            direction = -1;
        }else{
            direction = 1;
        }
        getPIDController().setSetpoint(Math.abs(distance));
    }
    @Override
    protected void initialize() {
        Robot.drivetrain.resetProfiling();
        getPIDController().enable();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
//        System.out.println("ERROR: "+getPIDController().getError());
        return Math.abs(getPIDController().getError()) < 5;
    }

        @Override
        protected double returnPIDInput() {
            double ticks = Robot.drivetrain.leftSRX.getSelectedSensorPosition();
            double dist = (ticks/1440)*(Math.PI*6);
//            System.out.println("VALUE: "+ticks + " DIST: "+dist);
            return dist * direction;
        }


        @Override
        protected void usePIDOutput(double output) {

        if (output < .15 && output > .05) {
            output = .15;
        }if (output < -.15 && output > -.05) {
            output = -.15;
        }

        // limit the max speed
                       output = Math.min(.5, output);
            output = output * direction;

        Robot.drivetrain.leftSRX.set(ControlMode.PercentOutput,  output);
        Robot.drivetrain.rightSRX.set(ControlMode.PercentOutput, output);
    }


    @Override
    protected void end() {
        getPIDController().disable();
        Robot.drivetrain.leftSRX.set(0);
        Robot.drivetrain.rightSRX.set(0);
    }
}
