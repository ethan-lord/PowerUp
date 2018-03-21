package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Claw_Release;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseTimed;
import org.usfirst.frc.team3042.robot.commands.Claw_StopAuton;
import org.usfirst.frc.team3042.robot.commands.Claw_Unclamp;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.commands.Drivetrain_ShiftHigh;
import org.usfirst.frc.team3042.robot.commands.Elevator_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Wait;
import org.usfirst.frc.team3042.robot.paths.CenterToLeftSwitch;
import org.usfirst.frc.team3042.robot.subsystems.Arm;
import org.usfirst.frc.team3042.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Center to Left Switch
 */
public class Center_LeftSwitch extends CommandGroup {

    public Center_LeftSwitch() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

    	
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	//addParallel(new Elevator_SetPosition(Elevator.Position.BOTTOM));
    	addSequential(new Drivetrain_ShiftHigh());
    	addParallel(new Arm_SetPosition(Arm.Position.MIDDLE));
    	addSequential(new DrivetrainAuton_Drive(new CenterToLeftSwitch().buildPath()));
    	addSequential(new Claw_ReleaseTimed(RobotMap.AUTO_CLAW_RELEASE_TIME));
    	addSequential(new Claw_StopAuton());

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
