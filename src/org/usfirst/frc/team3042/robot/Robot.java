
package org.usfirst.frc.team3042.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.robot.commands.autonomous.Center_ChooseSwitchSide;
import org.usfirst.frc.team3042.robot.commands.autonomous.DoNothing;
import org.usfirst.frc.team3042.robot.commands.autonomous.DriveStraight;
import org.usfirst.frc.team3042.robot.commands.autonomous.Left_ChooseScaleSide;
import org.usfirst.frc.team3042.robot.commands.autonomous.Right_ChooseScaleSide;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
import org.usfirst.frc.team3042.robot.subsystems.Claw;
import org.usfirst.frc.team3042.robot.subsystems.DriverCamera;
import org.usfirst.frc.team3042.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;
import org.usfirst.frc.team3042.robot.subsystems.HookDeploy;
import org.usfirst.frc.team3042.robot.subsystems.POP;
import org.usfirst.frc.team3042.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot { 
	/** Configuration Constants ***********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_ROBOT;
	private static final boolean HAS_DRIVETRAIN = RobotMap.HAS_DRIVETRAIN;
	private static final boolean HAS_GYROSCOPE = RobotMap.HAS_GYROSCOPE;
	private static final boolean HAS_WINCH = RobotMap.HAS_WINCH;
	private static final boolean HAS_CLAW = RobotMap.HAS_CLAW;
	private static final boolean HAS_ELEVATOR = RobotMap.HAS_ELEVATOR;
	private static final boolean HAS_COMPRESSOR = RobotMap.HAS_COMPRESSOR;
	private static final boolean HAS_ARM = RobotMap.HAS_ARM;
	private static final boolean HAS_HOOKDEPLOY = RobotMap.HAS_HOOKDEPLOY;
	private static final boolean HAS_POP = RobotMap.HAS_POP;
	
	/** Create Subsystems *****************************************************/
	private Log log = new Log(LOG_LEVEL, "Robot");
	public static final Drivetrain 	drivetrain 	= (HAS_DRIVETRAIN) 	? new Drivetrain() : null;
	public static final Winch winch = (HAS_WINCH) ? new Winch() : null;

	public static final Claw claw = (HAS_CLAW) ? new Claw() : null;
	public static final Elevator elevator = (HAS_ELEVATOR) ? new Elevator() : null;
	public static final Arm arm = (HAS_ARM) ? new Arm() : null;
	public static final HookDeploy hookDeploy = (HAS_HOOKDEPLOY) ? new HookDeploy() : null; 
	public static final POP pop = (HAS_POP) ? new POP() : null;
	public static OI oi;
	public static final Compressor compressor = (HAS_COMPRESSOR) ? new Compressor() : null;
	public static final DriverCamera driverCamera = new DriverCamera();
	
	//public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	RobotPoseTracker poseTracker;
	
	private static String gameData = "";
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<Command>();
	
	public static boolean elevatorEmergencyMode = false;
	public static boolean armEmergencyMode = false;

	
	/** robotInit *************************************************************
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		log.add("Robot Init", Log.Level.TRACE);
		
		poseTracker = RobotPoseTracker.getInstance();
		
		oi = new OI();
		
		chooser.addObject("Left to Scale", new Left_ChooseScaleSide());
		chooser.addObject("Right to Scale", new Right_ChooseScaleSide());
		chooser.addObject("Center to switch", new Center_ChooseSwitchSide());
		chooser.addObject("Drive Stright", new DriveStraight());
		chooser.addDefault("Do nothing (default)", new DoNothing());
		SmartDashboard.putData("Auto Mode", chooser);
	}

	
	/** disabledInit **********************************************************
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		log.add("Disabled Init", Log.Level.TRACE);
		gameData = "";
		if(!RobotMap.IS_GEORGE) arm.setPosition(Arm.Position.TOP);//preset 2 is the up to frame position
	}

	
	/** disabledPeriodic ******************************************************
	 * Called repeatedly while the robot is is disabled mode.
	 */
	public void disabledPeriodic() {
		//searchForGameData();
		
		Scheduler.getInstance().run();
	}

	
	/** autonomousInit ********************************************************
	 * Run once at the start of autonomous mode.
	 */
	public void autonomousInit() {
		log.add("Autonomous Init", Log.Level.TRACE);
		
		autonomousCommand = chooser.getSelected();
		
		gameData = "";

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	
	/** autonomousPeriodic ****************************************************
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		searchForGameData();
		
		poseTracker.outputToDashboard();
		
		Scheduler.getInstance().run();
	}

	
	/** teleopInit ************************************************************
	 * This function is called when first entering teleop mode.
	 */
	public void teleopInit() {
		log.add("Teleop Init", Log.Level.TRACE);
		
		//if(!RobotMap.IS_PBOT) Robot.elevator.setZero();
		//stops elevator from working after auto if cross scale does not back up properly
		
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}


	/** teleopPeriodic ********************************************************
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		if(!RobotMap.IS_GEORGE) SmartDashboard.putNumber("Arm Position", Robot.arm.getPosition());
		if(!RobotMap.IS_GEORGE) SmartDashboard.putNumber("Elevator Position", Robot.elevator.getPosition());
		if(!RobotMap.IS_GEORGE) SmartDashboard.putNumber("Gyroscope", Robot.drivetrain.getGyroRaw());
		//Only log on primary and secondary robots, pbot would crash code with this
		Scheduler.getInstance().run();
		
		poseTracker.outputToDashboard();
		
//		SmartDashboard.putNumber("PDP Channel Left Drivetrain 0", pdp.getCurrent(0));
//		SmartDashboard.putNumber("PDP Channel Left Drivetrain 1", pdp.getCurrent(1));
//		SmartDashboard.putNumber("PDP Channel 2", pdp.getCurrent(2));
//		SmartDashboard.putNumber("PDP Channel Arm", pdp.getCurrent(3));
//		SmartDashboard.putNumber("PDP Channel 11", pdp.getCurrent(11));
//		SmartDashboard.putNumber("PDP Channel Elevator", pdp.getCurrent(12));
//		SmartDashboard.putNumber("PDP Channel 13", pdp.getCurrent(13));
//		SmartDashboard.putNumber("PDP Channel Right Drivetrain 14", pdp.getCurrent(14));
//		SmartDashboard.putNumber("PDP Channel Right Drivetrain 15", pdp.getCurrent(15));
	}

	
	/** testPeriodic **********************************************************
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void searchForGameData(){
		SmartDashboard.putBoolean("Game Data Exists", gameDataPresent());
		if(!gameDataPresent()){
			gameData = DriverStation.getInstance().getGameSpecificMessage();
		}
	}
	
	public static boolean gameDataPresent(){
		return gameData.length() >= 3;
	}
	
	public static String getGameData(){
		return gameData;
	}
	
	public static enum Side{
		LEFT, RIGHT
	}
	
	public static Side getSwitchSide(){
		if(gameDataPresent()){
			return (Robot.getGameData().substring(0, 1).equals("R")) ? Side.RIGHT : Side.LEFT;
		}
		else{
			return null;
		}
	}
	
	public static Side getScaleSide(){
		if(gameDataPresent()){
			return (gameData.substring(1, 2).equals("R")) ? Side.RIGHT : Side.LEFT;
		}
		else{
			return null;
		}
	}
}