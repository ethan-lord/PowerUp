package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.HookDeploy_Stop;
import org.usfirst.frc.team3042.robot.subsystems.Arm.Position;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *Hooky
 */
public class HookDeploy extends Subsystem {
	/** Configuration constants ******************************************************************/
	private static final int CAN_HOOK_MOTOR = RobotMap.CAN_HOOK_MOTOR;
	private static final int STOW_POS = RobotMap.HOOK_STOWED_POS;
	private static final int RDY_POS = RobotMap.HOOK_READY_POS;
	private static final int DELVR_POS = RobotMap.HOOK_DELIVERY_POS;
	
	/** Instance Variables ***********************************************************************/
	private TalonSRX hookTalon = new TalonSRX(CAN_HOOK_MOTOR);
	private int currentPreset = 0;
	private int currentPos = 0;
	private Position[] positionFromInt = new Position[]{Position.STOWED, Position.READY, Position.DELIVERY};
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new HookDeploy_Stop());
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
				currentPos = STOW_POS;
				currentPreset = 0;
                break;
			case READY:
				hookTalon.set(ControlMode.Position, RDY_POS);
				currentPos = RDY_POS;
				currentPreset = 1;
				break;
			case DELIVERY:
				hookTalon.set(ControlMode.Position, DELVR_POS);
				currentPos = DELVR_POS;
				currentPreset = 2;
				break;
			default:
				stop();
				break;
		}
    }
}

