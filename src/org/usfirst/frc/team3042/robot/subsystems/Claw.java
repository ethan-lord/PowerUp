package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Claw_Stop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *The CLAW
 */
public class Claw extends Subsystem {
	// configuration constants
	private TalonSRX clawRightTalon = new TalonSRX(RobotMap.CAN_CLAW_RIGHT_TALON);
	private TalonSRX clawLeftTalon = new TalonSRX(RobotMap.CAN_CLAW_LEFT_TALON);
	
	// instance variables
	private Solenoid clampLeft = new Solenoid(RobotMap.CLAMP_SOLENOID_LEFT);
	private Solenoid clampRight = new Solenoid(RobotMap.CLAMP_SOLENOID_RIGHT);
	private double intakePower = RobotMap.CLAW_INTAKE_POWER;
	private double releasePower = RobotMap.CLAW_RELEASE_POWER;
	private boolean isActive = RobotMap.STARTS_ACTIVE;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Claw_Stop());
    }
    
    public void setPower(TalonSRX talon, double power){
    	talon.set(ControlMode.PercentOutput, power);
    }
    
    public void stop() {
    	setPower(clawRightTalon, 0);
    	setPower(clawLeftTalon, 0);
    }
    
    public void intake(){
    	setPower(clawRightTalon, intakePower);
    	setPower(clawLeftTalon, intakePower);
    }
    
    public void release() {
    	setPower(clawRightTalon, releasePower);
    	setPower(clawLeftTalon, releasePower);
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

