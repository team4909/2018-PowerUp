package org.team4909.bionicframework.motion;

public class MotionProfileConfig {
    public final double driveRotationTestFeet;
    public final double maxVelocityTicks;
    public final double ticksToFeet;

    public final double secondsFromNeutralToFull;

    public final int profileIntervalMs;
    public final double chassisWidthFeet;
    public final double cruiseVelocityFeet;
    public final double avgAccelerationFeet;
    public final double maxJerkFeet;

    public MotionProfileConfig(int profileIntervalMs, double cruisePercent, double wheelDiameterFeet,
                               double maxVelocityTicks, double secondsFromNeutralToFull,
                               double driveRotationTestFeet, double driveRotationTestRad) {
        // Motion Profile Processing Interval
        this.profileIntervalMs = profileIntervalMs;
        this.maxVelocityTicks = maxVelocityTicks;

        // Conversion Factors
        this.driveRotationTestFeet = driveRotationTestFeet;
        this.ticksToFeet = (Math.PI * wheelDiameterFeet) / (4 * 360); // e4t spec, with 4x subsampling
        this.chassisWidthFeet = (2 * driveRotationTestFeet) / driveRotationTestRad;

        // Cruise Velocity
        double maxVelocityFeet = maxVelocityTicks * ticksToFeet;
        this.cruiseVelocityFeet = cruisePercent * maxVelocityFeet;

        // Max Acceleration
        this.secondsFromNeutralToFull = secondsFromNeutralToFull;
        this.avgAccelerationFeet = maxVelocityFeet / secondsFromNeutralToFull;

        // Max Jerk
        this.maxJerkFeet = 60;
    }

    public double getProfileIntervalS(){
        return (double) profileIntervalMs / 1000;
    }
}
