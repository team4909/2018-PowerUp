package org.team4909.bionicframework.hardware.motor;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Notifier;

import jaci.pathfinder.Trajectory;
import org.team4909.bionicframework.hardware.motor.commandables.PercentOutputCommandable;
import org.team4909.bionicframework.hardware.motor.commandables.SmartOutputCommandable;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.operator.generic.BionicJoystick;

/**
 * Wrapper Class for CTRE WPI_TalonSRX implementing BionicFramework Commandables and Other Simplifications/Abstractions
 */
public class BionicSRX extends WPI_TalonSRX {
    private boolean finishedMotionProfile;

    /**
     * @param deviceNumber - CAN ID's of SRX Master
     * @param slaveNumbers - CAN ID's of SRX Followers
     */
    public BionicSRX(int deviceNumber, boolean invertGearbox,
                     int... slaveNumbers){
        super(deviceNumber);

        configVoltageCompSaturation(12, timeoutMs);
        enableVoltageCompensation(true);

        setInverted(invertGearbox);
        setNeutralMode(NeutralMode.Brake);

        for (int i = 0; i < slaveNumbers.length; i++) {
            WPI_VictorSPX follower = new WPI_VictorSPX(slaveNumbers[i]);

            follower.configVoltageCompSaturation(12, timeoutMs);
            follower.enableVoltageCompensation(true);

            follower.setInverted(invertGearbox);
            follower.setNeutralMode(NeutralMode.Brake);

            follower.follow(this);
        }
    }

    public BionicSRX(int deviceNumber, boolean invertGearbox,
                     FeedbackDevice feedbackDevice, boolean invertSensor,
                     double p, double i, double d,
                     int... slaveNumbers) {
        this(deviceNumber, invertGearbox, slaveNumbers);

        configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);

        setSensorPhase(invertSensor);
        zeroEncoderPosition();

        config_kPIDF(p,i,d,0);
    }

    public BionicSRX(int deviceNumber, boolean invertGearbox,
                     FeedbackDevice feedbackDevice, boolean invertSensor,
                     int... slaveNumbers) {
        this(deviceNumber, invertGearbox, feedbackDevice, invertSensor, 0,0,0, slaveNumbers);
    }

    public void config_kF(double value) {
        super.config_kF(pidIdx, value, timeoutMs);
    }

    public void config_kPIDF(double p, double i, double d, double f) {
        config_kP(0, p,0);
        config_kI(0, i,0);
        config_kD(0, d,0);
        config_kF(0, f,0);
    }

    /* Command-based */
    public void set(BionicJoystick joystick, BionicAxis axis) {
        set(joystick.getSensitiveAxis(axis));
    }

    /**
     * @param setpoint The percent output value between [-1,1] to set
     * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
     */
    public Commandable setPercentOutput(double setpoint) {
        return new PercentOutputCommandable(setpoint,this);
    }

    /**
     * @param mode     The control mode to set as found in CTRE documentation
     * @param setpoint The percent output value between [-1,1] to set
     * @return Returns a Commandable that can be used by the operator and autonomous CommandGroups
     */
    public Commandable setMode(ControlMode mode, double setpoint) {
        return new SmartOutputCommandable(mode, setpoint, this);
    }

    /* Sensor Feedback */
    private final int pidIdx = 0;
    private final int timeoutMs = 0;

    public void zeroEncoderPosition() {
        setSelectedSensorPosition(0, 0, timeoutMs);
    }

    public void configOpenloopRamp(double secondsFromNeutralToFull) {
        configOpenloopRamp(secondsFromNeutralToFull, timeoutMs);
    }

    /* Motion Profiling */
    private final int minBufferPoints = 20;

    private final Notifier processMotionProfileBuffer = new Notifier(this::processMotionProfileBuffer);

    /**
     * @param points Path consisting of waypoints to follow
     */
    public void initMotionProfile(int controlFramePeriod, TrajectoryPoint[] points) {
        this.changeMotionControlFramePeriod(controlFramePeriod);
        this.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);

        this.clearMotionProfileTrajectories();

        for (int i = 0; i < points.length; i++) {
            this.pushMotionProfileTrajectory(points[i]);
        }

        // Buffer Minimum Points At First
        while(!isBottomLevelBufferReady()){
            super.processMotionProfileBuffer();
        }

        // Lag behind for later points
        processMotionProfileBuffer.startPeriodic((double) controlFramePeriod / 1000);
    }

    public void runMotionProfile() {
        if (isBottomLevelBufferReady()) {
            this.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
            finishedMotionProfile = false;
        }
    }

    public void processMotionProfileBuffer() {
        if (isTopLevelBufferEmpty()) {
            processMotionProfileBuffer.stop();
        } else {
            super.processMotionProfileBuffer();
        }
    }

    private MotionProfileStatus getMotionProfileStatus() {
        MotionProfileStatus srxStatus = new MotionProfileStatus();

        this.getMotionProfileStatus(srxStatus);

        return srxStatus;
    }

    public void selectProfileSlot(int slotIdx) {
        super.selectProfileSlot(slotIdx, 0);
    }

    public void resetProfileSlot() {
        super.selectProfileSlot(pidIdx, 0);
    }

    /**
     * @return Returns whether the streamed subsystems profile has been completed or not
     */
    public boolean isMotionProfileFinished() {
        if (finishedMotionProfile == false && (this.getMotionProfileStatus()).isLast){
            finishedMotionProfile = true;
        }

        return finishedMotionProfile;
    }

    private boolean isTopLevelBufferEmpty() {
        return (this.getMotionProfileStatus()).topBufferCnt == 0;
    }

    private boolean isBottomLevelBufferReady() {
        return isTopLevelBufferEmpty() || (this.getMotionProfileStatus()).btmBufferCnt >= minBufferPoints;
    }

    public void enableFwdSoftLimit(int forwardLimit) {
        configForwardSoftLimitEnable(true, timeoutMs);
        configForwardSoftLimitThreshold(forwardLimit, timeoutMs);
    }

    public void enableRevSoftLimit(int reverseLimit) {
        configReverseSoftLimitEnable(true, timeoutMs);
        configReverseSoftLimitThreshold(reverseLimit, timeoutMs);
    }

    public void enableSoftLimits(int forwardLimit, int reverseLimit) {
        enableFwdSoftLimit(forwardLimit);
        enableRevSoftLimit(reverseLimit);
    }

    public void enableZeroOnFwdLimit() {
        configSetParameter(ParamEnum.eClearPositionOnLimitF, 1, 0, 0, timeoutMs);
    }

    public void enableZeroOnRevLimit() {
        configSetParameter(ParamEnum.eClearPositionOnLimitR, 1, 0, 0, timeoutMs);

    }

    public double getSelectedSensorPosition() {
        return getSelectedSensorPosition(0);
    }
}
