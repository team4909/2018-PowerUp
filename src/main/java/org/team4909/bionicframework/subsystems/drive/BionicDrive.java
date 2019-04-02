package org.team4909.bionicframework.subsystems.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import jaci.pathfinder.Waypoint;
import org.team4909.bionicframework.hardware.motor.BionicSRX;
import org.team4909.bionicframework.hardware.pneumatics.BionicSingleSolenoid;
import org.team4909.bionicframework.interfaces.Commandable;
import org.team4909.bionicframework.operator.controllers.BionicF310;
import org.team4909.bionicframework.operator.generic.BionicAxis;
import org.team4909.bionicframework.subsystems.drive.commands.DriveOI;
import org.team4909.bionicframework.subsystems.drive.commands.DriveRotate;
import org.team4909.bionicframework.subsystems.drive.commands.DriveTrajectory;
import org.team4909.bionicframework.subsystems.drive.commands.InvertDriveDirection;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainConfig;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainProfileUtil;
import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainTrajectory;

/**
 * BionicDrive abstracts away much of the underlying drivetrain functionality
 */
public class BionicDrive extends Subsystem {
    /* Hardware */
    public final BionicSRX leftSRX;
    public final BionicSRX rightSRX;
    private final BionicSingleSolenoid shifter;

    public final DriveOI defaultCommand;

    public boolean encoderOverride;

    /* Sensors */
    private final Gyro bionicGyro;
    public final DrivetrainProfileUtil pathgen;

    public double speedDeltaLimit, rotationDeltaLimit;

    private final double t;
    private double currentMaxVelocity, currentMaxAcceleration, currentMaxJerk = 0;
    private double lastVelocity, lastAcceleration = 0;
    public boolean profiling;

    final double kP = 0.035;
    final double kD = 0;

    /**
     * @param leftSRX              Left Drivetrain SRX
     * @param rightSRX             Right Drivetrain SRX
     * @param speedInputGamepad    Speed Input Gamepad/Joystick
     * @param speedInputAxis       Speed Input Axis
     * @param rotationInputGamepad Rotation Input Gamepad/Joystick
     * @param rotationInputAxis    Rotation Input Axis
     * @param bionicGyro           Gyro to Use for Closed-Loop
     */
    public BionicDrive(BionicSRX leftSRX, BionicSRX rightSRX,
                       BionicF310 speedInputGamepad, BionicAxis speedInputAxis, double speedMultiplier, double speedDeltaLimit,
                       BionicF310 rotationInputGamepad, BionicAxis rotationInputAxis, double rotationMultiplier, double rotationDeltaLimit,
                       DrivetrainConfig drivetrainConfig,
                       Gyro bionicGyro, BionicSingleSolenoid shifter) {
        super();

        this.leftSRX = leftSRX;
        this.rightSRX = rightSRX;

        leftSRX.configOpenloopRamp(0);
        rightSRX.configOpenloopRamp(0);

        this.speedDeltaLimit = speedDeltaLimit;
        this.rotationDeltaLimit = rotationDeltaLimit;

        this.bionicGyro = bionicGyro;
        this.pathgen = new DrivetrainProfileUtil(drivetrainConfig);
        this.t = pathgen.drivetrainConfig.getProfileIntervalS();

        this.shifter = shifter;

        this.defaultCommand = new DriveOI(this, leftSRX, rightSRX,
                speedInputGamepad, speedInputAxis, speedMultiplier,
                rotationInputGamepad, rotationInputAxis, rotationMultiplier);
    }

    @Override
    public void periodic() {
        double currentVelocity = getVelocity();
        double currentAcceleration = (currentVelocity - lastVelocity) / t;
        double currentJerk = (currentAcceleration - lastAcceleration) / t;

        if (currentVelocity > currentMaxVelocity) {
            currentMaxVelocity = currentVelocity;
        }

        if (currentAcceleration > currentMaxAcceleration) {
            currentMaxAcceleration = currentAcceleration;
        }

        if (currentJerk > currentMaxJerk) {
            currentMaxJerk = currentJerk;
        }

        lastVelocity = currentVelocity;
        lastAcceleration = currentAcceleration;

        if(profiling){
            System.out.println("V (ft/s): " + currentMaxVelocity + " A (ft/s^2):" + currentMaxAcceleration + " J (ft/sec^3):" + currentMaxJerk);
        }
    }

    public void resetProfiling(){
         currentMaxVelocity = 0;
         currentMaxAcceleration = 0;
         currentMaxJerk = 0;
      
         lastVelocity = 0;
         lastAcceleration = 0;

         bionicGyro.reset();
         leftSRX.setSelectedSensorPosition(0,0,0);
         rightSRX.setSelectedSensorPosition(0,0,0);
    }

    public void resetGyro(){
        bionicGyro.reset();
    }

    public double getVelocity(){
        double currentLeftVelocity = getLeftVelocity();
        double currentRightVelocity = getRightVelocity();

        return (currentLeftVelocity + currentRightVelocity)/2;
    }

    public double getLeftVelocity(){
        return (leftSRX.getSelectedSensorVelocity(0) * 10 * pathgen.drivetrainConfig.getTicksToFeet());
    }

    public double getRightVelocity(){
        return (rightSRX.getSelectedSensorVelocity(0) * 10 * pathgen.drivetrainConfig.getTicksToFeet());
    }

    /**
     * @return Returns Robot's Current Heading [0, 2pi)
     */
    public double getHeading() {
        return bionicGyro.getAngle();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(defaultCommand);
    }

    public Commandable invertDirection() {
        return new InvertDriveDirection(this);
    }

    public boolean getGear(){
        return shifter.get();
    }

    public Commandable changeGear() {
        return shifter.invert();
    }

    private Command driveTrajectory(DrivetrainTrajectory trajectory, double kp) {
        return new DriveTrajectory(this, leftSRX, rightSRX, trajectory, kp);
    }

    public Command driveDistance(double distance) {
        return driveDistance(distance, -1);
    }
    public Command driveDistance(double distance, double kp){
        return driveWaypoints(new Waypoint[]{
                new Waypoint(0,0,0),
                new Waypoint(distance,0,0)
        }, kp);
    }

    public Command driveRotation(double angle) {
        return new DriveRotate(this, leftSRX, rightSRX, angle,.0046,0,0);
    }
    public Command driveRotation(double angle, double kp, double ki, double kd) {
        return new DriveRotate(this, leftSRX, rightSRX, angle, kp, ki, kd);
    }

//    public Command driveRotationTest() {
//        return driveTrajectory(pathgen.getRotationTestTrajectory());
//    }

    public Command driveWaypoints(Waypoint[] points, double kp) {
        return driveTrajectory(pathgen.getTrajectory(points), kp);
    }

    public void driveAssisted(double throttle, double angleOffset) {
        // System.out.println("Angle offset = " + angleOffset);
        double correction = angleOffset * kP - angleOffset * kD;
        arcadeDrive(throttle * 0.5, correction);
      }
}
