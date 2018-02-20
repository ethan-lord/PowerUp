package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Gamepad;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HookDeploy_Test extends Command {

    public HookDeploy_Test() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.hookDeploy);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		double power = Robot.oi.gamepad.getRawAxis(Gamepad.RIGHT_JOY_Y_AXIS);
    		power *= 0.25;
    		Robot.hookDeploy.setPercentOutput(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
