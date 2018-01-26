package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Logger;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Elevator_Cycle_Positions extends Command {
	/** Configuration Constants ***********************************************/
	public static final Logger.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	
	/** Instance Variables ****************************************************/
	Logger log = new Logger(LOG_LEVEL, getName());
	private Command[] position;
	private int direction;
	
    public Elevator_Cycle_Positions(Command BOTTOM, Command INTAKE, Command SWITCH, Command LOW_SCALE, Command HIGH_SCALE, int direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	
    	position = new Command[]{BOTTOM, INTAKE, SWITCH, LOW_SCALE, HIGH_SCALE};
    	
    	this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	log.add("Initialize", Logger.Level.TRACE);
    	Robot.elevator.cyclePreset(direction);
    	
    	position[Robot.elevator.getCurrentPreset()].start();
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
    	log.add("End", Logger.Level.TRACE);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	log.add("Interrupted", Logger.Level.TRACE);
    }
}
