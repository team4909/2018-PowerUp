package org.team4909.bionicframework.subsystems.drive.motion

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
data class DrivetrainConfig(val profileIntervalMs: Int,
                            val wheelDiameterFeet: Double, val ticksPerRev: Int,
                            val maxVelocity: Double, val maxAcceleration: Double, val maxJerk: Double,
                            val driveRotationTestFeet: Double, val driveRotationTestRad: Double) {
    val ticksToFeet: Double
    val chassisWidthFeet: Double

    val profileIntervalS: Double
        get() = profileIntervalMs.toDouble() / 1000

    init {
        this.ticksToFeet = Math.PI * wheelDiameterFeet / (4 * ticksPerRev) // with 4x subsampling
        this.chassisWidthFeet = 2 * driveRotationTestFeet / driveRotationTestRad
    }

    fun toJSON(): String {
        return JSON.stringify(this);
    }
}
