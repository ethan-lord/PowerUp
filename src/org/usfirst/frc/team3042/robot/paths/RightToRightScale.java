package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class RightToRightScale implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(20,40,0,0));
        waypoints.add(new Waypoint(180,40,20,100));
        waypoints.add(new Waypoint(250,80,20,100));
        waypoints.add(new Waypoint(290,80,0,100));

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
	// WAYPOINT_DATA: [{"position":{"x":20,"y":40},"speed":0,"radius":0,"comment":""},{"position":{"x":180,"y":40},"speed":60,"radius":15,"comment":""},{"position":{"x":250,"y":75},"speed":60,"radius":15,"comment":""},{"position":{"x":280,"y":75},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: RightToRightScale
}