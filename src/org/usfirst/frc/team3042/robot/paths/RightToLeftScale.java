package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class RightToLeftScale implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(20,40,0,0));
        waypoints.add(new Waypoint(160,40,20,60));
        waypoints.add(new Waypoint(240,70,20,60));
        waypoints.add(new Waypoint(240,250,20,60));
        waypoints.add(new Waypoint(280,250,0,60));

        return PathBuilder.buildPathFromWaypoints(waypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(20, 40), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":20,"y":40},"speed":0,"radius":0,"comment":""},{"position":{"x":160,"y":40},"speed":60,"radius":15,"comment":""},{"position":{"x":250,"y":75},"speed":60,"radius":15,"comment":""},{"position":{"x":280,"y":75},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: RightToRightScale
}