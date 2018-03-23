package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RunWhenDistanceLeft extends Command {
	
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN_AUTON;

	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Command command;
	double distance;
	boolean finished = false;
	
	/**
	 * Runs a command when there is a given distance in inches left to drive
	 * 
	 * @param command - the command to run
	 * @param distance - distance in inches
	 */
    public Auto_RunWhenDistanceLeft(Command command, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    		this.command = command;
    		this.distance = distance / (Math.PI * RobotMap.WHEEL_DIAMETER);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		log.add("Initialize", Log.Level.TRACE);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double distanceTraveled = 
    				(Robot.drivetrain.getEncoders().getLeftTrajPos() + Robot.drivetrain.getEncoders().getRightTrajPos()) / 2;
    		double distanceToTravel = Robot.drivetrain.getAuton().getDistanceToTravel();
    		
    		if (distanceToTravel == 0) {
    			distanceToTravel = 1000;
    		}
    		
    		log.add("DistanceToTravel: " + distanceToTravel + ", DistanceTraveled: " + distanceTraveled, Log.Level.DEBUG_PERIODIC);
    		
    		if (distanceToTravel - distanceTraveled < distance) {
    			command.start();
    			finished = true;
    		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
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
