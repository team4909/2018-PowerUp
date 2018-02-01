package org.team4909.bionicframework.hardware.sensors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class BionicSRXEncoder {
    public final FeedbackDevice feedbackDevice;

    public final boolean inverted;

    public final double p;
    public final double i;
    public final double d;

    // Use F of 1023 for percentVBus Feedforward (as found by @oblarg)
    public final double f = 1023;

    public BionicSRXEncoder(FeedbackDevice feedbackDevice, boolean inverted, double p, double i, double d) {
        this.feedbackDevice = feedbackDevice;

        this.inverted = inverted;

        this.p = p;
        this.i = i;
        this.d = d;
    }
}