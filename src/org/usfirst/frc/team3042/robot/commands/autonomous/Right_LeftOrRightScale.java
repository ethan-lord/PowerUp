package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Right_LeftOrRightScale extends Command {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_ROBOT;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());

    public Right_LeftOrRightScale() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("GameData: " + Robot.getGameData(), Log.Level.DEBUG);
    	log.add("Robot.getGameData().substring(1, 2) = " + Robot.getGameData().substring(1, 2) + ".", Log.Level.DEBUG);
    	log.add("Robot.getSwitchSide() = " + Robot.getSwitchSide(), Log.Level.DEBUG);
    	
    	if (Robot.getScaleSide() == Robot.Side.LEFT) {
    		new Right_LeftScale().start();
    	}
    	else {
    		new Right_RightScale().start();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
