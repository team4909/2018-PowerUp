package org.team4909.bionicframework.motion;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class MotionProfileConfig {
    public final int profileIntervalMs;
    public final double profileIntervalS;

    public final double driveTestTicks;
    public final double ticksToFeet;
    public final double chassisWidth;

    public final double cruiseVelocityFeet;

    public final double secondsFromNeutralToFull;
    public final double avgAccelerationFeet;

    public final double maxJerkFeet;

    public MotionProfileConfig(int profileIntervalMs, double secondsFromNeutralToFull, double cruisePercent, double halfVoltageVelocityTestTicks, int driveTestTicks, double driveDistanceTestFeet, double driveRotationTestRad) {
        // Motion Profile Processing Interval
        this.profileIntervalMs = profileIntervalMs;
        this.profileIntervalS = (double) profileIntervalMs / 1000;

        // Conversion Factors
        this.driveTestTicks = driveTestTicks;
        this.ticksToFeet = driveDistanceTestFeet / driveTestTicks;
        this.chassisWidth = (2 * driveDistanceTestFeet) / driveRotationTestRad;

        // Max Velocity
        double maxVelocityTicks = (2 * halfVoltageVelocityTestTicks);
        double maxVelocityFeet = (10 * maxVelocityTicks) * ticksToFeet;

        // Cruise Velocity
        this.cruiseVelocityFeet = cruisePercent * maxVelocityFeet;

        // Max Acceleration
        this.secondsFromNeutralToFull = secondsFromNeutralToFull;
        this.avgAccelerationFeet = maxVelocityFeet / secondsFromNeutralToFull;

        // Max Jerk
        this.maxJerkFeet = 10;

        System.out.println("Profile Interval: " + profileIntervalMs + "ms");
        System.out.println("Drive Test Ticks: " + driveTestTicks + " ticks");
        System.out.println("Feet / Ticks: " + ticksToFeet);
        System.out.println("Effective Chassis Width: " + chassisWidth + "ft");
        System.out.println("Cruise Velocity: " + cruiseVelocityFeet + "ft/s");
        System.out.println("Seconds from Neutral to Full: " + secondsFromNeutralToFull + "s");
        System.out.println("Avg. Acceleration: " + avgAccelerationFeet + "ft/s^2");
        System.out.println("Max Jerk: " + maxJerkFeet + "ft/s^3");
    }
}
