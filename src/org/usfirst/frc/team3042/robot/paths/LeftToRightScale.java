package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class LeftToRightScale implements PathContainer {
    
	@Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(20,282,0,48));
        waypoints.add(new Waypoint(233,282,40,48));
        waypoints.add(new Waypoint(232,76,20,48));
        waypoints.add(new Waypoint(279,74,0,48));

        return PathBuilder.buildPathFromWaypoints(waypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(20, 282), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":20,"y":282},"speed":60,"radius":0,"comment":""},{"position":{"x":237,"y":282},"speed":60,"radius":40,"comment":""},{"position":{"x":237,"y":76},"speed":60,"radius":20,"comment":""},{"position":{"x":280,"y":76},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: LeftToRightScale
}
