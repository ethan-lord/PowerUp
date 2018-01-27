package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.lib.Logger;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CompressorSubsystem extends Subsystem {
	/** Configuration Constants ***********************************************/
	public static final Logger.Level LOG_LEVEL = RobotMap.LOG_COMPRESSOR;
	
	/** Instance Variables ****************************************************/
	Logger log = new Logger(LOG_LEVEL, getName());

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
    Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
   
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public CompressorSubsystem() {
    	log.add("Instantiating compressor", Logger.Level.TRACE);
        compressor.start();
        
        compressor.setClosedLoopControl(true);
        
        log.add("Finished instantiating compressor", Logger.Level.TRACE);
    }
}

