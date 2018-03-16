package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.lib.math.Kinematics;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Twist2d;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotPoseTracker {
	RobotPoseTracker instance = null;

	
	public RobotPoseTracker getInstance() {
		if (instance == null) {
			instance = new RobotPoseTracker();
		}
		
		return instance;
	}
	
	private final Notifier notifier;
	private final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			updatePose();
		}
	};
	
	private RigidTransform2d pose;
	private Twist2d measuredVelocity;
	
	private double oldLeftPos, oldRightPos;
	private Rotation2d oldHeading;
	
	private RobotPoseTracker() {
		notifier = new Notifier(runnable);
		notifier.startPeriodic(0.02);
	}
	
	public RigidTransform2d getPose() {
		return pose;
	}
	
	private void updatePose() {
		double leftPos = Robot.drivetrain.getEncoders().getLeftPosition();
		double rightPos = Robot.drivetrain.getEncoders().getRightPosition();
		double dLeftPos = leftPos - oldLeftPos;
		double dRightPos = rightPos - oldRightPos;
		
		Rotation2d currentHeading = Robot.drivetrain.getGyro();
		Rotation2d dHeading = oldHeading.inverse().rotateBy(currentHeading);
		
		final Twist2d odometryVelocity = generateOdometry(dLeftPos, dRightPos, currentHeading);
		
		pose = Kinematics.integrateForwardKinematics(pose, odometryVelocity);
		measuredVelocity = odometryVelocity;
		
		oldLeftPos = leftPos;
		oldRightPos = rightPos;
	}
	
	private Twist2d generateOdometry(double dLeftPos, double dRightPos, Rotation2d currentHeading) {
		final RigidTransform2d lastPosition = getPose();
		final Twist2d delta = Kinematics.forwardKinematics(lastPosition.getRotation(),
                dLeftPos, dRightPos, currentHeading);
		
		return delta;
	}
	
	public void outputToDashboard() {
		RigidTransform2d odometry = getPose();
        SmartDashboard.putNumber("robot_pose_x", odometry.getTranslation().x());
        SmartDashboard.putNumber("robot_pose_y", odometry.getTranslation().y());
        SmartDashboard.putNumber("robot_pose_theta", odometry.getRotation().getDegrees());
        SmartDashboard.putNumber("robot velocity", measuredVelocity.dx);
	}
	
}
