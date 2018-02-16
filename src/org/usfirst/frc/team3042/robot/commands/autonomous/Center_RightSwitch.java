package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.robot.commands.Arm_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Claw_Unclamp;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.commands.Elevator_SetPosition;
import org.usfirst.frc.team3042.robot.paths.CenterToLeftSwitch;
import org.usfirst.frc.team3042.robot.paths.CenterToRightSwitch;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center_RightSwitch extends CommandGroup {

    public Center_RightSwitch() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

    	addParallel(new Elevator_SetPosition(Elevator.Position.SWITCH));
    	addParallel(new Arm_SetPosition(Arm.Position.MIDDLE));
    	addSequential(new DrivetrainAuton_Drive(new CenterToRightSwitch().buildPath()));
    	addSequential(new Claw_Unclamp());
    	
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
