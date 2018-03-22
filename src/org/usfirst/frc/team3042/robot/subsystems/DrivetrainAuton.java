package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;


/** DrivetrainAuton ***********************************************************
 * The methods and information necessary for autonomous motion profile driving.
 */
public class DrivetrainAuton extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN_AUTON;
	private static final double WHEEL_DIAMETER = RobotMap.WHEEL_DIAMETER;
	private static final int COUNTS_PER_REVOLUTION = RobotMap.COUNTS_PER_REVOLUTION;
	private static final int PID_PROFILE = RobotMap.AUTON_PROFILE;
	private static final double kP = RobotMap.kP_AUTON;
	private static final double kI = RobotMap.kI_AUTON;
	private static final double kD = RobotMap.kD_AUTON;
	private static final double kF_LEFT = RobotMap.kF_DRIVE_LEFT;
	private static final double kF_RIGHT = RobotMap.kF_DRIVE_RIGHT;
	private static final double kF_HIGH_LEFT = RobotMap.kF_HIGH_DRIVE_LEFT;
	private static final double kF_HIGH_RIGHT = RobotMap.kF_HIGH_DRIVE_RIGHT;
	private static final int I_ZONE = RobotMap.I_ZONE_AUTON;
	private static final int DT_MS = RobotMap.AUTON_DT_MS;
	private static final int MAGIC_ACCEL = RobotMap.DRIVETRAIN_MOTION_MAGIC_ACCELERATION;
	private static final int MAGIC_CRUISE = RobotMap.DRIVETRAIN_MOTION_MAGIC_CRUISE_VELOCITY;
	//The Frame Rate is given in ms
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int PIDIDX = RobotMap.AUTON_PIDIDX;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TRAJPERIOD = RobotMap.TRAJPERIOD;
	private static final int BASE_TRAJPERIOD = RobotMap.BASE_TRAJPERIOD;

	
	/** Periodic Runnable *****************************************************
	 * Create a separate thread to push motion profile points out to the Talon
	 */
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() { 
			leftMotor.processMotionProfileBuffer();
			rightMotor.processMotionProfileBuffer();
		}
	}
    	
	
	/** Instance Variables ****************************************************/
	private Log log = new Log(LOG_LEVEL, getName());
	private TalonSRX leftMotor, rightMotor;
	private DrivetrainEncoders encoders;
	private Notifier notifier;
	private double distanceToTravel;
	

	/** DrivetrainAuton *******************************************************/
	public DrivetrainAuton(TalonSRX leftMotor, TalonSRX rightMotor, 
			DrivetrainEncoders encoders) {
		log.add("Constructor", LOG_LEVEL);
		
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.encoders = encoders;
		
		initMotor(leftMotor, kF_LEFT);
		initMotor(rightMotor, kF_RIGHT);
		
		initMotionMagic(leftMotor);
		initMotionMagic(rightMotor);

		/** Starting talons processing motion profile **/
		//Convert from ms to sec for the notifier
		double frameRateSec = (double)FRAME_RATE / 1000.0;
		notifier = new Notifier(new PeriodicRunnable());
		notifier.startPeriodic(frameRateSec);
	}
	private void initMotor(TalonSRX motor, double kF) {
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 
				FRAME_RATE, TIMEOUT);
		motor.configMotionProfileTrajectoryPeriod(DT_MS, TIMEOUT);
		
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, I_ZONE, TIMEOUT);
	}
	
	public void setFgain(TalonSRX motor, double kF) {
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
	}
	
	
	/** initDefaultCommand ****************************************************
	 * Set the default command for the subsystem.
	 */
	public void initDefaultCommand() {
	}
	
	/** prepareMotionMagic ****************************************************
	 * 
	 */
    public void initMotionMagic(TalonSRX motor){
		motor.configMotionAcceleration(MAGIC_ACCEL, TIMEOUT);
		motor.configMotionCruiseVelocity(MAGIC_CRUISE, TIMEOUT);
	}
    
    
    /** Motion Magic command methods ******************************************
     * Makes things move
     */
	public void setLeftPositionMotionMagic(double position) {
		leftMotor.set(ControlMode.MotionMagic, inchesToCounts(position) + Robot.drivetrain.getEncoders().getLeftPositionZero());
	}
	
	public void setRightPositionMotionMagic(double position) {
		rightMotor.set(ControlMode.MotionMagic, inchesToCounts(position) + Robot.drivetrain.getEncoders().getRightPositionZero());
	}
	
	public int inchesToCounts(double inches) {
		int counts = (int) (inches / (WHEEL_DIAMETER * Math.PI) * COUNTS_PER_REVOLUTION);
		return counts;
	}
	

	/** prepareMotionProfile *****************************************************
	 * Clears out any old trajectories and prepares to receive new trajectory 
	 * points.
	 */
	public void initMotionProfile() {		
		initMotor(leftMotor);
		initMotor(rightMotor);
		disableMotionProfile();
		removeUnderrun();
		encoders.setToZero();
	}
	private void initMotor(TalonSRX motor) {
		motor.clearMotionProfileTrajectories();
		motor.selectProfileSlot(PID_PROFILE, PIDIDX);
	}
    
	/** Motion Profile command methods ****************************************/
	public void pushPoints(	TrajectoryPoint leftPoint, 
							TrajectoryPoint rightPoint) {
		leftMotor.pushMotionProfileTrajectory(leftPoint);
		rightMotor.pushMotionProfileTrajectory(rightPoint);
	}
	public MotionProfileStatus getLeftStatus() {
		MotionProfileStatus status = new MotionProfileStatus();
		leftMotor.getMotionProfileStatus(status);
		return status;
	}
	public MotionProfileStatus getRightStatus() {
		MotionProfileStatus status = new MotionProfileStatus();
		rightMotor.getMotionProfileStatus(status);
		return status;
	}
	public void removeLeftUnderrun() {
		leftMotor.clearMotionProfileHasUnderrun(TIMEOUT);
	}
	public void removeRightUnderrun() {
		rightMotor.clearMotionProfileHasUnderrun(TIMEOUT);
	}
	private void removeUnderrun() {
		removeLeftUnderrun();
		removeRightUnderrun();
	}
	public void enableMotionProfile() {
		leftMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
		rightMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
	}
	public void holdMotionProfile() {
		leftMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
		rightMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
	}
	public void disableMotionProfile() {
		leftMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
		rightMotor.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
	}
	
	public double getLeftError() {
		return leftMotor.getClosedLoopError(PIDIDX);
	}
	
	public double getRightError() {
		return rightMotor.getClosedLoopError(PIDIDX);
	}
	
	public double getLeftGoal(){
		return leftMotor.getActiveTrajectoryPosition();
	}
	
	public double getRightGoal(){
		return rightMotor.getActiveTrajectoryPosition();
	}
	
	public void setDistanceToTravel(double distance) {
		distanceToTravel = distance;
	}
	
	public double getDistanceToTravel() {
		return distanceToTravel;
	}
}