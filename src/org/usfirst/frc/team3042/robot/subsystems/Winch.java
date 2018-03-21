package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.ADIS16448_IMU;
import org.usfirst.frc.team3042.robot.Robot;
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

	private static final int CAN_WINCH_MOTOR = RobotMap.CAN_WINCH_MOTOR_MASTER;
	private static final int CAN_WINCH_MOTOR_FOLLOWER = RobotMap.CAN_WINCH_MOTOR_FOLLOWER;

	private TalonSRX winchMotor = new TalonSRX(CAN_WINCH_MOTOR);
	private TalonSRX winchMotorFollow = new TalonSRX(CAN_WINCH_MOTOR_FOLLOWER);
	
	private double climbPower = RobotMap.WINCH_BASE_POWER;
	
	public Winch(){
		winchMotorFollow.set(ControlMode.Follower, CAN_WINCH_MOTOR);
		winchMotorFollow.setInverted(true);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Winch_Stop());
    }
    
    private void setPower(double winchPower) {
		winchPower = safetyCheck(winchPower);
		
		winchMotor.set(ControlMode.PercentOutput, winchPower);		
	}
    public void climb(){
    	setPower(climbPower);
    }
    
    private double safetyCheck(double power) {
		power = Math.min(1.0, power);
		power = Math.max(-1.0, power);
		return power;
	}
    
	public void stop() {
		setPower(0.0);
	}
	
	public void reverse() {
		setPower(-climbPower);
	}
}

