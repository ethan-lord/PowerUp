package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class CenterToLeftSwitch implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(20,157,0,60));
        waypoints.add(new Waypoint(53,157,15,60));
        waypoints.add(new Waypoint(80,234,15,60));
        waypoints.add(new Waypoint(99,234,0,60));

        return PathBuilder.buildPathFromWaypoints(waypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(20, 157), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":20,"y":157},"speed":60,"radius":0,"comment":""},{"position":{"x":40,"y":157},"speed":60,"radius":15,"comment":""},{"position":{"x":100,"y":220},"speed":60,"radius":15,"comment":""},{"position":{"x":121,"y":220},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: CenterToLeftSwitch
}
