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
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private TalonSRX winchMotor = new TalonSRX(CAN_WINCH_MOTOR);
	
	private double climbPower = RobotMap.WINCH_POWER;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Winch_Stop());
    }
    
    public void setPower(double winchPower) {
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
}

