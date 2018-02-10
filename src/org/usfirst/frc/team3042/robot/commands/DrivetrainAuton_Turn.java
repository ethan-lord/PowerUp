package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3042.robot.subsystems.DrivetrainAuton;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Spins the robot around and around
 */
public class DrivetrainAuton_Turn extends Command {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN_AUTON;
	
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Drivetrain drivetrain = Robot.drivetrain;
	DrivetrainAuton auton = Robot.drivetrain.getAuton();
	Rotation2d goal, turn;

    public DrivetrainAuton_Turn(Rotation2d turn) {
		log.add("Constructor", Log.Level.TRACE);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	
    	this.turn = turn;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		log.add("Initialize", Log.Level.TRACE);
		
		goal = drivetrain.getGyro().rotateBy(turn);
		
		double distance = angleToDistance(turn);
		auton.setLeftPositionMotionMagic(-distance);
		auton.setRightPositionMotionMagic(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Rotation2d remainingTurn = goal.rotateBy(drivetrain.getGyro().inverse());
    	
    	double distance = angleToDistance(remainingTurn);
		auton.setLeftPositionMotionMagic(-distance);
		auton.setRightPositionMotionMagic(distance);
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
		log.add("Interrupt", Log.Level.TRACE);
    }
    
    private double angleToDistance(Rotation2d turn) {
    	double distance = RobotMap.ROBOT_WIDTH * turn.getRadians() / 2;
    	return distance;
    }
}
