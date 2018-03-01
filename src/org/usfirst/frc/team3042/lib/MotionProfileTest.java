package org.usfirst.frc.team3042.lib;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.paths.LeftToLeftScale;

import com.ctre.phoenix.motion.TrajectoryPoint;

public class MotionProfileTest {

	static FileIO file = new FileIO();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		file.create("C:\\Users\\Robotics\\Desktop\\", "Profile.txt");

		Path testPath = new LeftToLeftScale().buildPath();

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
