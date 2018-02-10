package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class LeftToLeftScale implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(20,282,0,60));
        waypoints.add(new Waypoint(60,282,30,60));
        waypoints.add(new Waypoint(250,300,30,60));
        waypoints.add(new Waypoint(310,300,20,60));
        waypoints.add(new Waypoint(310,270,0,60));

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
	// WAYPOINT_DATA: [{"position":{"x":20,"y":282},"speed":60,"radius":0,"comment":""},{"position":{"x":60,"y":282},"speed":60,"radius":30,"comment":""},{"position":{"x":250,"y":300},"speed":60,"radius":30,"comment":""},{"position":{"x":310,"y":300},"speed":60,"radius":20,"comment":""},{"position":{"x":310,"y":270},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: LeftToLeftScale
}
