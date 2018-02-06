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
                drivetrainConfig.getProfileIntervalS(), drivetrainConfig.getMaxVelocity(),
                drivetrainConfig.getMaxAcceleration(), drivetrainConfig.getMaxJerk());
    }

    public DrivetrainTrajectory getTrajectory(Waypoint[] points) {
        Trajectory leftTrajectory;
        Trajectory rightTrajectory;

        try {
            String profileJSON = new DrivetrainProfileConfig(drivetrainConfig, points).toJSON();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(profileJSON.getBytes());
            byte[] digest = md.digest();
            String profileHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

            File leftFile = new File(profile_dir, profileHash + "-left.traj");
            File rightFile = new File(profile_dir, profileHash + "-right.traj");

            if (leftFile.isFile() && rightFile.isFile()) {
                leftTrajectory = Pathfinder.readFromFile(leftFile);
                rightTrajectory = Pathfinder.readFromFile(rightFile);
            } else {
                Trajectory trajectory = Pathfinder.generate(points, pathfinderConfig);
                TankModifier modifier = new TankModifier(trajectory).modify(drivetrainConfig.getChassisWidthFeet());

                leftTrajectory = modifier.getLeftTrajectory();
                rightTrajectory = modifier.getRightTrajectory();

                Pathfinder.writeToFile(leftFile, leftTrajectory);
                Pathfinder.writeToFile(rightFile, rightTrajectory);
            }
        } catch (NoSuchAlgorithmException e){
            Trajectory trajectory = Pathfinder.generate(points, pathfinderConfig);
            TankModifier modifier = new TankModifier(trajectory).modify(drivetrainConfig.getChassisWidthFeet());

            leftTrajectory = modifier.getLeftTrajectory();
            rightTrajectory = modifier.getRightTrajectory();
        }

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
