package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.HookDeploy_HoldPosition;
import org.usfirst.frc.team3042.robot.commands.HookDeploy_SetPosition;
import org.usfirst.frc.team3042.robot.commands.HookDeploy_Stop;
import org.usfirst.frc.team3042.robot.subsystems.Arm.Position;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Hooky
 */
public class HookDeploy extends Subsystem {
	/** Configuration constants ******************************************************************/
	private static final int CAN_HOOK_MOTOR = RobotMap.CAN_HOOK_MOTOR;
	private static final int STOW_POS = RobotMap.HOOK_STOWED_POS;
	private static final int RDY_POS = RobotMap.HOOK_READY_POS;
	private static final int DELVR_POS = RobotMap.HOOK_DELIVERY_POS;
	private static final int SLOTIDX_1 = RobotMap.SLOTIDX_1;
	private static final int TIMEOUT = RobotMap.TALON_ERROR_TIMEOUT;
	private static final int FRAME_RATE = RobotMap.AUTON_FRAME_RATE;
	private static final int PIDIDX = RobotMap.PIDIDX;
	private static final double kP = RobotMap.HOOK_KP;
	private static final double kI = RobotMap.HOOK_KI;
	private static final double kD = RobotMap.HOOK_KD;
	private static final double kF = RobotMap.HOOK_KF;
	private static final int IZONE = RobotMap.HOOK_IZONE;
	public static final boolean REVERSE_PHASE = RobotMap.HOOK_DEPLOY_REVERSE_SENSOR_PHASE;
	
	/** Instance Variables ***********************************************************************/
	private TalonSRX hookTalon = new TalonSRX(CAN_HOOK_MOTOR);
	private int currentPreset = 0;
	private int currentGoalPos = STOW_POS;
	private Position[] positionFromInt = new Position[]{Position.STOWED, Position.READY, Position.DELIVERY};
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public HookDeploy(){
		initMotor(hookTalon);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new HookDeploy_HoldPosition(currentGoalPos));
    }
    public void stop() {
		setPower(hookTalon, 0);
    }
    private void setPower(TalonSRX talon, double power){
    		talon.set(ControlMode.PercentOutput, power);
    }
    public static enum Position {
		STOWED, READY, DELIVERY;
	}
    public void setPosition(Position position) {
		switch (position) {
			case STOWED:
				hookTalon.set(ControlMode.Position, STOW_POS);
				currentGoalPos = STOW_POS;
				currentPreset = 0;
                break;
			case READY:
				hookTalon.set(ControlMode.Position, RDY_POS);
				currentGoalPos = RDY_POS;
				currentPreset = 1;
				break;
			case DELIVERY:
				hookTalon.set(ControlMode.Position, DELVR_POS);
				currentGoalPos = DELVR_POS;
				currentPreset = 2;
				break;
			default:
				stop();
				break;
		}
    }
    public int getPosition(){
    	return hookTalon.getSelectedSensorPosition(PIDIDX);
    }
    private void initMotor(TalonSRX motor) {
    	motor.setSensorPhase(REVERSE_PHASE);
		motor.changeMotionControlFramePeriod(FRAME_RATE);
		motor.config_kP(SLOTIDX_1, kP, TIMEOUT);
		motor.config_kI(SLOTIDX_1, kI, TIMEOUT);
		motor.config_kD(SLOTIDX_1, kD, TIMEOUT);
		motor.config_kF(SLOTIDX_1, kF, TIMEOUT);
		motor.config_IntegralZone(SLOTIDX_1, IZONE, TIMEOUT);
		motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, PIDIDX, TIMEOUT);
	}
    public void setTalonPosition(int position){
    	hookTalon.set(ControlMode.Position, position);
    }
    public void setPercentOutput(double power){
    	hookTalon.set(ControlMode.PercentOutput, power);
    }
}

