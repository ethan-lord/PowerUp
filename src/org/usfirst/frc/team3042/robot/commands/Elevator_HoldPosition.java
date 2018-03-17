package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * DON'T MOVE
 */
public class Elevator_HoldPosition extends Command {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	
    public Elevator_HoldPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Log.Level.TRACE);
    	
    	if(!Robot.elevatorEmergencyMode){
    		Robot.elevator.setTalonPositionMagic(Robot.elevator.getCurrentGoalPos());
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.elevatorEmergencyMode){
    		if(Robot.oi.gamepad.getPOV(0) == -1){
        		Robot.elevator.setPower(0.04);
        	}
        	else if(Robot.oi.gamepad.getPOV(0) == 0){
        		Robot.elevator.setPower(0.5);
        	}
        	else if(Robot.oi.gamepad.getPOV(0) == 180){
        		Robot.elevator.setPower(-0.5);
        	}
    	}
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
