package org.team4909.bionicframework.subsystems.drive.motion;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;


/**
 * Path generation utility
 */
public class DrivetrainProfileUtil {
    private final Config pathfinderConfig;
    public final DrivetrainConfig drivetrainConfig;

    /**
     * @param drivetrainConfig MotionProfile Generation Configuration
     */
    public DrivetrainProfileUtil(DrivetrainConfig drivetrainConfig) {
        this.drivetrainConfig = drivetrainConfig;

        pathfinderConfig = new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_FAST,
                drivetrainConfig.getProfileIntervalS(), drivetrainConfig.getCruiseVelocityFeet(),
                drivetrainConfig.getAvgAccelerationFeet(), drivetrainConfig.getMaxJerkFeet());
    }

    public DrivetrainTrajectory getTrajectory(Waypoint[] points) {
        DrivetrainProfileConfig profileHash = new DrivetrainProfileConfig(drivetrainConfig, points);

        Trajectory trajectory = Pathfinder.generate(points, pathfinderConfig);
        TankModifier modifier = new TankModifier(trajectory).modify(drivetrainConfig.getChassisWidthFeet());

        Trajectory leftTrajectory = modifier.getLeftTrajectory();
        Trajectory rightTrajectory = modifier.getRightTrajectory();

        return new DrivetrainTrajectory(drivetrainConfig, leftTrajectory, rightTrajectory);
    }

//    public DrivetrainTrajectory getRotationalTrajectory(double distance) {
//        Trajectory trajectoryLeft = Pathfinder.generate(new Waypoint[]{
//                new Waypoint(0,0,0,),
//                new Waypoint(distance,0,0)
//        }, pathfinderConfig);
//        Trajectory trajectoryRight = Pathfinder.generate(pointsRight, pathfinderConfig);
//
//        return new DrivetrainTrajectory(trajectoryLeft, trajectoryRight);
//    }

}
