package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * YOU SHALL NOT PASS (unless we say you can)
 */
public class Wait extends Command {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_ROBOT;

	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Timer time = new Timer();
	double duration;// measured in seconds
	double timeZero;
	
    public Wait(double duration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Log.Level.TRACE);
    	
    	time.start();
    	timeZero = time.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time.get() - timeZero >= duration;
    }

    // Called once after isFinished returns true
    protected void end() {
    	log.add("End", Log.Level.TRACE);
    	time.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	log.add("Interrupted", Log.Level.TRACE);
    	time.stop();
    }
}
