package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	private TalonSRX clawRightMotor = new TalonSRX(RobotMap.CAN_CLAW_RIGHT_MOTOR);
	private TalonSRX clawLeftMotor = new TalonSRX(RobotMap.CAN_CLAW_LEFT_MOTOR);
	private Solenoid clampLeft = new Solenoid(RobotMap.CLAMP_SOLENOID_LEFT);
	private Solenoid clampRight = new Solenoid(RobotMap.CLAMP_SOLENOID_RIGHT);
	private double intakePower = RobotMap.CLAW_INTAKE_POWER;
	private boolean isActive = RobotMap.STARTS_ACTIVE;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setPower(TalonSRX motor, double power){
    	motor.set(ControlMode.PercentOutput, power);
    }
    public void intake(){
    	setPower(clawRightMotor, intakePower);
    	setPower(clawLeftMotor, intakePower);
    }
    public void clamp(){
    	clampLeft.set(true);
    	clampRight.set(true);
    	isActive = true;
    }
    public void unclamp(){
    	clampLeft.set(false);
    	clampRight.set(false);
    	isActive = false;
    }
    public void clampToggle(){
    	if (isActive){
    		unclamp();
    	}
    	else {
    		clamp();
    	}
    }
}

