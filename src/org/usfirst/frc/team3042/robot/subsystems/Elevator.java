package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.ElevatorPath;
import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Elevator_HoldPosition;
import org.usfirst.frc.team3042.robot.commands.Elevator_HoldPositionSimple;
import org.usfirst.frc.team3042.robot.commands.Elevator_Stop;
import org.usfirst.frc.team3042.robot.commands.Elevator_Test;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * We can't do anything without this
 */
public class Elevator extends Subsystem {
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	private TalonSRX elevatorTalon = new TalonSRX(RobotMap.CAN_ELEVATOR_TALON);
	private final int BOT_POS = RobotMap.ELEVATOR_BOTTOM_POSITION;
	private final int INT_POS = RobotMap.ELEVATOR_INTAKE_POSITION;
	private final int SWITCH_POS = RobotMap.ELEVATOR_SWITCH_POSITION;
	private final int LOW_SCALE_POS = RobotMap.ELEVATOR_LOW_SCALE_POSITION;
	private final int MID_SCALE_POS = RobotMap.ELEVATOR_MID_SCALE_POSITION;
	private final int HIGH_SCALE_POS = RobotMap.ELEVATOR_HIGH_SCALE_POSITION;
	private final int MAX_POS = RobotMap.ELEVATOR_MAX_POSITION;
	private final int MIN_POS = RobotMap.ELEVATOR_MIN_POSITION;
	private final int MANUAL_SPEED = RobotMap.ELEVATOR_MANUAL_SPEED;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int PIDIDX = RobotMap.PIDIDX;
	private static final double kP = RobotMap.ELEVATOR_KP;
	private static final double kI = RobotMap.ELEVATOR_KI;
	private static final double kD = RobotMap.ELEVATOR_KD;
	private static final double kF = RobotMap.ELEVATOR_KF;
	private static final int I_ZONE = RobotMap.ELEVATOR_I_ZONE;
	private static final int MAGIC_ACCEL = RobotMap.ELEVATOR_MOTION_MAGIC_ACCELERATION;
	private static final int MAGIC_CRUISE = RobotMap.ELEVATOR_MOTION_MAGIC_CRUISE_VELOCITY;
	
