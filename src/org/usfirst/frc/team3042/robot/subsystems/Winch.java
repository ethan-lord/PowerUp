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

	private static final int CAN_WINCH_MOTOR = RobotMap.CAN_WINCH_MOTOR;

	private TalonSRX winchMotorLeft = new TalonSRX(CAN_WINCH_MOTOR);
	private TalonSRX winchMotorRight = new TalonSRX(CAN_WINCH_MOTOR);
	
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
    	setPower(winchMotorLeft, climbPower);
    	setPower(winchMotorRight, climbPower);
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
}

