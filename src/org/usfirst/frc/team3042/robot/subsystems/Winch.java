package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.ADIS16448_IMU;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Winch_Stop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *Winches used for climbing.
 */
public class Winch extends Subsystem {

	private static final int CAN_WINCH_MOTOR_FRONT_LEFT = RobotMap.CAN_WINCH_MOTOR_FRONT_LEFT;
	private static final int CAN_WINCH_MOTOR_FRONT_RIGHT = RobotMap.CAN_WINCH_MOTOR_FRONT_RIGHT;
	private static final int CAN_WINCH_MOTOR_REAR_LEFT = RobotMap.CAN_WINCH_MOTOR_REAR_LEFT;
	private static final int CAN_WINCH_MOTOR_REAR_RIGHT = RobotMap.CAN_WINCH_MOTOR_REAR_RIGHT;

	private TalonSRX winchMotorFrontLeft = new TalonSRX(CAN_WINCH_MOTOR_FRONT_LEFT);
	private TalonSRX winchMotorFrontRight = new TalonSRX(CAN_WINCH_MOTOR_FRONT_RIGHT);
	private TalonSRX winchMotorRearLeft = new TalonSRX(CAN_WINCH_MOTOR_REAR_LEFT);
	private TalonSRX winchMotorRearRight = new TalonSRX(CAN_WINCH_MOTOR_REAR_RIGHT);
	
	private double hasLoadThreshhold = RobotMap.WINCH_HAS_LOAD_THRESHHOLD;
	
	ADIS16448_IMU gyro = new ADIS16448_IMU();
	
	private Timer time = new Timer();
	private double oldTime = time.get();
	
	private double climbPower = RobotMap.WINCH_VERTICAL_BASE_POWER;
	//Use dimensions of the robot and mounting locations of winches to make triangles to determine how
	//much base power to give each winch.
	private double basePowerFL = climbPower;
	private double basePowerFR = climbPower;
	private double basePowerRL = climbPower;
	private double basePowerRR = climbPower;
	
	private double kPFL = RobotMap.KP_WINCH_FRONT_LEFT;
	private double kIFL = RobotMap.KI_WINCH_FRONT_LEFT;
	private double kDFL = RobotMap.KD_WINCH_FRONT_LEFT;

	private double kPFR = RobotMap.KP_WINCH_FRONT_RIGHT;
	private double kIFR = RobotMap.KI_WINCH_FRONT_RIGHT;
	private double kDFR = RobotMap.KD_WINCH_FRONT_RIGHT;

	private double kPRL = RobotMap.KP_WINCH_REAR_LEFT; 
	private double kIRL = RobotMap.KI_WINCH_REAR_LEFT; 
	private double kDRL = RobotMap.KD_WINCH_REAR_LEFT;

	private double kPRR = RobotMap.KP_WINCH_REAR_RIGHT;
	private double kIRR = RobotMap.KI_WINCH_REAR_RIGHT;
	private double kDRR = RobotMap.KD_WINCH_REAR_RIGHT;
	
	private double oldErrorLeftRight = 0;
	private double accumErrorLeftRight = 0;
	private double oldErrorFrontBack = 0;
	private double accumErrorFrontBack = 0;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Winch_Stop());
    }
    
    public void setPower(TalonSRX motor, double winchPower) {
		winchPower = safetyCheck(winchPower);
    
				
		motor.set(ControlMode.PercentOutput, winchPower);		
	}
    /**
     * sequentially ensures tension on the winches before beginning to climb.
     */
    public void prepareClimb(){
    	while(winchMotorFrontLeft.getOutputCurrent() < hasLoadThreshhold){
    		winchMotorFrontLeft.set(ControlMode.PercentOutput, .1);
    	}
    	while(winchMotorFrontRight.getOutputCurrent() < hasLoadThreshhold){
    		winchMotorFrontRight.set(ControlMode.PercentOutput, .1);
    	}
    	while(winchMotorRearLeft.getOutputCurrent() < hasLoadThreshhold){
    		winchMotorRearLeft.set(ControlMode.PercentOutput, .1);
    	}
    	while(winchMotorRearRight.getOutputCurrent() < hasLoadThreshhold){
    		winchMotorRearRight.set(ControlMode.PercentOutput, .1);
    	}
    }
    /**
     * uses gyroPIDCorrection after ensuring tension to assist in climbing
     */
    public void climb(){
    	prepareClimb();
    	
    	double[] motorPower = gyroPIDCorrection();
    	
    	setPower(winchMotorFrontLeft, motorPower[0]);
		setPower(winchMotorFrontRight, motorPower[1]);
		setPower(winchMotorRearLeft, motorPower[2]);
		setPower(winchMotorRearRight, motorPower[3]);
    }
    /**
     * uses a PID loop to correct the winch powers so the robot remains level.
     */
    private double[] gyroPIDCorrection() {
    	double powerFL = basePowerFL;
    	double powerRL = basePowerRL;
    	double powerFR = basePowerFR;
    	double powerRR = basePowerRR;
    	
    	double time = this.time.get();
    	double deltaTime = time - oldTime;
    	
    	//Left to Right.
    	double errorLeftRight = 0 - gyro.getAngleX(); //positive error tilts to the left.
    	double deltaErrorLeftRight = errorLeftRight - oldErrorLeftRight;
    	
    	powerFL += errorLeftRight * kPFL + accumErrorLeftRight * kIFL + (deltaErrorLeftRight / deltaTime) * kDFL;
    	powerRL += errorLeftRight * kPRL + accumErrorLeftRight * kIRL + (deltaErrorLeftRight / deltaTime) * kDRL;
    	powerFR -= errorLeftRight * kPFR + accumErrorLeftRight * kIFR + (deltaErrorLeftRight / deltaTime) * kDFR;
    	powerRR -= errorLeftRight * kPRR + accumErrorLeftRight * kIRR + (deltaErrorLeftRight / deltaTime) * kDRR;
    	
    	//Front to Back.
    	double errorFrontBack = 0 - gyro.getAngleY();// positive error tilts to the rear.
    	double deltaErrorFrontBack = errorFrontBack - oldErrorFrontBack;
    	
    	powerFL -= errorFrontBack * kPFL + accumErrorFrontBack * kIFL + (deltaErrorFrontBack / deltaTime) * kDFL;
    	powerRL += errorFrontBack * kPRL + accumErrorFrontBack * kIRL + (deltaErrorFrontBack / deltaTime) * kDRL;
    	powerFR -= errorFrontBack * kPFR + accumErrorFrontBack * kIFR + (deltaErrorFrontBack / deltaTime) * kDFR;
    	powerRR += errorFrontBack * kPRR + accumErrorFrontBack * kIRR + (deltaErrorFrontBack / deltaTime) * kDRR;
    	
    	//Prepare for next time through
    	oldTime = time;
    	accumErrorLeftRight += errorLeftRight;
    	accumErrorFrontBack += errorFrontBack;
    	oldErrorLeftRight = errorLeftRight;
    	oldErrorFrontBack = errorFrontBack;
    	
    	return new double[]{powerFL, powerRL, powerFR, powerRR};
    }
    
    private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
    
	public void stop() {
		setPower(winchMotorFrontLeft, 0.0);
		setPower(winchMotorFrontRight, 0.0);
		setPower(winchMotorRearLeft, 0.0);
		setPower(winchMotorRearRight, 0.0);
	}
}

