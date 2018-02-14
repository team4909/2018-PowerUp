package org.team4909.bionicframework.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.subsystems.drive.BionicDrive;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainTrajectory;

public class DriveDistance extends Command {
    private final BionicDrive bionicDrive;

    private final BionicSRX leftSRX;
    private final BionicSRX rightSRX;
    private final double distance;


    public DriveDistance(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX, double distance) {
        requires(bionicDrive);
        setInterruptible(false);

        this.bionicDrive = bionicDrive;
        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        this.leftSRX.setSelectedSensorPosition(0,0,0);
        this.rightSRX.setSelectedSensorPosition(0,0,0);

        this.distance = distance;
    }

    @Override
    protected void initialize() {
        bionicDrive.resetProfiling();

        leftSRX.set(ControlMode.Position, distance);
        rightSRX.set(ControlMode.Position, distance);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
