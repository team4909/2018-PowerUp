package org.team4909.bionicframework.motion;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Path generation utility
 */
public class MotionProfileUtil {
    private final Trajectory.Config pathfinderConfig;
    public final MotionProfileConfig motionProfileConfig;

    /**
     * @param motionProfileConfig MotionProfile Generation Configuration
     */
    public MotionProfileUtil(MotionProfileConfig motionProfileConfig) {
        this.motionProfileConfig = motionProfileConfig;
        this.pathfinderConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
                motionProfileConfig.profileIntervalS, motionProfileConfig.cruiseVelocityFeet,
                motionProfileConfig.avgAccelerationFeet, motionProfileConfig.maxJerkFeet);
    }

    /**
     * @param points Path consisting of waypoints to follow
     * @return Returns SRX Motion Profiling Compliant Trajectories
     */
    public MotionProfileTrajectory getTrajectory(Waypoint[] points) {
        Trajectory trajectory = Pathfinder.generate(points, pathfinderConfig);

        TankModifier modifier = new TankModifier(trajectory).modify(motionProfileConfig.chassisWidth);

        return new MotionProfileTrajectory(modifier.getLeftTrajectory(), modifier.getRightTrajectory());
    }

    /**
     * SRX Motion Profiling Compliant Trajectory Abstraction Layer
     */
    public class MotionProfileTrajectory {
        public final int profileInterval;

        /**
         * Trajectory of Drivetrain Left
         */
        public final TrajectoryPoint[] left;

        /**
         * Trajectory of Drivetrain Right
         */
        public final TrajectoryPoint[] right;

        /**
         * @param left  Left Trajectory Generated from Pathfinder
         * @param right Right Trajectory Generated from Pathfinder
         */
        public MotionProfileTrajectory(Trajectory left, Trajectory right) {
            this.profileInterval = motionProfileConfig.profileIntervalMs;

            this.left = convertToSRXTrajectory(left);
            this.right = convertToSRXTrajectory(right);
        }

        private TrajectoryPoint[] convertToSRXTrajectory(Trajectory trajectory) {
            int length = trajectory.length();
            TrajectoryPoint[] parsedSRXTrajectory = new TrajectoryPoint[length];

            for (int i = 0; i < length; i++) {
                TrajectoryPoint point = new TrajectoryPoint();

                // Profile Data
                point.position = trajectory.get(i).position / motionProfileConfig.ticksToFeet;
                point.velocity = (trajectory.get(i).velocity / motionProfileConfig.cruiseVelocityFeet) / 10;

                // Configuration Data
                point.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;
                point.profileSlotSelect0 = 0;
                point.zeroPos = (i == 0);
                point.isLastPoint = (i == length - 1);
                point.velocity = point.zeroPos ? point.velocity : 0;

                parsedSRXTrajectory[i] = point;
            }

            return parsedSRXTrajectory;
        }
    }
}
