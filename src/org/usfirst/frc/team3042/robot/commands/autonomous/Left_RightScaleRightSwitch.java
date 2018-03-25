package org.usfirst.frc.team3042.robot.commands.autonomous;

import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Arm_SetPosition;
import org.usfirst.frc.team3042.robot.commands.Claw_IntakeAuto;
import org.usfirst.frc.team3042.robot.commands.Claw_ReleaseTimed;
import org.usfirst.frc.team3042.robot.commands.DrivetrainAuton_Drive;
import org.usfirst.frc.team3042.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Left_RightScaleRightSwitch extends CommandGroup {

    public Left_RightScaleRightSwitch() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new Left_RightScale());
    	addParallel(new Claw_IntakeAuto());
    	Path driveForward = new Path();
    	driveForward.addStraight(12, 12);
    	addSequential(new DrivetrainAuton_Drive(driveForward));
    	addSequential(new Arm_SetPosition(Arm.Position.MIDDLE));
    	addParallel(new Claw_ReleaseTimed(RobotMap.AUTO_CLAW_RELEASE_TIME), 6.0);
    	addSequential(new DrivetrainAuton_Drive(driveForward));

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
