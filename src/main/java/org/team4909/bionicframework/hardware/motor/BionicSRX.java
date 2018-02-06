package org.team4909.bionicframework.hardware.motor;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
    /**
     * @param deviceNumber - CAN ID's of SRX Master
     * @param slaveNumbers - CAN ID's of SRX Followers
     */
    public BionicSRX(int deviceNumber, boolean invertGearbox,
                     int... slaveNumbers){
        super(deviceNumber);

        configVoltageCompSaturation(11.5, timeoutMs);
        enableVoltageCompensation(true);

        setInverted(invertGearbox);

        for (int i = 0; i < slaveNumbers.length; i++) {
            WPI_VictorSPX follower = new WPI_VictorSPX(slaveNumbers[i]);

            follower.setInverted(invertGearbox);
            follower.follow(this);
        }
    }

    public BionicSRX(int deviceNumber, boolean invertGearbox,
                     FeedbackDevice feedbackDevice, boolean invertSensor,
                     double p, double i, double d, double f,
                     int... slaveNumbers) {
        this(deviceNumber, invertGearbox, slaveNumbers);

        configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);

        setSensorPhase(invertSensor);
        zeroEncoderPosition();

        config_kP(pidIdx, p, timeoutMs);
        config_kI(pidIdx, i, timeoutMs);
        config_kD(pidIdx, d, timeoutMs);
        config_kF(pidIdx, f, timeoutMs);
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

    /* Motion Profiling */
    private final int minBufferPoints = 20;
    private final double bufferInterval = 0.005;

    private final Notifier processMotionProfileBuffer = new Notifier(this::processMotionProfileBuffer);

    public void configOpenloopRamp(double secondsFromNeutralToFull) {
        configOpenloopRamp(secondsFromNeutralToFull, timeoutMs);
    }

    public static TrajectoryPoint[] convertToSRXTrajectory(Trajectory trajectory, double conversionFactor, double cruiseVelocity) {
        int length = trajectory.length();
        TrajectoryPoint[] parsedSRXTrajectory = new TrajectoryPoint[length];

        for (int i = 0; i < length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();

            // Profile Data
            point.position = trajectory.get(i).x / conversionFactor;
            point.velocity = trajectory.get(i).velocity / cruiseVelocity;

            // Configuration Data
            point.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;
            point.profileSlotSelect0 = 0;
            point.zeroPos = (i == 0);
            point.isLastPoint = (i == length - 1);
            point.velocity = point.zeroPos ? point.velocity : 0;

            parsedSRXTrajectory[i] = point;
        }

        return parsedSRXTrajectory;
    }

    /**
     * @param points Path consisting of waypoints to follow
     */
    public void initMotionProfile(int profileIntervalMs, TrajectoryPoint[] points) {
        this.changeMotionControlFramePeriod(profileIntervalMs);
        this.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);

        this.clearMotionProfileTrajectories();

        for (int i = 0; i < points.length; i++) {
            this.pushMotionProfileTrajectory(points[i]);
        }

        processMotionProfileBuffer.startPeriodic(bufferInterval);
    }

    public void runMotionProfile() {
        if (isBottomLevelBufferReady()) {
            this.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
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
        return (this.getMotionProfileStatus()).isLast;
    }

    private boolean isTopLevelBufferEmpty() {
        return (this.getMotionProfileStatus()).topBufferCnt == 0;
    }

    private boolean isBottomLevelBufferReady() {
        return isTopLevelBufferEmpty() || (this.getMotionProfileStatus()).btmBufferCnt >= minBufferPoints;
    }

    public void enableSoftLimit(int startPosition, int endPosition){
        configForwardSoftLimitEnable(true, timeoutMs);
        configForwardSoftLimitThreshold(startPosition, timeoutMs);

        configReverseSoftLimitEnable(true, timeoutMs);
        configReverseSoftLimitThreshold(endPosition, timeoutMs);
    }

    public void enableZeroOnFwdLimit(){
        configSetParameter(ParamEnum.eClearPositionOnLimitF, 1, 0, 0, timeoutMs);
    }

    public void enableZeroOnRevLimit(){
        configSetParameter(ParamEnum.eClearPositionOnLimitR, 1, 0, 0, timeoutMs);
    }
}
