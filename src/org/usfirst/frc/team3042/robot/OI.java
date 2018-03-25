package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.lib.ElevatorPath;
import org.usfirst.frc.team3042.lib.Log;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.robot.commands.Arm_CrisisAverted;
import org.usfirst.frc.team3042.robot.commands.Arm_CyclePositions;
import org.usfirst.frc.team3042.robot.commands.Arm_Drive;
import org.usfirst.frc.team3042.robot.commands.Arm_IntoFrame;
import org.usfirst.frc.team3042.robot.commands.Arm_EmergencyMode;
import org.usfirst.frc.team3042.robot.commands.Arm_ToIntake;
import org.usfirst.frc.team3042.robot.commands.Claw_Clamp;
import org.usfirst.frc.team3042.robot.commands.Claw_ClampIntake;
import org.usfirst.frc.team3042.robot.commands.Claw_Intake;
import org.usfirst.frc.team3042.robot.commands.Claw_IntakeAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_Release;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_Toggle;
import org.usfirst.frc.team3042.robot.commands.Claw_Unclamp;
import org.usfirst.frc.team3042.robot.commands.Claw_UnclampIntake;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_GyroTurn;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Turn;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_TurnCalibrate;
import org.usfirst.frc.team3042.robot.commands.Elevator_CyclePositions;
import org.usfirst.frc.team3042.robot.commands.Elevator_EmergencyMode;
import org.usfirst.frc.team3042.robot.commands.HookDeploy_Release;
import org.usfirst.frc.team3042.robot.commands.POP_Unleash;
import org.usfirst.frc.team3042.robot.commands.Winch_Climb;
import org.usfirst.frc.team3042.robot.commands.Winch_Reverse;
import org.usfirst.frc.team3042.robot.commands.autonomous.Center_LeftSwitch;
import org.usfirst.frc.team3042.robot.commands.autonomous.Center_RightSwitch;
import org.usfirst.frc.team3042.robot.commands.autonomous.DriveStraight;
import org.usfirst.frc.team3042.robot.commands.autonomous.Left_LeftScale;
import org.usfirst.frc.team3042.robot.commands.autonomous.Left_RightScale;
import org.usfirst.frc.team3042.robot.commands.autonomous.Right_LeftScale;
import org.usfirst.frc.team3042.robot.commands.autonomous.Right_RightScale;
import org.usfirst.frc.team3042.robot.commands.autonomous.Right_RightScaleRightSwitch;
import org.usfirst.frc.team3042.robot.commands.Drivetrain_Calibrate;
import org.usfirst.frc.team3042.robot.commands.Drivetrain_Shift;
import org.usfirst.frc.team3042.robot.commands.Elevator_CrisisAverted;
import org.usfirst.frc.team3042.robot.paths.CenterToLeftSwitch;
import org.usfirst.frc.team3042.robot.paths.CenterToRightSwitch;
import org.usfirst.frc.team3042.robot.paths.LeftToRightScale;
import org.usfirst.frc.team3042.robot.paths.RightToLeftScale;
import org.usfirst.frc.team3042.robot.paths.RightToRightScale;
import org.usfirst.frc.team3042.robot.paths.TestThing;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;
import org.usfirst.frc.team3042.robot.subsystems.HookDeploy;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {	
	/** Configuration Constants ***********************************************/
	private static final boolean IS_GEORGE = RobotMap.IS_GEORGE;
	private static final boolean IS_DABNEY = RobotMap.IS_DABNEY;
	private static final boolean IS_BUSHNELL = RobotMap.IS_BUSHNELL;
	private static final int USB_GAMEPAD = RobotMap.USB_GAMEPAD;
	private static final int USB_JOY_LEFT = RobotMap.USB_JOYSTICK_LEFT;
	private static final int USB_JOY_RIGHT = RobotMap.USB_JOYSTICK_RIGHT;
	private static final boolean USE_JOYSTICKS = RobotMap.USE_JOYSTICKS;
	private static final double JOYSTICK_DRIVE_SCALE = RobotMap.JOYSTICK_DRIVE_SCALE;
	private static final double JOYSTICK_DEAD_ZONE = RobotMap.JOYSTICK_DEAD_ZONE;
	private static final double TRIGGER_SPINNER_SCALE = RobotMap.TRIGGER_SPINNER_SCALE;
	private static final int GAMEPAD_LEFT_Y_AXIS = Gamepad.LEFT_JOY_Y_AXIS;
	private static final int GAMEPAD_RIGHT_Y_AXIS = Gamepad.RIGHT_JOY_Y_AXIS;
	private static final int JOYSTICK_Y_AXIS = Gamepad.JOY_Y_AXIS;
	private static final int GAMEPAD_LEFT_TRIGGER = Gamepad.LEFT_TRIGGER;
	private static final int GAMEPAD_RIGHT_TRIGGER = Gamepad.RIGHT_TRIGGER;
	private static final double ROBOT_WIDTH = RobotMap.ROBOT_WIDTH;
	
	
	/** Instance Variables ****************************************************/
	Log log = new Log(RobotMap.LOG_OI, "OI");
	public Gamepad gamepad, joyLeft, joyRight;
	int driveAxisLeft, driveAxisRight;


	/** OI ********************************************************************
	 * Assign commands to the buttons and triggers
	 * 
	 * Example Commands:
	 * gamepad.A.whenPressed(new ExampleCommand());
	 * gamepad.B.toggleWhenPressed(new ExampleCommand());
	 * gamepad.X.whileHeld(new ExampleCommand());
	 * gamepad.Y.whenReleased(new ExampleCommand());
	 * gamepad.LT.toggleWhenActive(new ExampleCommand());
	 * gamepad.RT.whenActive(new ExampleCommand());
	 * gamepad.POVUp.whileActive(new ExampleCommand());
	 */
	public OI() {
		log.add("OI Constructor", Log.Level.TRACE);
		
		gamepad = new Gamepad(USB_GAMEPAD);
		
		/** Setup Driving Controls ********************************************/
		if (USE_JOYSTICKS) {
			joyLeft = new Gamepad(USB_JOY_LEFT);
			joyRight = new Gamepad(USB_JOY_RIGHT);
			driveAxisLeft = JOYSTICK_Y_AXIS;
			driveAxisRight = JOYSTICK_Y_AXIS;
		}
		else {
			joyLeft = gamepad;
			joyRight = gamepad;
			driveAxisLeft = GAMEPAD_LEFT_Y_AXIS;
			driveAxisRight = GAMEPAD_RIGHT_Y_AXIS;
		}
		
		/** PBOT Controls *****************************************************/
		if (IS_GEORGE) {
			
			gamepad.A.whenPressed(new DrivetrainAuton_Drive((new CenterToRightSwitch()).buildPath()));
			
			double turnRadius = 1.5 * ROBOT_WIDTH;
			Path testPath = new Path();
			testPath.addStraight(36.0, 24);
			testPath.addRightTurn(45.0, 15.3042, 24);
			testPath.addStraight(57, 24);
			//testPath.addRightTurn(46.4, 15, 24);
			//testPath.addStraight(6, 24);
			
//			testPath.addStraight(36.0, 18.0);
//			testPath.addRightTurn(90.0, turnRadius, 21.0);
//			testPath.addLeftTurn(120, turnRadius, 21.0);
//			testPath.addLeftTurn(120, turnRadius, -21.0);
//			testPath.addRightTurn(90.0, turnRadius, -21.0);
//			testPath.addStraight(36.0, -18.0);
			//gamepad.B.whenPressed(new Left_LeftScale());
			
			gamepad.X.whenPressed(new DrivetrainAuton_Turn(Rotation2d.fromDegrees(90)));
			
			gamepad.Y.whenPressed(new Drivetrain_Calibrate());
		}
		/** Primary and Secondary Robot Controls ******************************************/
		if (IS_DABNEY || IS_BUSHNELL) {
			gamepad.POVUp.whenActive(new Elevator_CyclePositions(POVButton.UP));
			gamepad.POVDown.whenActive(new Elevator_CyclePositions(POVButton.DOWN));
			
			gamepad.Y.whenPressed(new Arm_CyclePositions(POVButton.UP));
			gamepad.A.whenPressed(new Arm_CyclePositions(POVButton.DOWN));
			gamepad.X.whenPressed(new Claw_Unclamp());
			gamepad.X.whenReleased(new Claw_Clamp());
			
			gamepad.RB.whileHeld(new Claw_Intake());
			gamepad.LB.whileHeld(new Claw_Release());
			gamepad.Back.whenPressed(new HookDeploy_Release());
			gamepad.Start.whenPressed(new POP_Unleash());
			gamepad.RT.whileActive(new Winch_Climb());
			gamepad.LT.whileActive(new Winch_Climb());
	
			
			Path drivetrainTesting = new Path();
			drivetrainTesting.addStraight(300, 90);
			gamepad.B.whenPressed(new DrivetrainAuton_Drive(drivetrainTesting));
			
			joyRight.button1.whenPressed(new Drivetrain_Shift());
			
			joyLeft.button1.whenPressed(new Drivetrain_Shift());
			
			joyLeft.button9.whileActive(new Winch_Reverse());
			joyRight.button7.whenPressed(new Claw_IntakeAuto());
			joyRight.button8.whenPressed(new DrivetrainAuton_TurnCalibrate(20, 3));
			joyRight.button9.whenPressed(new Right_RightScaleRightSwitch());
			joyRight.button3.whenPressed(new Arm_EmergencyMode());
			joyRight.button5.whenPressed(new Arm_CrisisAverted());
			joyLeft.button3.whenPressed(new Elevator_EmergencyMode());
			joyLeft.button5.whenPressed(new Elevator_CrisisAverted());
			//joyRight.button10.whenPressed(new DriveStraight());
		}
	}
	
	/** Access to the driving axes values *************************************
	 * A negative has been added to make pushing forward positive.
	 */
	public double getDriveLeft() {
		double joystickValue = joyLeft.getRawAxis(driveAxisLeft);
		joystickValue = scaleJoystick(joystickValue);
		return joystickValue;
	}
	public double getDriveRight() {
		double joystickValue = joyRight.getRawAxis(driveAxisRight);
		joystickValue = scaleJoystick(joystickValue);
		return joystickValue;
	}
	private double scaleJoystick(double joystickValue) {
		joystickValue = checkDeadZone(joystickValue);
		joystickValue *= JOYSTICK_DRIVE_SCALE;
		joystickValue *= -1.0;
		return joystickValue;
	}
	private double checkDeadZone(double joystickValue) {
		if (Math.abs(joystickValue) < JOYSTICK_DEAD_ZONE) joystickValue = 0.0;
		return joystickValue;
	}
	
	
	/** Access the POV value **************************************************/
	public int getPOV() {
		return gamepad.getPOV();
	}
	
	
	/** Access the Trigger Values *********************************************/
	public double getTriggerDifference() {
		double leftTrigger = gamepad.getRawAxis(GAMEPAD_LEFT_TRIGGER);
		double rightTrigger = gamepad.getRawAxis(GAMEPAD_RIGHT_TRIGGER);
		return (rightTrigger - leftTrigger) * TRIGGER_SPINNER_SCALE;
	}
	public double getLeftTrigger(){
		return gamepad.getRawAxis(GAMEPAD_LEFT_TRIGGER);
	}
	public double getRightTrigger(){
		return gamepad.getRawAxis(GAMEPAD_RIGHT_TRIGGER);
	}
}

