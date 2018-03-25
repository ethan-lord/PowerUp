package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Claw_IntakeAuto extends Command {
	
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_CLAW;
	public static final double GRAB_TIME = RobotMap.CLAW_GRAB_TIME;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	private boolean isCubeIn = false;
	private boolean timerStarted = false;
	Timer timer = new Timer();
	
    public Claw_IntakeAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.claw);
    }

    protected void initialize() {
    	log.add("Initialize", Log.Level.TRACE);
    	
    	timer.stop();
    	timer.reset();
    	
    	isCubeIn = false;
    	timerStarted = false;
    	Robot.claw.unclamp();
    	Robot.claw.intake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isCubeIn = Robot.claw.isCubeIn();
    	if (isCubeIn){
    		Robot.claw.clamp();
    		if (!timerStarted){
    			timer.start();
    			timerStarted = true;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() > GRAB_TIME);
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
