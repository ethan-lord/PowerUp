package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class LeftScaleToCube implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(310,270,0,60));
        waypoints.add(new Waypoint(270,260,20,60));
        waypoints.add(new Waypoint(230,242,0,60));

        return PathBuilder.buildPathFromWaypoints(waypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(310, 270), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":310,"y":270},"speed":60,"radius":0,"comment":""},{"position":{"x":270,"y":260},"speed":60,"radius":20,"comment":""},{"position":{"x":230,"y":242},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: LeftScaleToCube
}
