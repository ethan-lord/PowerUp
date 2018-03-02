package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.command.Command;

/**
 * THIS COULD BE BAD
 */
public class Winch_ClimbOneSide extends Command {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_WINCH;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Winch winchSide;
	
    public Winch_ClimbOneSide(Winch winchSide) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.winchSide = winchSide;
    	requires(winchSide);//winchSide must be the side of the winch this command should require and control.
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Log.Level.TRACE);
    	winchSide.climbOneSide();
    	//new Wait(2.0).start();
    	//new HookDeploy_OverrideDefaultCommandWithStop().start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
