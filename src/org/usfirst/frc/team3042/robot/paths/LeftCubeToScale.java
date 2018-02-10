package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class LeftCubeToScale implements PathContainer {
    
    @Override
    public Path buildPath() {
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        waypoints.add(new Waypoint(230,242,0,60));
        waypoints.add(new Waypoint(260,247,15,60));
        waypoints.add(new Waypoint(282,247,0,60));

        return PathBuilder.buildPathFromWaypoints(waypoints);
    }
    
    @Override
    public RigidTransform2d getStartPose() {
        return new RigidTransform2d(new Translation2d(230, 242), Rotation2d.fromDegrees(180.0)); 
    }

    @Override
    public boolean isReversed() {
        return false; 
    }
	// WAYPOINT_DATA: [{"position":{"x":230,"y":242},"speed":60,"radius":0,"comment":""},{"position":{"x":260,"y":247},"speed":60,"radius":15,"comment":""},{"position":{"x":282,"y":247},"speed":60,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: LeftCubeToScale
}
