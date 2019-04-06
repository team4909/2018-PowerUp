package org.team4909.bionicframework.subsystems.drive.commands;

//import com.ctre.phoenix.motorcontrol.ControlMode;
//import edu.wpi.first.wpilibj.command.Command;
//import org.team4909.bionicframework.hardware.motor.BionicSRX;
//import org.team4909.bionicframework.subsystems.drive.BionicDrive;
//import org.team4909.bionicframework.subsystems.drive.motion.DrivetrainTrajectory;
//
//    public class DriveTrajectory extends Command {
//        private final DrivetrainTrajectory trajectory;
//
//        private final BionicDrive bionicDrive;
//        private final BionicSRX leftSRX, rightSRX;
//
//        boolean done = false;
//
//        public DriveTrajectory(BionicDrive bionicDrive, BionicSRX leftSRX, BionicSRX rightSRX, DrivetrainTrajectory trajectory, double kp) {
//            requires(bionicDrive);
//
//            if (kp != -1) {
//                leftSRX.config_kP(0, kp, 0);
//                rightSRX.config_kP(0, kp, 0);
//            }
//            this.trajectory = trajectory;
//
//            this.bionicDrive = bionicDrive;
//            this.leftSRX = leftSRX;
//            this.rightSRX = rightSRX;
//        }
//
//    @Override
//    protected void initialize() {
//        if (bionicDrive.encoderOverride){
//            cancel();
//        }
//
//        bionicDrive.resetProfiling();
//
//        leftSRX.set(ControlMode.Position, trajectory.left[trajectory.left.length-1].position);
//        rightSRX.set(ControlMode.Position, trajectory.right[trajectory.right.length-1].position);
//        }
//
//    @Override
//    protected void execute() {
//    }
//
//    @Override
//    protected boolean isFinished() {
////        System.out.println("Left Error: " + leftSRX.getClosedLoopError(0));// + " Right Error: " + rightSRX.getClosedLoopError(0)
//        if (Math.abs(leftSRX.getClosedLoopError(0)) > 200)
//            done = true;
//        return done && (Math.abs(leftSRX.getClosedLoopError(0)) < 110 ); //|| Math.abs(rightSRX.getClosedLoopError(0)) < 110
//    }
//
//    @Override
//    protected void end() {
//        leftSRX.set(0);
//        rightSRX.set(0);
//
////        System.out.println("Final Heading: " + bionicDrive.getHeading() + " degrees"); // NOT RADIANS
////        System.out.println("Left Error: " + leftSRX.getClosedLoopError(0) + " ticks"); // NOT RADIANS
////        System.out.println("Right Error: " + rightSRX.getClosedLoopError(0) + " ticks"); // NOT RADIANS
//    }
//}