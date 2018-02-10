package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.ADIS16448_IMU;
import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Drivetrain_TankDrive;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;


/** Drivetrain ****************************************************************
 * The drivetrain subsystem for the robot.
 */
public class Drivetrain extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_DRIVETRAIN;
	private static final int CAN_LEFT_MOTOR = RobotMap.CAN_LEFT_MOTOR;
	private static final int CAN_RIGHT_MOTOR = RobotMap.CAN_RIGHT_MOTOR;
	private static final boolean HAS_FOLLOWERS = RobotMap.HAS_FOLLOWERS;
	private static final boolean HAS_ENCODERS = RobotMap.HAS_ENCODERS;
	private static final boolean HAS_AUTON = RobotMap.HAS_AUTON;
	private static final NeutralMode BRAKE_MODE = RobotMap.DRIVETRAIN_BRAKE_MODE;
	private static final boolean REVERSE_LEFT_MOTOR = RobotMap.REVERSE_LEFT_MOTOR;
	private static final boolean REVERSE_RIGHT_MOTOR = RobotMap.REVERSE_RIGHT_MOTOR;
	private static final int SHIFT_SOLENOID = RobotMap.SHIFT_SOLENOID;
	private static final boolean STARTS_HIGH = RobotMap.STARTS_HIGH_GEAR;
	
	
	/** Instance Variables ****************************************************/
	private Log log = new Log(LOG_LEVEL, getName());
	private Solenoid shift = new Solenoid(SHIFT_SOLENOID);
	private TalonSRX leftMotor = new TalonSRX(CAN_LEFT_MOTOR);
	private TalonSRX rightMotor = new TalonSRX(CAN_RIGHT_MOTOR);
	private ADIS16448_IMU gyro = new ADIS16448_IMU();
	private DrivetrainFollowers followers;
	private DrivetrainEncoders encoders;
	private DrivetrainAuton auton;
	private boolean isHighGear = STARTS_HIGH;

	
	/** Drivetrain ************************************************************
	 * Set up the talons for desired behavior.
	 */
	public Drivetrain() {
		log.add("Constructor", LOG_LEVEL);
		
		if (HAS_FOLLOWERS) followers = new DrivetrainFollowers();
		if (HAS_ENCODERS) {
			encoders = new DrivetrainEncoders(leftMotor, rightMotor);
			
			if (HAS_AUTON) auton = 
					new DrivetrainAuton(leftMotor, rightMotor, encoders);
		}
		
		initMotor(leftMotor, REVERSE_LEFT_MOTOR);
		initMotor(rightMotor, REVERSE_RIGHT_MOTOR);
	}
	private void initMotor(TalonSRX motor, boolean reverse) {
		motor.setNeutralMode(BRAKE_MODE);
		motor.setInverted(reverse); 	// affects percent Vbus mode
		//motor.setSensorPhase(reverse); 	// affects closed-loop mode
	}
	
	
	/** initDefaultCommand ****************************************************
	 * Set the default command for the subsystem.
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new Drivetrain_TankDrive());
	}
	
	public void setHighGear(){
    	shift.set(!STARTS_HIGH);
    	isHighGear = true;
    }
    public void setLowGear(){
    	shift.set(STARTS_HIGH);
    	isHighGear = false;
    }
    public void ToggleGear(){
    	if (isHighGear){
    		setHighGear();
    	}
    	else {
    		setLowGear();
    	}
    }
	
	/** Methods for setting the motors in Percent Vbus mode ********************/
	public void setPower(double leftPower, double rightPower) {
		leftPower = safetyCheck(leftPower);
		rightPower = safetyCheck(rightPower);
				
		leftMotor.set(ControlMode.PercentOutput, leftPower);
		rightMotor.set(ControlMode.PercentOutput, rightPower);		
	}
	public void stop() {
		setPower(0.0, 0.0);
	}
	private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
	
	/** Make Gyro WORK FOR IT! *********************************************
	 * 
	 */
	public Rotation2d getGyro() {
		return Rotation2d.fromDegrees(gyro.getAngle());
	}
	
	
	/** Provide commands access to the encoders and autonomous ****************/
	public DrivetrainEncoders getEncoders() {
		return encoders;
	}
	public DrivetrainAuton getAuton() {
		return auton;
	}
}