package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *POPPY!!!!!!!!!!!!!!!!!
 */
public class POP extends Subsystem {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_POP;

	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());
	private Solenoid popSolenoid = new Solenoid(RobotMap.POP_SOLENOID);


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	/**
	 * THIS IS WHERE WE UNLEASH THE POP TO KILL EVERYONE!!!!!!!!!!!!!!!
	 */
	public void unleashThePOP() {
		popSolenoid.set(true);
	}
	
	public void leashThePOP() {
		popSolenoid.set(false);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	// This line exists so that it is not stupidly yelling at us.
    }
}

