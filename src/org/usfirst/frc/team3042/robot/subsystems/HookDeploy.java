package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Hooky
 */
public class HookDeploy extends Subsystem {
	/** Configuration constants ******************************************************************/
	
	/** Instance Variables ***********************************************************************/
	private Solenoid hookDeploymentSolenoid = new Solenoid(RobotMap.HOOK_DEPLOYMENT_SOLENOID);
	
	public void unleashTheArm() {
		hookDeploymentSolenoid.set(true);
	}
	
	public void leashTheArm() {
		hookDeploymentSolenoid.set(false);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	// This line exists so that it is not stupidly yelling at us.
    }
}

