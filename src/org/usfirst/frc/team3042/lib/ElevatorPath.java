package org.usfirst.frc.team3042.lib;

import java.util.ArrayList;

import org.usfirst.frc.team3042.robot.RobotMap;

public class ElevatorPath {
	/** Configurations Constants **********************************************/
	private static final Log.Level LOG_LEVEL = RobotMap.LOG_ELEVATOR;
	private static final double CIRCUMFERENCE = RobotMap.Elevator_SPROCKET_CIRCUMFERENCE;

	
	/** Instance Variables ****************************************************/
	Log log = new Log(LOG_LEVEL, "Elevator Path");
	ArrayList<Double> distances = new ArrayList<Double>();
	ArrayList<Double> speeds = new ArrayList<Double>();
	

	/** addStraight ***********************************************************
	 * distance: physical unit matching that of the wheel diameter constant
	 * speed: physical unit of distance per second.
	 * 
	 * Converted to revolutions and rps for the motion profile
	 * Direction is determined by the sign of speed.
	 */
	public void addStraight(double distance, double speed) {
		log.add("Adding Straight with distance: " + distance + ", speed: " + speed, LOG_LEVEL.DEBUG);
		distance = convertDistance(distance);
		speed = convertSpeed(speed);
		
		distances.add(distance);
		speeds.add(speed);
	}
	/**
	 * 
	 * @param distance
	 * @return revolutions
	 */
	private double convertDistance(double distance) {
		distance = Math.abs(distance);
		return distance / CIRCUMFERENCE;
	}
	/**
	 * 
	 * @param speed
	 * @return revolutions per second
	 */
	private double convertSpeed(double speed) {
		return speed / CIRCUMFERENCE;
	}
	
	
	/** Generate Motion Profile Paths *****************************************/
	public MotionProfile generatePath() {
		return new MotionProfile(convert(distances), convert(speeds));
	}
	private double[] convert(ArrayList<Double> input) {
		int length = input.size();
		double[] output = new double[length];
		for (int n=0; n<length; n++) {
			output[n] = input.get(n);
		}
		return output;
	}
}
