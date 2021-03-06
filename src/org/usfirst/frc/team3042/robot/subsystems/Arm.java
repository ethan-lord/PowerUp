package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_HoldPosition;
import org.usfirst.frc.team3042.robot.commands.Arm_Stop;
import org.usfirst.frc.team3042.robot.commands.Arm_Test;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Lifts over nine thousand
 */
public class Arm extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final int CAN_ARM_MOTOR = RobotMap.CAN_ARM_MOTOR;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int PIDIDX = RobotMap.PIDIDX;
	private static final double kP = RobotMap.ARM_KP;
	private static final double kI = RobotMap.ARM_KI;
	private static final double kD = RobotMap.ARM_KD;
	private static final double kF = RobotMap.ARM_KF;
	private static final int I_ZONE = RobotMap.ARM_I_ZONE;
	private static final int MANUAL_SPEED = RobotMap.ARM_MANUAL_SPEED;
	private static final int BOT_POS = RobotMap.ARM_BOTTOM_POS;
	private static final int MID_POS = RobotMap.ARM_MIDDLE_POS;
	private static final int TOP_POS = RobotMap.ARM_TOP_POS;
	private static final int MAGIC_ACCEL = RobotMap.ARM_MOTION_MAGIC_ACCELERATION;
	private static final int MAGIC_CRUISE = RobotMap.ARM_MOTION_MAGIC_CRUISE_VELOCITY;
	private static final int MAX_POS = RobotMap.ARM_MAX_POSITION;
	private static final int MIN_POS = RobotMap.ARM_MIN_POSITION;
	private static final int FRAME_POS = RobotMap.ARM_FRAME_PERIMITER;
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_ARM;
	public static final boolean REVERSE_PHASE = RobotMap.ARM_REVERSE_SENSOR_PHASE;
	
	/** Instance Variables ****************************************************/
	private TalonSRX armTalon = new TalonSRX(CAN_ARM_MOTOR);
	private int currentPreset = 2;
	private int currentGoalPos = FRAME_POS;
	public Position[] positionFromInt = new Position[]{Position.BOTTOM, Position.MIDDLE, Position.TOP};
	Log log = new Log(LOG_LEVEL, getName());
	
	public Arm(){
		initMotor(armTalon);
		initMotionMagic(armTalon);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new Arm_Test());
    	//setDefaultCommand(new Arm_Stop());
    	setDefaultCommand(new Arm_HoldPosition());
    }
    
    public void manual(int direction){
    	switch (direction) {
		case POVButton.UP:
			//setTalonPosition(currentGoalPos += MANUAL_SPEED);
			log.add("Error: " + armTalon.getClosedLoopError(PIDIDX) + "\nPosition: " + armTalon.getSelectedSensorPosition(PIDIDX), Log.Level.DEBUG);
			armTalon.set(ControlMode.PercentOutput, 0.3);
			break;
		case POVButton.DOWN:
			//setTalonPosition(currentGoalPos -= MANUAL_SPEED);
			log.add("Error: " + armTalon.getClosedLoopError(PIDIDX) + "\nPosition: " + armTalon.getSelectedSensorPosition(PIDIDX), Log.Level.DEBUG);
			armTalon.set(ControlMode.PercentOutput, -0.3);
			break;
		default:
			break;
    	}
	}
	
	public void cyclePreset(int direction){
		switch (direction) {
		case POVButton.UP:
			currentPreset = Math.min(currentPreset + 1, positionFromInt.length - 1);
			setPosition(positionFromInt[currentPreset]);
			break;
		case POVButton.DOWN:
			currentPreset = Math.max(currentPreset - 1, 0);
			setPosition(positionFromInt[currentPreset]);
			break;
		default:
			break;
		}
	}
	/**
	 * Returns the actual pos - not the enum one.
	 * @return <code>armTalon.getSelectedSensorPosition(PIDIDX);</code>
	 */
	public int getPosition(){
		log.add("Pot: " + currentGoalPos, Log.Level.DEBUG_PERIODIC);
		return armTalon.getSelectedSensorPosition(PIDIDX);
	}
	
	public int getCurrentGoalPos(){
		return currentGoalPos;
	}
	
	public int getCurrentPreset(){
		return currentPreset;
	}
	
	public static enum Position {
		BOTTOM, MIDDLE, TOP;
	}
	
	public void intoFrame(){
		setTalonPosition(FRAME_POS);
	}
	
	public void toIntake(){
		setTalonPosition(BOT_POS);
	}
    
    private void initMotor(TalonSRX motor) {
    	motor.setSensorPhase(REVERSE_PHASE);
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, I_ZONE, TIMEOUT);
		motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, PIDIDX, TIMEOUT);
	}
    
    public void initMotionMagic(TalonSRX motor){
		motor.configMotionAcceleration(MAGIC_ACCEL, TIMEOUT);
		motor.configMotionCruiseVelocity(MAGIC_CRUISE, TIMEOUT);
	}
    
	public int safetyCheck(int position) {
		return Math.max(Math.min(MAX_POS, position), MIN_POS);
	}
    
	public void setTalonPosition(int position) {
		log.add("Setting arm to position: " + safetyCheck(position), Log.Level.DEBUG);
		armTalon.set(ControlMode.Position, safetyCheck(position));
		log.add("Current arm position: " + getPosition(), Log.Level.DEBUG);
		currentGoalPos = safetyCheck(position);
	}
	
	public void setTalonPositionMagic(int position) {
		armTalon.set(ControlMode.MotionMagic, safetyCheck(position));
		currentGoalPos = safetyCheck(position);
	}
    
    public void setPosition(Position position) {
		switch (position) {
			case BOTTOM:
				setTalonPositionMagic(BOT_POS);
				currentPreset = 0;
                break;
			case MIDDLE:
				setTalonPositionMagic(MID_POS);
				currentPreset = 1;
				break;
			case TOP:
				setTalonPositionMagic(TOP_POS);
				currentPreset = 2;
				break;
			default:
				stop();
				break;
		}
    }
    public void setPreset(int preset){
    	currentPreset = preset;
    }
    public void stop() {
		setPower(armTalon, 0);
	}
    public void setPower(double power) {
    	setPower(armTalon, power);
    }
    private void setPower(TalonSRX talon, double power){
    	talon.set(ControlMode.PercentOutput, power);
    }
}

