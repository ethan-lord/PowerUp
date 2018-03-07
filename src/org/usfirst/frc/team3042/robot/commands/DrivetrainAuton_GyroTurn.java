package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivetrainAuton_GyroTurn extends Command {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_GYROSCOPE;
	private static final double kP = RobotMap.kP_GYRO;
	private static final double kI = RobotMap.kI_GYRO;
	private static final double kD = RobotMap.kD_GYRO;
	private static final int I_ZONE = RobotMap.I_ZONE_GYRO;
	private static final double ANGLE_TOLERANCE = RobotMap.ANGLE_TOLERANCE;
	private static final double MAX_SPEED = RobotMap.MAX_SPEED_GYRO;
	
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	Drivetrain drivetrain = Robot.drivetrain;
	double lastError, integralError, goalAngle;
	double gyroZero;
	
	
	/** Drivetrain_GyroTurn ***************************************************
	 * Required subsystems will cancel commands when this command is run.
	 * 
	 * distance is given in physical units matching the wheel diameter unit
	 * 
	 * speed is given in physical units per second. The physical units should 
	 * match that of the Wheel diameter.
	 */
	public DrivetrainAuton_GyroTurn(double angle) {
		log.add("Constructor", Log.Level.TRACE);
		requires(drivetrain);

		goalAngle = angle;
	}
	
	
	/** initialize ************************************************************
	 * Called just before this Command runs the first time
	 */
	protected void initialize() {
		log.add("Initialize", Log.Level.TRACE);

		drivetrain.stop();
		lastError = 0.0;
		integralError = 0.0;
		gyroZero = Robot.drivetrain.getGyroRaw();
	}

	
	/** execute ***************************************************************
	 * Called repeatedly when this Command is scheduled to run
	 */
	protected void execute() {
		double error = goalAngle - Robot.drivetrain.getGyroRaw() + gyroZero;
		integralError += error;
		if (Math.abs(error) > I_ZONE) integralError = 0;
		double deltaError = error - lastError;
		
		double Pterm = kP * error;
		double Iterm = kI * integralError;
		double Dterm = kD * deltaError;
		
		double correction = Pterm + Iterm + Dterm;
		
		correction = Math.min(MAX_SPEED, correction);
		correction = Math.max(-MAX_SPEED, correction);
		
		drivetrain.setPower(correction, -correction);
		
		lastError = error;
	}
	
	
	/** isFinished ************************************************************	
	 * Make this return true when this Command no longer needs to run execute()
	 */
	protected boolean isFinished() {
		return Math.abs(lastError) < ANGLE_TOLERANCE;
	}

	
	/** end *******************************************************************
	 * Called once after isFinished returns true
	 */
	protected void end() {
		log.add("End", Log.Level.TRACE);
		terminate();
	}

	
	/** interrupted ***********************************************************
	 * Called when another command which requires one or more of the same
	 * subsystems is scheduled to run
	 */
	protected void interrupted() {
		log.add("Interrupted", Log.Level.TRACE);
		terminate();
	}
	
	
	/** Graceful End **********************************************************/
	private void terminate() {
		drivetrain.stop();
	}
}