	/** Instance Variables ****************************************************/
	private Log log = new Log(LOG_LEVEL, getName());
	private int currentGoalPos = BOT_POS;
	private int currentPreset = 0;
	public static Position[] positionFromPreset = new Position[]{Position.BOTTOM, Position.INTAKE, Position.SWITCH, Position.LOW_SCALE, Position.MID_SCALE, Position.HIGH_SCALE};
	public static double[] distanceFromPreset = new double[] {0, 0, 0, 0, 0, 0};
	public static final double maxSpeed = RobotMap.ELEVATOR_MAX_SPEED;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	//setDefaultCommand(new Elevator_HoldPosition());
    	setDefaultCommand(new Elevator_Test());
    }
    
    public Elevator(){
    	initMotor(elevatorTalon);
    	initMotionMagic(elevatorTalon);
    }
    
    private void initMotor(TalonSRX motor) {
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, I_ZONE, TIMEOUT);
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDIDX, TIMEOUT);
		motor.setInverted(RobotMap.ELEVATOR_REVERSE);
	}
    
    public void initMotionMagic(TalonSRX motor){
		motor.configMotionAcceleration(MAGIC_ACCEL, TIMEOUT);
		motor.configMotionCruiseVelocity(MAGIC_CRUISE, TIMEOUT);
	}
    
    public void setPower(double power) {
    	setPower (elevatorTalon, power);
    }
    private void setPower(TalonSRX talon, double power){
    	talon.set(ControlMode.PercentOutput, power);
    }
    
	public void stop() {
		setPower(elevatorTalon, 0);
	}
	
	/**
	 * elevatorTalon.set(controlMode.MotionMagic, safetyCheck(position));
	 * @param position
	 */
	public void setTalonPositionMagic(int position) {
		elevatorTalon.set(ControlMode.MotionMagic, safetyCheck(position));
		currentGoalPos = position;
	}
	
	public void setTalonPosition(int position) {
		elevatorTalon.set(ControlMode.Position, safetyCheck(position));
		currentGoalPos = position;
	}

	
	public int safetyCheck(int position) {
		return Math.max(Math.min(MAX_POS, position), MIN_POS);
	}
	
	/**
	 * use this method to set Elevator Position to a Position - BOTTOM, INTAKE, SWITCH, LOW_SCALE, MID_SCALE, HIGH_SCALE;
	 * @param position
	 */
	public void setPosition(Position position) {
		switch (position) {
			case BOTTOM:
				log.add("Bottom", Log.Level.DEBUG);
				setTalonPositionMagic(BOT_POS);
				log.add("Botton position: " + BOT_POS, Log.Level.DEBUG);
                break;
			case INTAKE:
				log.add("Intake", Log.Level.DEBUG);
				setTalonPositionMagic(INT_POS);
				log.add("Intake position: " + INT_POS, Log.Level.DEBUG);
				break;
			case SWITCH:
				log.add("Switch", Log.Level.DEBUG);
				setTalonPositionMagic(SWITCH_POS);
				log.add("Switch position: " + SWITCH_POS, Log.Level.DEBUG);
				break;
			case LOW_SCALE:
				log.add("Low Scale", Log.Level.DEBUG);
				setTalonPositionMagic(LOW_SCALE_POS);
				log.add("Low scale position: " + LOW_SCALE_POS, Log.Level.DEBUG);
				break;
			case MID_SCALE:
				log.add("Mid Scale", Log.Level.DEBUG);
				setTalonPositionMagic(MID_SCALE_POS);
				log.add("Mid scale position: " + MID_SCALE_POS, Log.Level.DEBUG);
				break;
			case HIGH_SCALE:
				log.add("High Scale", Log.Level.DEBUG);
				setTalonPositionMagic(HIGH_SCALE_POS);
				log.add("High scale position: " + HIGH_SCALE_POS, Log.Level.DEBUG);
				break;
			default:
				stop();
				break;
		}
	}
	
	public void manual(int direction){
		switch (direction) {
			case POVButton.UP:
				setTalonPositionMagic(currentGoalPos += MANUAL_SPEED);
				break;
			case POVButton.DOWN:
				setTalonPositionMagic(currentGoalPos -= MANUAL_SPEED);
				break;
			default:
				break;
		}
	}
	
	public void cyclePreset(int direction){
		switch (direction) {
			case POVButton.UP:
				currentPreset = Math.min(currentPreset + 1, positionFromPreset.length - 1);
				setPosition(positionFromPreset[currentPreset]);
				break;
			case POVButton.DOWN:
				currentPreset = Math.max(currentPreset - 1, 0);
				setPosition(positionFromPreset[currentPreset]);
				break;
			default:
				break;
		}
	}
	
	public ElevatorPath elevatorPathFromOnePositionToAnotherPosition(int preset1, int preset2){
		ElevatorPath path = new ElevatorPath();
		path.addStraight(Elevator.distanceFromPreset[preset2] - Elevator.distanceFromPreset[preset1], Elevator.maxSpeed);
		return path;
	}
	
	public void pushPoint(	TrajectoryPoint leftPoint) {
		elevatorTalon.pushMotionProfileTrajectory(leftPoint);
	}
	public MotionProfileStatus getStatus() {
		MotionProfileStatus status = new MotionProfileStatus();
		elevatorTalon.getMotionProfileStatus(status);
		return status;
	}
	
	public void initMotionProfile() {		
		initMotor(elevatorTalon);
		disableMotionProfile();
		removeUnderrun();
	}
	
	public void enableMotionProfile() {
		elevatorTalon.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
	}
	
	public void disableMotionProfile() {
		elevatorTalon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
	}
	
	public void removeUnderrun() {
		elevatorTalon.clearMotionProfileHasUnderrun(TIMEOUT);
	}
	
	public int getPosition(){
		return elevatorTalon.getSelectedSensorPosition(PIDIDX);
	}
	
	public int getCurrentGoalPos(){
		return currentGoalPos;
	}
	
	public int getCurrentPreset(){
		return currentPreset;
	}
	
	public static enum Position {
		BOTTOM, INTAKE, SWITCH, LOW_SCALE, MID_SCALE, HIGH_SCALE;
	}
}

