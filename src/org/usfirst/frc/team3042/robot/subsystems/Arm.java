package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_Stop;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final int CAN_ARM_MOTOR = RobotMap.CAN_ARM_MOTOR;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int kP = RobotMap.ARM_KP;
	private static final int kI = RobotMap.ARM_KI;
	private static final int kD = RobotMap.ARM_KD;
	private static final int kF = RobotMap.ARM_KF;
	private static final int I_ZONE = RobotMap.ARM_I_ZONE;
	private static final int MANUAL_SPEED = RobotMap.ARM_MANUAL_SPEED;
	private static final int BOT_POS = RobotMap.ARM_BOTTOM_POS;
	private static final int MID_POS = RobotMap.ARM_MIDDLE_POS;
	private static final int TOP_POS = RobotMap.ARM_TOP_POS;
	/** Instance Variables ****************************************************/
	private TalonSRX armTalon = new TalonSRX(CAN_ARM_MOTOR);
	private int currentPreset = 0;
	private int currentPos = 0;
	private Position[] positionFromInt = new Position[]{Position.BOTTOM, Position.MIDDLE, Position.TOP};

	public Arm(){
		initMotor(armTalon);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Arm_Stop());
    }
    
    public void manual(int direction){
		if(direction == POVButton.UP){
			armTalon.set(ControlMode.Position, currentPos += MANUAL_SPEED);
		}
		else if(direction == POVButton.DOWN){
			armTalon.set(ControlMode.Position, currentPos -= MANUAL_SPEED);
		}
	}
	
	public void cyclePreset(int direction){
		if(direction == POVButton.UP){
			currentPreset = (currentPreset + 1) % positionFromInt.length; // modulus to keep the value in the range of 0-4
			setPosition(positionFromInt[currentPreset]);
		}
		else if(direction == POVButton.DOWN){
			currentPreset = (currentPreset - 1) % positionFromInt.length; // modulus to keep the value in the range of 0-4
			setPosition(positionFromInt[currentPreset]);
		}
	}
	
	public int getPosition(){
		return currentPos;
	}
	
	public int getCurrentPreset(){
		return currentPreset;
	}
	
	public static enum Position {
		BOTTOM, MIDDLE, TOP;
	}
    
    private void initMotor(TalonSRX motor) {
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, I_ZONE, TIMEOUT);
	}
    public void setPosition(Position position) {
		switch (position) {
			case BOTTOM:
				armTalon.set(ControlMode.Position, BOT_POS);
				currentPos = BOT_POS;
				currentPreset = 0;
                break;
			case MIDDLE:
				armTalon.set(ControlMode.Position, MID_POS);
				currentPos = MID_POS;
				currentPreset = 1;
				break;
			case TOP:
				armTalon.set(ControlMode.Position, TOP_POS);
				currentPos = TOP_POS;
				currentPreset = 2;
				break;
			default:
				stop();
				break;
		}
    }
    public void stop() {
		setPower(armTalon, 0);
	}
    private void setPower(TalonSRX talon, double power){
    	talon.set(ControlMode.PercentOutput, power);
    }
}

