package org.team4909.bionicframework.subsystems.drive.motion

import jaci.pathfinder.Waypoint
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
data class DrivetrainProfileConfig(val drivetrainConfig: DrivetrainConfig, val path: Array<Waypoint>){
    fun toJSON(): String {
        return JSON.stringify(this);
    }
}