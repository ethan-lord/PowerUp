package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;

import edu.wpi.first.wpilibj.Notifier;

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
		
		
		
		oldLeftPos = leftPos;
		oldRightPos = rightPos;
	}
	
}
