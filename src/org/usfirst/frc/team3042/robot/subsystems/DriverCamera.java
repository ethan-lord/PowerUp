package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriverCamera extends Subsystem {
	
	UsbCamera camera;
	
	public DriverCamera(){
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

