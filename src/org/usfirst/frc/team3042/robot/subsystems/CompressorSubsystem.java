package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Compress the cube
 */
public class CompressorSubsystem extends Subsystem {
	/** Configuration Constants ***********************************************/
	public static final Log.Level LOG_LEVEL = RobotMap.LOG_COMPRESSOR;
	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, getName());

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
    Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
   
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public CompressorSubsystem() {
    	log.add("Instantiating compressor", Log.Level.TRACE);
        compressor.start();
        
        compressor.setClosedLoopControl(true);
        
        log.add("Finished instantiating compressor", Log.Level.TRACE);
    }
}

