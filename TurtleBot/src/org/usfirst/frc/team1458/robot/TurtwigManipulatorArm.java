package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtlePID;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigManipulatorArm implements TurtleRobotComponent {
	private TurtleMotor shoulder = new TurtleVictor(TurtwigConstants.SHOULDERVICTORPORT);
	private TurtleMotor elbow = new TurtleVictor(TurtwigConstants.ELBOWVICTORPORT);
	private TurtleEncoder shoulderEncoder = new Turtle4PinEncoder(TurtwigConstants.SHOULDERENCODERPORT1,
			TurtwigConstants.SHOULDERENCODERPORT2);
	private TurtleEncoder elbowEncoder = new Turtle4PinEncoder(TurtwigConstants.ELBOWENCODERPORT1,
			TurtwigConstants.ELBOWENCODERPORT2);
	private TurtlePID shoulderPID;
	private TurtlePID elbowPID;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void teleUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
	
	public void setTarget(double x, double y) {
		/*shoulderPID = new TurtlePDD2(TurtwigConstants.shoulderConstants,
				Math.toDegrees(Math.atan2(y, x) + Math.acos(()/())), 0);*/
	}

}
