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
	boolean toldToMoveDown;

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
    			toldToMoveDown = false;
    		} else {
    			Robot.elevator.setPower(RobotMap.ELEVATOR_LOWER_VELOCITY);
    			toldToMoveDown = true;
    		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int goalPos = Robot.elevator.getCurrentGoalPos();
		int currentPos = Robot.elevator.getPosition();
    	
		if(toldToMoveDown && goalPos != RobotMap.ELEVATOR_INTAKE_POSITION){//falls to the bottom of the control range to avoid driving the motor downward
			if(goalPos - RobotMap.ELEVATOR_POSITION_CONTROL_RANGE < currentPos){
				Robot.elevator.setPower(RobotMap.ELEVATOR_LOWER_VELOCITY);
			} else {
				toldToMoveDown = false;
			}
		} else if (Math.abs(goalPos - currentPos) < RobotMap.ELEVATOR_POSITION_CONTROL_RANGE){
			if (goalPos == RobotMap.ELEVATOR_INTAKE_POSITION) {
				Robot.elevator.setPower(0);
			} else {
				Robot.elevator.setTalonPosition(goalPos);
			}
		} else if (goalPos > currentPos) {
			Robot.elevator.setTalonPositionMagic(goalPos);
		} else {
			Robot.elevator.setPower(RobotMap.ELEVATOR_LOWER_VELOCITY);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; // should not end, this is an upgraded version of the hold position with the same purpose of being the default command.
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
