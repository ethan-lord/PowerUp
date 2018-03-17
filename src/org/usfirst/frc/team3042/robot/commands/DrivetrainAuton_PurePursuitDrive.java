package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.Lookahead;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.RobotPoseTracker;
import org.usfirst.frc.team3042.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3042.robot.subsystems.DrivetrainAuton;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivetrainAuton_PurePursuitDrive extends Command {
	
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN_AUTON;
	private static final int BUFFER_TRIGGER = RobotMap.AUTON_BUFFER_TRIGGER;
		
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Drivetrain drivetrain = Robot.drivetrain;
	DrivetrainAuton auton = Robot.drivetrain.getAuton();
	Lookahead lookahead;
	RobotPoseTracker poseTracker;

    public DrivetrainAuton_PurePursuitDrive(Path path) {
    		log.add("Constructor", Log.Level.TRACE);
		requires(drivetrain);
		
		lookahead = new Lookahead(RobotMap.MIN_LOOK_AHEAD, RobotMap.MIN_LOOK_AHEAD_SPEED, 
				RobotMap.MAX_LOOK_AHEAD, RobotMap.MAX_LOOK_AHEAD_SPEED);
		poseTracker = RobotPoseTracker.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		log.add("Initialize", Log.Level.TRACE);
    		
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double currentSpeed = poseTracker.getPredictedVelocity().dx;
    		
    		double lookaheadDistance = lookahead.getLookaheadForSpeed(currentSpeed);
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		log.add("End", Log.Level.TRACE);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		log.add("Interrupted", Log.Level.TRACE);
    }
}
