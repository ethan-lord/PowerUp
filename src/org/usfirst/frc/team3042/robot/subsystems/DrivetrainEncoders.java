package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.RobotPoseTracker;
import org.usfirst.frc.team3042.robot.commands.DrivetrainEncoders_Dashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;


/** DrivetrainEncoders ***********************************************************
 * The encoders for the drivetrain.
 */
public class DrivetrainEncoders extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN_ENCODERS;
	private static final int COUNTS_PER_REVOLUTION = RobotMap.COUNTS_PER_REVOLUTION;
	private static final int FRAME_RATE = RobotMap.ENCODER_FRAME_RATE;
	private static final int PIDIDX = RobotMap.PIDIDX;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final boolean SENSOR_PHASE_LEFT = RobotMap.SENSOR_PHASE_LEFT;
	private static final boolean SENSOR_PHASE_RIGHT = RobotMap.SENSOR_PHASE_RIGHT;

	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	TalonSRX leftEncoder, rightEncoder;
	double leftPositionZero, rightPositionZero;
	int leftCountsZero, rightCountsZero;
	
	
	/** DrivetrainEncoders ****************************************************/
	public DrivetrainEncoders(TalonSRX leftMotor, TalonSRX rightMotor) {
		log.add("Constructor", LOG_LEVEL);
		
		leftEncoder = leftMotor;
		rightEncoder = rightMotor;
		
		initEncoder(leftEncoder, SENSOR_PHASE_LEFT);
		initEncoder(rightEncoder, SENSOR_PHASE_RIGHT);
													
		reset();
		RobotPoseTracker.getInstance().reset(new RigidTransform2d());
	}
	private void initEncoder(TalonSRX encoder, boolean sensorPhase) {
		encoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDIDX, TIMEOUT);
		encoder.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, FRAME_RATE, TIMEOUT);
		encoder.setSensorPhase(sensorPhase);
	}
	
	
	/** initDefaultCommand ****************************************************
	 * Set the default command for the subsystem.
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new DrivetrainEncoders_Dashboard());
	}

	
	/** reset *****************************************************************/
	public void reset() {
		leftPositionZero = leftEncoder.getSelectedSensorPosition(PIDIDX);
		rightPositionZero = rightEncoder.getSelectedSensorPosition(PIDIDX);
	}
	public void setToZero() {
		leftPositionZero = 0.0;
		rightPositionZero = 0.0;
		
		leftCountsZero = 0;
		rightCountsZero = 0;
	}
	
	public double getLeftPositionZero(){
		return leftPositionZero;
	}
	public double getRightPositionZero(){
		return rightPositionZero;
	}
	
	/** Get the encoder position or speed *************************************
	 * Position is given in Revolutions - getPosition()
	 * Counts given in encoder counts - getEncPosition()
	 * Speed returns RPM - getSpeed()
	 * Encoder speed returns counts per 100ms - getEncSpeed()
	 */
	public double getLeftPosition() {
		return (leftEncoder.getSelectedSensorPosition(PIDIDX) + leftPositionZero) / COUNTS_PER_REVOLUTION;
	}
	public double getRightPosition() {
		return (rightEncoder.getSelectedSensorPosition(PIDIDX) - rightPositionZero) / COUNTS_PER_REVOLUTION;
	}
	public double getLeftSpeed() {
		return leftEncoder.getSelectedSensorVelocity(PIDIDX) * 600 / COUNTS_PER_REVOLUTION;//600/COUNTS_PER_REVOLUTION conversion from counts per 100 ms to rpm
	}
	public double getRightSpeed() {
		return rightEncoder.getSelectedSensorVelocity(PIDIDX) * 600 / COUNTS_PER_REVOLUTION;//600/COUNTS_PER_REVOLUTION conversion from counts per 100 ms to rpm
	}
	
	public double getLeftSpeedNative() {
		return leftEncoder.getSelectedSensorVelocity(PIDIDX);
	}
	
	public double getRightSpeedNative() {
		return rightEncoder.getSelectedSensorVelocity(PIDIDX);
	}
	
	
	/** rpmToF ****************************************************************
	 * Convert RPM reading into an F-Gain
	 * Note that 1023 is the native full-forward power of the talons, 
	 * equivalent to setting the power to 1.0.
	 * The speed has to be converted from rpm to encoder counts per 100ms
	 * 
	 * so F = power * 1023 / speed
	 */
	public double rpmToF(double rpm, double power) {
		//Convert to counts per 100 ms
		double speed = rpm * 4 * COUNTS_PER_REVOLUTION / 600;
		double kF = power * 1023.0 / speed;
		return kF;
	}
	public double rpmToPower(double rpm, double kF) {
		//Convert to counts per 100 ms
		double speed = rpm * 4 * COUNTS_PER_REVOLUTION / 600;
		double power = kF * speed / 1023.0;
		return power;
	}
}