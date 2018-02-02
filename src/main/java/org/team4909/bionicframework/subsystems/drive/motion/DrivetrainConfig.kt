package org.team4909.bionicframework.subsystems.drive.motion

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
data class DrivetrainConfig(val profileIntervalMs: Int, val cruisePercent: Double, val wheelDiameterFeet: Double,
                       val maxVelocityTicks: Double, val secondsFromNeutralToFull: Double,
                       val driveRotationTestFeet: Double, val driveRotationTestRad: Double) {
    val ticksToFeet: Double
    val chassisWidthFeet: Double
    val cruiseVelocityFeet: Double
    val avgAccelerationFeet: Double
    val maxJerkFeet: Double

    val profileIntervalS: Double
        get() = profileIntervalMs.toDouble() / 1000

    init {
        this.ticksToFeet = Math.PI * wheelDiameterFeet / (4 * 360) // e4t spec, with 4x subsampling
        this.chassisWidthFeet = 2 * driveRotationTestFeet / driveRotationTestRad

        // Cruise Velocity
        val maxVelocityFeet = maxVelocityTicks * ticksToFeet
        this.cruiseVelocityFeet = cruisePercent * maxVelocityFeet
        this.avgAccelerationFeet = maxVelocityFeet / secondsFromNeutralToFull

        // Max Jerk
        this.maxJerkFeet = 60.0
    }

    fun toJSON(): String {
        return JSON.stringify(this);
    }
}
