package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.MotionProfileUtil;

public class DriveTrajectory extends Command {
    private final MotionProfileUtil.MotionProfileTrajectory trajectory;

    public DriveTrajectory(BionicDrive subsystem, MotionProfileUtil.MotionProfileTrajectory trajectory) {
        requires(subsystem);
        setInterruptible(false);

        this.trajectory = trajectory;
    }

    @Override
    protected void initialize() {
        leftSRX.setNeutralMode(NeutralMode.Brake);
        rightSRX.setNeutralMode(NeutralMode.Brake);

        leftSRX.initMotionProfile(trajectory.profileInterval, trajectory.left);
        rightSRX.initMotionProfile(trajectory.profileInterval, trajectory.right);
    }

    @Override
    protected void execute() {
        leftSRX.runMotionProfile();
        rightSRX.runMotionProfile();
    }

    @Override
    protected boolean isFinished() {
        return leftSRX.isMotionProfileFinished() && rightSRX.isMotionProfileFinished();
    }

    @Override
    protected void end() {
        System.out.println("Final Heading: " + getHeading() + "rad");
    }
}