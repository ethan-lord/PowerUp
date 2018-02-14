package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Gamepad;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Arm_Test extends Command {

    public Arm_Test() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double power = -Robot.oi.gamepad.getRawAxis(Gamepad.RIGHT_JOY_Y_AXIS);
    	power *= (power > 0) ? 0.5 : 0.25;
    	Robot.arm.setPower(power);
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