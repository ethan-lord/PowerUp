package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class TestThing implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(25,160,0,0));
        waypoints.add(new Waypoint(80,160,15,30));
        waypoints.add(new Waypoint(80,210,0,30));

        return PathBuilder.buildPathFromWaypoints(waypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(20, 160), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":20,"y":160},"speed":0,"radius":0,"comment":""},{"position":{"x":80,"y":160},"speed":36,"radius":15,"comment":""},{"position":{"x":80,"y":205},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: TestThing
}