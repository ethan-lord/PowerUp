package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Auto_RunWhenDistanceLeft;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseTimed;
import org.usfirst.frc.team3042.robot.commands.Claw_Stop;
import org.usfirst.frc.team3042.robot.commands.Claw_StopAuton;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Turn;
import org.usfirst.frc.team3042.robot.commands.Elevator_HoldPosition;
import org.usfirst.frc.team3042.robot.commands.Elevator_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Elevator_Stop;
import org.usfirst.frc.team3042.robot.paths.RightToLeftScale;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Right_LeftScale extends CommandGroup {

    public Right_LeftScale() {
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
    	addSequential(new DrivetrainAuton_Drive(new RightToLeftScale().buildPath()));
    
    	addSequential(new Claw_ReleaseTimed(RobotMap.AUTO_CLAW_RELEASE_TIME));
    	Path backUp = new Path();
    	backUp.addStraight(-40, -48);
    	addSequential(new DrivetrainAuton_Drive(backUp));
    	addParallel(new Elevator_SetPosition(Elevator.Position.INTAKE));
    	addParallel(new DrivetrainAuton_Turn(Rotation2d.fromDegrees(-165)));
    	addSequential(new Arm_SetPosition(Arm.Position.BOTTOM));
    	addSequential(new Elevator_Stop());

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
