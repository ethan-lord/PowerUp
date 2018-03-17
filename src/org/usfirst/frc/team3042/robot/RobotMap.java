package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.lib.Log;

import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/** Robot selector ********************************************************/
	public static enum Bot {PBOT, PRIMARY, SECONDARY};
	// Set the bot to which you intend to push code.
	private static Bot currentBot = Bot.SECONDARY;

	public static final boolean IS_PBOT 	= (currentBot == Bot.PBOT);
	public static final boolean IS_PRIMARY = (currentBot == Bot.PRIMARY);
	public static final boolean IS_SECONDARY = (currentBot == Bot.SECONDARY);
	
	public static final boolean DEMO_MODE = false;
	
	
	/** Robot Size Parameters *************************************************
	 * The units of the wheel diameter determine the units of the position 
	 * and speed closed-loop commands. For example, if the diameter is given 
	 * in inches, position will be in inches and speed in inches per second.
	 */
	public static final double WHEEL_DIAMETER = 4.0;
	public static final double ROBOT_WIDTH = (IS_PBOT) ? 15.0 : (IS_PRIMARY) ? 29.53042 : 29.53042;
	public static final double ROBOT_WIDTH_REAL = (IS_PBOT) ? 15.0 : (IS_PRIMARY) ? 23.795 : 23.795;
	
	
	/** USB ports *************************************************************/					
	public static final int USB_JOYSTICK_LEFT 	= 0;
	public static final int USB_JOYSTICK_RIGHT 	= 1;
	public static final int USB_GAMEPAD 		= IS_PBOT ? 0 : 2;


	/** PWM ports *************************************************************/
	
	/** CAN ID numbers ********************************************************/
	public static final int CAN_LEFT_MOTOR 	= 		IS_PBOT 	? 3 :
													IS_PRIMARY 	? 29 : 14;
	public static final int CAN_RIGHT_MOTOR = 		IS_PBOT 	? 9 :
													IS_PRIMARY 	? 32 : 25;
	public static final int CAN_LEFT_FOLLOWER = 		IS_PRIMARY 	? 28 : 8;
	public static final int CAN_RIGHT_FOLLOWER = 	IS_PRIMARY 	? 5 : 7;
	public static final int CAN_WINCH_MOTOR_MASTER = 	IS_PRIMARY  ? 34 : 19;
	public static final int CAN_WINCH_MOTOR_FOLLOWER = 	IS_PRIMARY  ? 27 : 18;
	
	public static final int CAN_CLAW_RIGHT_TALON =  	IS_PRIMARY  ? 30 : 1;
	public static final int CAN_CLAW_LEFT_TALON =   	IS_PRIMARY  ? 26 : 12;
	
	public static final int CAN_ELEVATOR_TALON = 	IS_PRIMARY ? 13 : 17;
	
	public static final int CAN_ARM_MOTOR =			IS_PRIMARY ? 33 : 2;

	public static final int CAN_HOOK_MOTOR =			IS_PRIMARY ? 31 : 15;
	
	/** DIO channels **********************************************************/
	public static final int DIO_CLAW_ULTRA_PING = 1;
	public static final int DIO_CLAW_ULTRA_ECHO = 0;
	
	/** PCM channels **********************************************************/
	public static final int CLAMP_SOLENOID = (IS_PRIMARY)? 0 : 0;
	public static final int SHIFT_SOLENOID = (IS_PRIMARY)? 1 : 1;
	public static final int HOOK_DEPLOYMENT_SOLENOID  = (IS_PRIMARY)? 3 : 2;
	public static final int POP_SOLENOID   = (IS_PRIMARY)? 2 : 3;
	
	
	/** OI Settings ***********************************************************/
	public static final boolean USE_JOYSTICKS = !IS_PBOT;
	public static final double JOYSTICK_DRIVE_SCALE = (IS_PBOT || DEMO_MODE)? 0.6 : 1.0;
	public static final double TRIGGER_SPINNER_SCALE = 0.1;
	public static final double JOYSTICK_DEAD_ZONE = 0.15;


	/** Drivetrain Settings ***************************************************/
	public static final boolean HAS_DRIVETRAIN = true;
	public static final boolean HAS_FOLLOWERS = !IS_PBOT;
	public static final NeutralMode DRIVETRAIN_BRAKE_MODE = NeutralMode.Brake;
	public static final boolean REVERSE_LEFT_MOTOR = 	(IS_PBOT) ? true : false;
	public static final boolean REVERSE_RIGHT_MOTOR = 	(IS_PBOT) ? false: true;
	// Maximum Acceleration given in power per second
	public static final double ACCELERATION_MAX = 50;
	public static final double kF_DRIVE_LEFT = 	(IS_PBOT) 		? 1.16666666666666666666666666667 ://0.7130907570054371 :
												(IS_PRIMARY) 	? 1.189 : 1.21;
	public static final double kF_DRIVE_RIGHT = (IS_PBOT) 		? 1.12 ://0.6492764661081493 :
												(IS_PRIMARY) 	? 1.278 : 1.24;
	public static final int TALON_ERROR_TIMEOUT = 0;// measured in Ms
	public static final int TRAJPERIOD = 10;
	public static final int BASE_TRAJPERIOD = 0;
	public static final int PIDIDX = 0; //pidIdx - 0 for Primary closed-loop. 1 for cascaded closed-loop. See Phoenix-Documentation for how to interpret.
	public static final int SLOTIDX_1 = 0;
	public static final boolean STARTS_HIGH_GEAR = (IS_PRIMARY) ? false : false;
	public static final int DRIVETRAIN_MOTION_MAGIC_ACCELERATION = IS_PRIMARY ? 0 : 493;
	public static final int DRIVETRAIN_MOTION_MAGIC_CRUISE_VELOCITY = IS_PRIMARY ? 0 : 150;	
	
	
	/** Winch Settings ********************************************************/
	public static final boolean HAS_WINCH = !IS_PBOT;
	
	public static final double WINCH_HAS_LOAD_THRESHHOLD = 0;//test to determine what this should be.
	
	public static final double WINCH_BASE_POWER = IS_PRIMARY ? 1.0 : -1.0;// percent output
	
	/** Claw Settings *********************************************************/
	public static final boolean HAS_CLAW =!IS_PBOT;
	public static final double CLAW_INTAKE_POWER = IS_PRIMARY 	? 0.8 : 0.8;
	public static final double CLAW_RELEASE_POWER_DEFAULT = IS_PRIMARY 	? -0.5 : -0.375;
	public static final double CLAW_RELEASE_POWER_EXCHANGE = IS_PRIMARY   ? -0.53042 : -0.53042;
	public static final double CLAW_GRAB_DISTANCE = 4;
	public static final boolean STARTS_ACTIVE = false;
	public static final double CLAW_INTAKE_TIMEOUT = IS_PRIMARY ? 0 : 0;
	public static final boolean CLAW_RIGHT_REVERSE = (IS_PRIMARY) ? false : true;
	public static final boolean CLAW_LEFT_REVERSE = true;
	
	/** Elevator Settings *****************************************************/
	public static final boolean HAS_ELEVATOR = !IS_PBOT;
	public static final int ELEVATOR_INTAKE_POSITION = IS_PRIMARY 		? 200 : 200;
	public static final int ELEVATOR_LOW_SCALE_POSITION = IS_PRIMARY 	? 25000 : 25000;
	public static final int ELEVATOR_MID_SCALE_POSITION = IS_PRIMARY    ? 31000 : 31000;
	public static final int ELEVATOR_HIGH_SCALE_POSITION = IS_PRIMARY 	? 36000 : 36000;
	public static final int ELEVATOR_MAX_POSITION = IS_PRIMARY          ? 37000 : 37000;
	public static final int ELEVATOR_MIN_POSITION = IS_PRIMARY          ? 0 : 0;
	public static final double ELEVATOR_MAX_SPEED = IS_PRIMARY			? 1000 : 1000;
	public static final int ELEVATOR_MANUAL_SPEED = IS_PRIMARY 	? 5 : 5;
	public static final double ELEVATOR_KP = IS_PRIMARY 	? 0.5 : 0.5;
	public static final double ELEVATOR_KI = IS_PRIMARY 	? 0 : 0;
	public static final double ELEVATOR_KD = IS_PRIMARY 	? 15.0 : 15.0;
	public static final double ELEVATOR_KF = IS_PRIMARY 	? 0.5 : 0.5;
	public static final int ELEVATOR_I_ZONE = IS_PRIMARY 	? 0 : 0;
	public static final int ELEVATOR_MOTION_MAGIC_ACCELERATION = IS_PRIMARY ? 3500 : 1500;
	public static final int ELEVATOR_MOTION_MAGIC_CRUISE_VELOCITY = IS_PRIMARY ? 4000 : 2500;
	public static final double ELEVATOR_LOWER_VELOCITY = IS_PRIMARY ? 0.041 : -0.1;
	public static final double ELEVATOR_POSITION_CONTROL_RANGE = IS_PRIMARY ? 300 : 300;
	public static final int ELEVATOR_SPROCKET_CIRCUMFERENCE = IS_PRIMARY ? 4 : 4;
	public static final boolean ELEVATOR_REVERSE = true;
	
	/** Arm Settings **********************************************************/
	public static final boolean HAS_ARM = !IS_PBOT;
	public static final double ARM_KP = IS_PRIMARY 	? 4.2 : 6.0;
	public static final double ARM_KI = IS_PRIMARY 	? 0.015 : 0.015;
	public static final double ARM_KD = IS_PRIMARY 	? 80.0 : 50.0;
	public static final double ARM_KF = IS_PRIMARY 	? 0 : 0;
	public static final int ARM_I_ZONE = IS_PRIMARY 	? 30 : 30;
	public static final boolean ARM_REVERSE_SENSOR_PHASE = (IS_PRIMARY) ? false : true;
	public static final int ARM_MANUAL_SPEED = IS_PRIMARY	? 0 : 1;
	public static final int ARM_BOTTOM_POS = IS_PRIMARY	? 480 : -180;
	public static final int ARM_MIDDLE_POS = IS_PRIMARY	? 770 : -15;
	public static final int ARM_TOP_POS = IS_PRIMARY	? 810 : 0;
	public static final int ARM_FRAME_PERIMITER = IS_PRIMARY ? 810 : 0;
	public static final int ARM_MOTION_MAGIC_ACCELERATION = IS_PRIMARY ? 200 : 100;
	public static final int ARM_MOTION_MAGIC_CRUISE_VELOCITY = IS_PRIMARY ? 170 : 100;
	public static final int ARM_MAX_POSITION = IS_PRIMARY    ? 810 : 0;
	public static final int ARM_MIN_POSITION = IS_PRIMARY    ? 480 : -190;
	public static final int ARM_POSITION_CONTROL_RANGE = IS_PRIMARY	? 30 : 20;
	
	/** HookDeploy Settings **********************************************/
	public static final boolean HAS_HOOKDEPLOY = !IS_PBOT;
	public static final double TIME_TO_REENGAGE_HOOK_DEPLOYMENT = 1.0;
	
	
	/** P.O.P. Settings *******************************************************/
	public static final boolean HAS_POP = !IS_PBOT;
	public static final double TIME_TO_REENGAGE_POPS = 0.5;
	

	
	/** Compressor Settings ***************************************************/
	public static final int COMPRESSOR_PORT = 0;
	public static final boolean HAS_COMPRESSOR = true;//!IS_PBOT;
	
	
	/** Drivetrain Encoder Settings *******************************************/
	public static final boolean HAS_ENCODERS = true;
	//Encoder counts per revolution
	//In quadrature mode, actual counts will be 4x this; e.g., 360 -> 1440
	public static final int COUNTS_PER_REVOLUTION = 1440;
	//How often the encoders update on the CAN, in milliseconds
	public static final int ENCODER_FRAME_RATE = 10;
	public static final boolean SENSOR_PHASE_LEFT = 	(IS_PRIMARY) ? true: false;
	public static final boolean SENSOR_PHASE_RIGHT = 	(IS_PRIMARY) ? false: false;
	
	
	/** Drivetrain Autonomous Settings ****************************************/
	public static final boolean HAS_AUTON = HAS_ENCODERS;
	public static final int AUTON_PROFILE = 0;
	public static final double kP_AUTON = 		(IS_PBOT) 		? 1.0 :
												(IS_PRIMARY) 	? 1.3 : 13.0;
	public static final double kI_AUTON = 		(IS_PBOT) 		? 0.0 :
												(IS_PRIMARY) 	? 0.00 : 0.05;
	public static final double kD_AUTON = 		(IS_PBOT) 		? 2.0 :
												(IS_PRIMARY) 	? 4.5 : 45.0;
	public static final int I_ZONE_AUTON =		(IS_PBOT)		? 0 :
												(IS_PRIMARY)	? 0 : 0;
	public static final double DRIVETRAIN_ALLOWABLE_TURN_ERROR_IN_THE_Z_AXIS_IN_DRIVETRAIN_AUTONOMOUS_IN_DEGREES = 1;
	public static final double DRIVETRAIN_ALLOWABLE_TURN_ERROR_IN_THE_Z_AXIS_IN_DRIVETRAIN_AUTONOMOUS_IN_RADIANS = DRIVETRAIN_ALLOWABLE_TURN_ERROR_IN_THE_Z_AXIS_IN_DRIVETRAIN_AUTONOMOUS_IN_DEGREES * Math.PI / 180.0;
	//The rate of pushing motion profile points to the talon, in ms
	public static final int AUTON_FRAME_RATE = 10;
	//Parameters for calibrating the F-gain
	public static final double AUTON_CALIBRATE_POWER = 1.0;
	public static final double AUTON_CALIBRATE_TIME = 5.0; //seconds
	public static final int AUTON_COUNT_AVERAGE = 20;
	//Parameters for motion profile driving
	public static final int AUTON_DT_MS = 30;
	public static final double AUTON_DT_SEC = (double)AUTON_DT_MS / 1000.0;
	public static final double AUTON_ACCEL_TIME = 1.0; //time in sec
	public static final double AUTON_SMOOTH_TIME = 0.1; //time in sec
	public static final double AUTON_MAX_ACCEL = 4.8; //rev per sec per sec. Used to test whether profiles should work
	public static final int AUTON_BUFFER_TRIGGER = 10;
	public static final int AUTON_TIMEOUT = 0; // timeout in ms; set to zero
	public static final int AUTON_PIDIDX = 0; // used for cascading PID; set to zero
	public static final int AUTON_HEADING = 0; //unimplemented feature; set to zero
	
	public static final double MIN_LOOK_AHEAD = 12.0; // inches
    public static final double MIN_LOOK_AHEAD_SPEED = 9.0; // inches per second
    public static final double MAX_LOOK_AHEAD = 24.0; // inches
    public static final double MAX_LOOK_AHEAD_SPEED = 120.0; // inches per second
	
	
	/** Drivetrain Gyro Drive Settings ****************************************/
	public static final double kP_GYRO = IS_PBOT 	? 0.01 :
										 IS_PRIMARY ? 0    : 1.0;
	public static final double kI_GYRO = IS_PBOT 	? 0    :
										 IS_PRIMARY ? 0    : 0;
	public static final double kD_GYRO = IS_PBOT 	? 0.05 :
										 IS_PRIMARY ? 0    : 10;
	public static final int I_ZONE_GYRO = IS_PBOT   ? 0    :
		 								 IS_PRIMARY ? 0    : 10;
	public static final double ANGLE_TOLERANCE = 1.0;
	public static final double MAX_SPEED_GYRO = 1.0;
	
	
	/** Gyroscope Settings ****************************************************/
	public static final boolean HAS_GYROSCOPE = true;
	public static final double GYROSCOPE_SCALE = 0.25;
	
	/** Autonomous Constants ****************************************************/
	public static final double AUTO_CLAW_RELEASE_TIME = 1.0;
	
	
	/** Logger Settings *******************************************************/
	public static final String 		LOG_FILE_FORMAT = "yyyy-MM-dd-hhmmss";
	public static final String 		LOG_TIME_FORMAT = "hh:mm:ss:SSS";
	public static final String 		LOG_DIRECTORY_PATH = "/home/lvuser/logs/";
	public static final String 		LOG_TIME_ZONE = "America/Chicago";
	public static final boolean 		LOG_TO_CONSOLE 				= true;
	public static final boolean 		LOG_TO_FILE 					= true;
	public static final Log.Level LOG_GLOBAL 					= Log.Level.TRACE;
	public static final Log.Level LOG_ROBOT 					= Log.Level.DEBUG;
	public static final Log.Level	LOG_OI 						= Log.Level.TRACE;
	public static final Log.Level	LOG_AXIS_TRIGGER 			= Log.Level.ERROR;
	public static final Log.Level	LOG_POV_BUTTON				= Log.Level.ERROR;
	/** Subsystems **/                                                   
	public static final Log.Level	LOG_DRIVETRAIN				= Log.Level.TRACE;
	public static final Log.Level	LOG_DRIVETRAIN_FOLLOWERS		= Log.Level.TRACE;
	public static final Log.Level	LOG_DRIVETRAIN_ENCODERS 		= Log.Level.DEBUG;
	public static final Log.Level	LOG_DRIVETRAIN_AUTON			= Log.Level.DEBUG_PERIODIC;
	public static final Log.Level	LOG_MOTION_PROFILE				= Log.Level.DEBUG;
	public static final Log.Level	LOG_GYROSCOPE				= Log.Level.DEBUG;
	public static final Log.Level	LOG_WINCH					= Log.Level.TRACE;
	public static final Log.Level	LOG_CLAW						= Log.Level.TRACE;
	public static final Log.Level	LOG_ELEVATOR					= Log.Level.DEBUG;
	public static final Log.Level 	LOG_ARM						= Log.Level.DEBUG;
	public static final Log.Level	LOG_HOOK						= Log.Level.TRACE;
	public static final Log.Level 	LOG_COMPRESSOR              = Log.Level.TRACE;
	public static final Log.Level   LOG_POP                     = Log.Level.TRACE;

}
