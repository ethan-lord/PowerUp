package org.usfirst.frc.team3042.lib;

import org.usfirst.frc.team3042.robot.RobotMap;

import com.ctre.phoenix.motion.TrajectoryPoint;

public class MotionProfileTest {

	static FileIO file = new FileIO();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		file.create("C:\\Users\\Robotics\\Desktop\\", "Profile.txt");

		Path testPath = new Path();
		testPath.addStraight(5.0, 60);
		testPath.addLeftTurn(46.4, 15, 60);
		testPath.addStraight(57, 24);
		testPath.addRightTurn(46.4, 15, 24);
		testPath.addStraight(6, 24);

		MotionProfile leftProfile = testPath.generateLeftPath();
		MotionProfile rightProfile = testPath.generateRightPath();

		int profileLength = Math.min(leftProfile.getLength(), rightProfile.getLength());
		for (int i = 0; i < profileLength; i++) {
			int time = RobotMap.AUTON_DT_MS * i;

			TrajectoryPoint leftPoint = leftProfile.getPoint(i);
			TrajectoryPoint rightPoint = rightProfile.getPoint(i);
			file.write(time + ", " + leftPoint.position + ", " + leftPoint.velocity + ", " 
								   + rightPoint.position + ", " + rightPoint.velocity);	
		}
		
		file.close();
	}

}
