package org.team4909.bionicframework.subsystems.drive.motion;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Path generation utility
 */
public class DrivetrainProfileUtil {
    private final String profile_dir = "/home/lvuser/profiles/";
    private final Config pathfinderConfig;
    public final DrivetrainConfig drivetrainConfig;

    /**
     * @param drivetrainConfig MotionProfile Generation Configuration
     */
    public DrivetrainProfileUtil(DrivetrainConfig drivetrainConfig) {
        this.drivetrainConfig = drivetrainConfig;

        pathfinderConfig = new Config(FitMethod.HERMITE_CUBIC, Config.SAMPLES_FAST,
                drivetrainConfig.getProfileIntervalS(), 5.8,
                drivetrainConfig.getMaxAcceleration(), drivetrainConfig.getMaxJerk());
    }

    public DrivetrainTrajectory getTrajectory(Waypoint[] points) {
        Trajectory trajectory = Pathfinder.generate(points, pathfinderConfig);
        TankModifier modifier = new TankModifier(trajectory).modify(drivetrainConfig.getChassisWidthFeet());

        return new DrivetrainTrajectory(
                drivetrainConfig,
                modifier.getLeftTrajectory(),
                modifier.getRightTrajectory()
        );
    }

    public DrivetrainTrajectory getRotationTestTrajectory() {
        Trajectory rotationTrajectory = Pathfinder.generate(new Waypoint[]{
                new Waypoint(0,0,0),
                new Waypoint(drivetrainConfig.getDriveRotationTestFeet(),0,0)
        }, pathfinderConfig);

        return new DrivetrainTrajectory(drivetrainConfig, rotationTrajectory);
    }

}
