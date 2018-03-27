package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Claw_ReleaseAuto extends Command {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_CLAW;
	public static final double RELEASE_TIME = RobotMap.CLAW_RELEASE_TIME;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Timer timer = new Timer();
	boolean timerStarted = false;

    public Claw_ReleaseAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

 // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Log.Level.TRACE);
    	
    	Robot.claw.release();
    	
    	timer.stop();
    	timer.reset();
    	timerStarted = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	log.add("Distance Left: " + Robot.claw.getDistance(), Log.Level.DEBUG);
    	
    	if (!timerStarted && Robot.claw.getDistance() > RobotMap.AUTO_CLAW_RELEASE_DISTANCE) {
    		timer.start();
    		timerStarted = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() > RELEASE_TIME);
    }

    // Called once after isFinished returns true
    protected void end() {
    	log.add("End", Log.Level.TRACE);
    	Robot.claw.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	log.add("Interrupted", Log.Level.TRACE);
    	Robot.claw.stop();
    }
}
