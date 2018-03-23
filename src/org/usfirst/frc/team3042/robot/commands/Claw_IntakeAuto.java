package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The claw eats the power cube
 * - this one has an is finished when the arm changes positions.
 */
public class Claw_IntakeAuto extends Command {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_CLAW;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	
	/** Static Variables ******************************************************/
	static int armPos;// is the current preset of the arm.//used to make this command end.

    public Claw_IntakeAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		log.add("Initialize", Log.Level.TRACE);
    		armPos = Robot.arm.getCurrentPreset();
    		Robot.claw.intake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return armPos != Robot.arm.getCurrentPreset();
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
