package org.team4909.bionicframework.motion;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class PathgenUtil {
	private Trajectory.Config config;
	private double drivebaseWidth;
	
	public PathgenUtil(Trajectory.Config config, double drivebaseWidth) {
		this.config = config;
		this.drivebaseWidth = drivebaseWidth;
	}
	
	public TankTrajectory getTankTrajectory(Waypoint[] points) {
		Trajectory trajectory = Pathfinder.generate(points, config);
		
		TankModifier modifier = new TankModifier(trajectory).modify(drivebaseWidth);
		
		return new TankTrajectory(trajectory, modifier.getLeftTrajectory(), modifier.getRightTrajectory());
	}
	
	public class TankTrajectory {
		public final Trajectory center;
		public final Trajectory left;
		public final Trajectory right;
		
		public TankTrajectory(Trajectory center, Trajectory left, Trajectory right) {
			this.center = center;
			this.left = left;
			this.right = right;
		}
	}
}
