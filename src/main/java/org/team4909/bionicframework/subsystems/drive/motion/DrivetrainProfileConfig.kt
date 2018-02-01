package org.team4909.bionicframework.subsystems.drive.motion

import jaci.pathfinder.Waypoint

data class DrivetrainProfileConfig(val drivetrainConfig: DrivetrainConfig, val path: Array<Waypoint>)