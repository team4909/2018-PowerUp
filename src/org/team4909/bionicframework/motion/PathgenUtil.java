package org.team4909.bionicframework.motion;

import com.ctre.phoenix.motion.TrajectoryPoint;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class PathgenUtil {
	private Trajectory.Config config;
	private double drivebaseWidth;
	private double wheelDiameter;
	
	public PathgenUtil(Trajectory.Config config, double drivebaseWidth, double wheelDiameter) {
		this.config = config;
		this.drivebaseWidth = drivebaseWidth;
		this.wheelDiameter = wheelDiameter;
	}
	
	public TankTrajectory getTrajectory(Waypoint[] points) {
		Trajectory trajectory = Pathfinder.generate(points, config);
		
		TankModifier modifier = new TankModifier(trajectory).modify(drivebaseWidth);
		
		return new TankTrajectory(modifier.getLeftTrajectory(), modifier.getRightTrajectory());
	}
	
	public class TankTrajectory {
		public final TrajectoryPoint[] left;
		public final TrajectoryPoint[] right;
		
		public TankTrajectory(Trajectory left, Trajectory right) {
			this.left = convertToSRXTrajectory(left);
			this.right = convertToSRXTrajectory(right);
		}
		
		private TrajectoryPoint[] convertToSRXTrajectory(Trajectory trajectory) {
			int length = trajectory.length();
			TrajectoryPoint[] parsedSRXTrajectory = new TrajectoryPoint[length];
			
			for (int i = 0; i < length; i++) {
				TrajectoryPoint point = new TrajectoryPoint();
				
				// Profile Data
				point.position = convertFeetToTicks(trajectory.get(i).position);
				point.velocity = trajectory.get(i).velocity / config.max_velocity;
				
				// Configuration Data
				point.profileSlotSelect = 0;
				point.zeroPos = (i == 1);
				point.isLastPoint = (i == length - 1);
				point.velocity = point.zeroPos ? point.velocity : 0;
				
				parsedSRXTrajectory[i] = point;
	        }
			
			return parsedSRXTrajectory;
		}
		
		private double convertFeetToTicks(double meters) {
			double revPerTick = 1 / 4096;
					
			// Feet
			// Tick / Meter 
			// Rev / Ticks 
			return revPerTick * wheelDiameter;
		}
	}
}
