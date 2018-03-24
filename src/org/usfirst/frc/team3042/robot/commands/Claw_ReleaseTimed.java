package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Claw_ReleaseTimed extends Command {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_CLAW;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Timer time = new Timer();
	double duration;// measured in seconds
	double timeZero;

    public Claw_ReleaseTimed(double duration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.claw);
    	
    	this.duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Log.Level.TRACE);
    	
    	Robot.claw.release();
    	
    	time.start();
    	timeZero = time.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    protected boolean isFinished() {
        return time.get() - timeZero >= duration;
    }

    // Called once after isFinished returns true
    protected void end() {
    	log.add("End", Log.Level.TRACE);
    	time.stop();
    	Robot.claw.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	log.add("Interrupted", Log.Level.TRACE);
    	time.stop();
    	Robot.claw.stop();
    }
}
