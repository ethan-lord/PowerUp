package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Elevator_HoldPositionSimple extends Command {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());

    public Elevator_HoldPositionSimple() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		log.add("Initialize", Log.Level.TRACE);
    		
    		int goalPos = Robot.elevator.getCurrentGoalPos();
    		int currentPos = Robot.elevator.getPosition();
    		
    		if (goalPos > currentPos) {
    			Robot.elevator.setTalonPositionMagic(goalPos);
    		} else {
    			Robot.elevator.setPower(RobotMap.ELEVATOR_LOWER_VELOCITY);
    		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		int goalPos = Robot.elevator.getCurrentGoalPos();
		int currentPos = Robot.elevator.getPosition();
    	
		if (Math.abs(currentPos - goalPos) < RobotMap.ELEVATOR_POSITION_CONTROL_RANGE) {
			if (goalPos == RobotMap.ELEVATOR_BOTTOM_POSITION) {
				Robot.elevator.setPower(0);
			} else {
				Robot.elevator.setTalonPositionMagic(goalPos); // Not sure if this should just be regular position control or motion magic
			}
		} else if (goalPos > currentPos) {
			Robot.elevator.setTalonPositionMagic(goalPos);
		} else {
			Robot.elevator.setPower(RobotMap.ELEVATOR_LOWER_VELOCITY);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; // Not sure what to do here
    }

    // Called once after isFinished returns true
    protected void end() {
    		log.add("End", Log.Level.TRACE);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		log.add("Interrupt", Log.Level.TRACE);
    }
}
