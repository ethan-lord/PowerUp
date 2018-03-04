package org.usfirst.frc.team3042.robot.paths;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.paths.PathBuilder.Waypoint;
import org.usfirst.frc.team3042.lib.Path;
import org.usfirst.frc.team3042.lib.math.RigidTransform2d;
import org.usfirst.frc.team3042.lib.math.Rotation2d;
import org.usfirst.frc.team3042.lib.math.Translation2d;

public class RightScaleToCube implements PathContainer {

	@Override
	public Path buildPath() {
		ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
		waypoints.add(new Waypoint(256, 75, 0, 0));
		waypoints.add(new Waypoint(226, 84, 0, 24));

		return PathBuilder.buildPathFromWaypoints(waypoints);
	}

	@Override
	public RigidTransform2d getStartPose() {
		return new RigidTransform2d(new Translation2d(256, 75), Rotation2d.fromDegrees(180.0));
	}

	@Override
	public boolean isReversed() {
		return false;
	}
	// WAYPOINT_DATA: [{"position":{"x":256,"y":75},"speed":0,"radius":0,"comment":""},{"position":{"x":226,"y":84},"speed":24,"radius":0,"comment":""}]
	// IS_REVERSED: false
	// FILE_NAME: RightScaleToCube
}
