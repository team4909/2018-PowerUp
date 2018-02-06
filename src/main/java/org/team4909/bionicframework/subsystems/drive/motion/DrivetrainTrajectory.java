package org.team4909.bionicframework.subsystems.drive.motion;

import com.ctre.phoenix.motion.TrajectoryPoint;
import jaci.pathfinder.Trajectory;
import org.team4909.bionicframework.hardware.motor.BionicSRX;

/**
 * SRX Motion Profiling Compliant Trajectory Abstraction Layer
 */
public class DrivetrainTrajectory {
    public final int profileInterval;

    public final TrajectoryPoint[] left;
    public final TrajectoryPoint[] right;

    /**
     * @param left  Left Trajectory Generated from Pathfinder
     * @param right Right Trajectory Generated from Pathfinder
     */
    public DrivetrainTrajectory(DrivetrainConfig config, Trajectory left, Trajectory right) {
        profileInterval = config.getProfileIntervalMs();

        this.left = BionicSRX.convertToSRXTrajectory(left, config.getTicksToFeet(), config.getCruiseVelocityFeet());
        this.right = BionicSRX.convertToSRXTrajectory(right, config.getTicksToFeet(), config.getCruiseVelocityFeet());
    }

    public DrivetrainTrajectory(DrivetrainConfig config, Trajectory rotation) {
        profileInterval = config.getProfileIntervalMs();

        left = BionicSRX.convertToSRXTrajectory(rotation, config.getTicksToFeet(), config.getCruiseVelocityFeet(), true);
        right = BionicSRX.convertToSRXTrajectory(rotation, config.getTicksToFeet(), config.getCruiseVelocityFeet(), false);
    }

}