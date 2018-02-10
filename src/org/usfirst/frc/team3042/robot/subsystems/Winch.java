package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.ADIS16448_IMU;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Winch_Stop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Winches used for climbing.
 */
public class Winch extends Subsystem {

	private static final int CAN_WINCH_MOTOR_LEFT = RobotMap.CAN_WINCH_MOTOR_LEFT;
	private static final int CAN_WINCH_MOTOR_RIGHT = RobotMap.CAN_WINCH_MOTOR_RIGHT;

	private TalonSRX winchMotorLeft = new TalonSRX(CAN_WINCH_MOTOR_LEFT);
	private TalonSRX winchMotorRight = new TalonSRX(CAN_WINCH_MOTOR_RIGHT);
	
	private TalonSRX winchMotorOneSide = null;
	
	private double hasLoadThreshhold = RobotMap.WINCH_HAS_LOAD_THRESHHOLD;
	
	ADIS16448_IMU gyro = new ADIS16448_IMU();
	
	private Timer time = new Timer();
	private double oldTime = time.get();
	
	private double climbPower = RobotMap.WINCH_BASE_POWER;
	//Use dimensions of the robot and mounting locations of winches to make triangles to determine how
	//much base power to give each winch.
	private double basePowerL = climbPower;
	private double basePowerR = climbPower;
	
	private double kPL = RobotMap.KP_WINCH_LEFT;
	private double kIL = RobotMap.KI_WINCH_LEFT;
	private double kDL = RobotMap.KD_WINCH_LEFT;

	private double kPR = RobotMap.KP_WINCH_RIGHT;
	private double kIR = RobotMap.KI_WINCH_RIGHT;
	private double kDR = RobotMap.KD_WINCH_RIGHT;
	
	private double oldErrorLeftRight = 0;
	private double accumErrorLeftRight = 0;
	
	public Winch(){
		
	}
	public Winch(Side side){
		winchMotorOneSide = (side == Side.LEFT)? winchMotorLeft : winchMotorRight;
		//If we choose to control each side independantly, create two subsystems with this constructor.
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Winch_Stop());
    }
    
    private void setPower(TalonSRX motor, double winchPower) {
		winchPower = safetyCheck(winchPower);
    
				
		motor.set(ControlMode.PercentOutput, winchPower);		
	}
    /**
     * sequentially ensures tension on the winches before beginning to climb.
     */
    public void prepareClimb(){
    	while(winchMotorLeft.getOutputCurrent() < hasLoadThreshhold){
    		winchMotorLeft.set(ControlMode.PercentOutput, .1);
    	}
    	while(winchMotorRight.getOutputCurrent() < hasLoadThreshhold){
    		winchMotorRight.set(ControlMode.PercentOutput, .1);
    	}
    }
    /**
     * uses gyroPIDCorrection after ensuring tension to assist in climbing
     */
    public void climb(){
    	prepareClimb();
    	
    	double[] motorPower = gyroPIDCorrection();
    	
    	setPower(winchMotorLeft, motorPower[0]);
		setPower(winchMotorRight, motorPower[1]);
    }
    
    public void climbOneSide(){
    	if(winchMotorOneSide != null){
    		setPower(winchMotorOneSide, climbPower);
    	}
    	//Used only if we control manually both sides of the winch indepentantly
    	//Does nothing if set up for automatic leveling
    }
    /**
     * uses a PID loop to correct the winch powers so the robot remains level.
     */
    private double[] gyroPIDCorrection() {
    	double powerL = basePowerL;
    	double powerR = basePowerR;
    	
//    	double time = this.time.get();
//    	double deltaTime = time - oldTime;
//    	
//    	//Left to Right.
//    	double errorLeftRight = 0 - gyro.getAngleX(); //positive error tilts to the left.
//    	double deltaErrorLeftRight = errorLeftRight - oldErrorLeftRight;
//    	
//    	powerL += errorLeftRight * kPL + accumErrorLeftRight * kIL + (deltaErrorLeftRight / deltaTime) * kDL;
//    	powerR -= errorLeftRight * kPR + accumErrorLeftRight * kIR + (deltaErrorLeftRight / deltaTime) * kDR;
//    	
//    	//Prepare for next time through
//    	oldTime = time;
//    	accumErrorLeftRight += errorLeftRight;
//    	oldErrorLeftRight = errorLeftRight;
    	
    	return new double[]{powerL, powerR};
    }
    
    private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
    
	public void stop() {
		setPower(winchMotorLeft, 0.0);
		setPower(winchMotorRight, 0.0);
	}
	
	public void reverse() {
		setPower(winchMotorLeft, -climbPower);
		setPower(winchMotorRight, -climbPower);
	}
	
	public enum Side {
		LEFT, RIGHT;
	}
}

