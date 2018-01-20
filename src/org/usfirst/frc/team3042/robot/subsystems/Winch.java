package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Winch_Stop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {

	private static final int CAN_WINCH_MOTOR_FRONT_LEFT = RobotMap.CAN_WINCH_MOTOR_FRONT_LEFT;
	private static final int CAN_WINCH_MOTOR_FRONT_RIGHT = RobotMap.CAN_WINCH_MOTOR_FRONT_RIGHT;
	private static final int CAN_WINCH_MOTOR_REAR_LEFT = RobotMap.CAN_WINCH_MOTOR_REAR_LEFT;
	private static final int CAN_WINCH_MOTOR_REAR_RIGHT = RobotMap.CAN_WINCH_MOTOR_REAR_RIGHT;

	private TalonSRX winchMotorFrontLeft = new TalonSRX(CAN_WINCH_MOTOR_FRONT_LEFT);
	private TalonSRX winchMotorFrontRight = new TalonSRX(CAN_WINCH_MOTOR_FRONT_RIGHT);
	private TalonSRX winchMotorRearLeft = new TalonSRX(CAN_WINCH_MOTOR_FRONT_LEFT);
	private TalonSRX winchMotorRearRight = new TalonSRX(CAN_WINCH_MOTOR_FRONT_RIGHT);
	
	private double climbPower = RobotMap.WINCH_POWER;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Winch_Stop());
    }
    
    public void setPower(TalonSRX motor, double winchPower) {
		winchPower = safetyCheck(winchPower);
    
				
		motor.set(ControlMode.PercentOutput, winchPower);		
	}
    
    public void climb(){
    	
    }
    
    private void 
    
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

