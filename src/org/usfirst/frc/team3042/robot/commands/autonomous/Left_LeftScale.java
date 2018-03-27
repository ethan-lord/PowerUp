package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Arm_WaitForNearPosition;
import org.usfirst.frc.team3042.robot.commands.Auto_RunWhenDistanceLeft;
import org.usfirst.frc.team3042.robot.commands.Claw_IntakeAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseTimed;
import org.usfirst.frc.team3042.robot.commands.Claw_Stop;
import org.usfirst.frc.team3042.robot.commands.Claw_StopAuton;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Turn;
import org.usfirst.frc.team3042.robot.commands.Elevator_HoldPosition;
import org.usfirst.frc.team3042.robot.commands.Elevator_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Elevator_Stop;
import org.usfirst.frc.team3042.robot.paths.LeftToLeftScale;
import org.usfirst.frc.team3042.robot.paths.RightToLeftScale;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Left_LeftScale extends CommandGroup {

    public Left_LeftScale() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addParallel(new Elevator_HoldPosition());
    	addParallel(new Arm_SetPosition(Arm.Position.MIDDLE));
    	addParallel(new Auto_RunWhenDistanceLeft(new Elevator_SetPosition(Elevator.Position.MID_SCALE), 150.0));
    	addParallel(new Auto_RunWhenDistanceLeft(new Claw_ReleaseAuto(), 14.0));
    	addSequential(new DrivetrainAuton_Drive(new LeftToLeftScale().buildPath()));
    	Path backUp = new Path();
    	backUp.addStraight(-40, -60);
    	addParallel(new Auto_RunWhenDistanceLeft(new Elevator_SetPosition(Elevator.Position.INTAKE), 34));
    	addSequential(new DrivetrainAuton_Drive(backUp));
    	addParallel(new Arm_SetPosition(Arm.Position.BOTTOM));
    	addSequential(new DrivetrainAuton_Turn(Rotation2d.fromDegrees(-170)));
    	addSequential(new Elevator_Stop());
    	addParallel(new Claw_IntakeAuto());
    	Path driveToCube = new Path();
    	driveToCube.addStraight(13, 24);
    	addSequential(new DrivetrainAuton_Drive(driveToCube));
    	addSequential(new Arm_SetPosition(Arm.Position.MIDDLE));
    	addSequential(new Arm_WaitForNearPosition());

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
