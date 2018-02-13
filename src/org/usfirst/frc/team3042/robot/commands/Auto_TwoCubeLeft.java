package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.robot.paths.LeftCubeToScale;
import org.usfirst.frc.team3042.robot.paths.LeftScaleToCube;
import org.usfirst.frc.team3042.robot.paths.LeftToLeftScale;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_TwoCubeLeft extends CommandGroup {

    public Auto_TwoCubeLeft() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    		//CUBE 1 PLACEMENT
    		addParallel(new Arm_SetPosition(Arm.Position.MIDDLE));
    		addParallel(new Elevator_SetPosition(Elevator.Position.HIGH_SCALE));
    		addSequential(new DrivetrainAuton_Drive((new LeftToLeftScale()).buildPath()));
    		addSequential(new Claw_Unclamp());
    		
    		//CUBE 2 INTAKE
    		addParallel(new Elevator_SetPosition(Elevator.Position.INTAKE));
    		addSequential(new DrivetrainAuton_Turn(Rotation2d.fromDegrees(160)));
    		addParallel(new Claw_Intake());
    		addSequential(new DrivetrainAuton_Drive((new LeftScaleToCube()).buildPath()));
    		
    		//CUBE 2 PLACEMENT
    		addParallel(new Elevator_SetPosition(Elevator.Position.HIGH_SCALE));
    		addSequential(new DrivetrainAuton_Turn(Rotation2d.fromDegrees(180)));
    		addSequential(new DrivetrainAuton_Drive((new LeftCubeToScale()).buildPath()));
    		addSequential(new Claw_Unclamp());
    		
    }
}
