 package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.lib.MotionProfile;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3042.robot.subsystems.DrivetrainAuton;

import com.ctre.phoenix.motion.MotionProfileStatus;


/** DrivetrainAuton_Drive *****************************************************
 * Autonomous driving using motion profile.
 */
public class DrivetrainAuton_Drive extends Command {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN_AUTON;
	private static final int BUFFER_TRIGGER = RobotMap.AUTON_BUFFER_TRIGGER;
		
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Drivetrain drivetrain = Robot.drivetrain;
	DrivetrainAuton auton = Robot.drivetrain.getAuton();
	MotionProfile leftMotionProfile, rightMotionProfile;
	boolean isLast;
	double distance;
	
	
	/** Drivetrain_Auton ******************************************************/
	public DrivetrainAuton_Drive(Path path) {
		log.add("Constructor", Log.Level.TRACE);
		requires(drivetrain);
		
		leftMotionProfile = path.generateLeftPath();
		rightMotionProfile = path.generateRightPath();
		
		distance = path.getAvgDistance();
	}	
	
	/** initialize ************************************************************
	 * Called just before this Command runs the first time
	 */
	protected void initialize() {
		log.add("Initialize", Log.Level.TRACE);
		
		auton.setDistanceToTravel(distance);
		
		auton.initMotionProfile();
		
		for (int n=0; n< Math.min(leftMotionProfile.getLength(), rightMotionProfile.getLength()); n++) {
			leftMotionProfile.getPoint(leftMotionProfile.getLength() - 1).isLastPoint = true;
			rightMotionProfile.getPoint(rightMotionProfile.getLength() - 1).isLastPoint = true;
			auton.pushPoints(leftMotionProfile.getPoint(n), 
					rightMotionProfile.getPoint(n));
		}
	}

	
	/** execute ***************************************************************
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {
		MotionProfileStatus leftStatus = auton.getLeftStatus();
		MotionProfileStatus rightStatus = auton.getRightStatus();
		
		SmartDashboard.putNumber("Left Error", auton.getLeftError());
		SmartDashboard.putNumber("Right Error", auton.getRightError());
		
		log.add("Left Error: " + auton.getLeftError() + ", Right Error: " + auton.getRightError(), Log.Level.DEBUG_PERIODIC);
		
		if  ( (leftStatus.btmBufferCnt > BUFFER_TRIGGER) && 
				(rightStatus.btmBufferCnt > BUFFER_TRIGGER) ) {
			auton.enableMotionProfile();
		}
		if (leftStatus.hasUnderrun) {
			log.add("Left motion underrun", Log.Level.WARNING);
			auton.removeLeftUnderrun();
		}
		if (rightStatus.hasUnderrun) {
			log.add("Right motion underrun", Log.Level.WARNING);
			auton.removeRightUnderrun();
		}
		
		isLast = leftStatus.isLast || rightStatus.isLast;
	}
	
	
	/** isFinished ************************************************************	
	 * Make this return true when this Command no longer needs to run execute()
	 */
	protected boolean isFinished() {
		return isLast;
	}

	
	/** end *******************************************************************
	 * Called once after isFinished returns true
	 */
	protected void end() {
		log.add("End", Log.Level.TRACE);
		terminate();
	}

	
	/** interrupted ***********************************************************
	 * Called when another command which requires one or more of the same
	 * subsystems is scheduled to run
	 */
	protected void interrupted() {
		log.add("Interrupted", Log.Level.TRACE);
		terminate();
	}
	
	
	/** Graceful End **********************************************************/
	private void terminate() {
		auton.disableMotionProfile();
	}
}
