package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	private TalonSRX clawRightMotor = new TalonSRX(RobotMap.CAN_CLAW_RIGHT_MOTOR);
	private TalonSRX clawLeftMotor = new TalonSRX(RobotMap.CAN_CLAW_LEFT_MOTOR);
	private double intakePower = RobotMap.CLAW_INTAKE_POWER;
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
}

