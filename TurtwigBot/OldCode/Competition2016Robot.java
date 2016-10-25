package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleFlightStick.FlightAxis;
import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.TestMode;

public class Competition2016Robot extends SampleRobotObjectHolder {

	public Competition2016Robot() {
		TurtleFlightStick left = new TurtleFlightStick(
				TurtwigConstants.LJOYSTICKPORT);
		TurtleFlightStick right = new TurtleFlightStick(
				TurtwigConstants.RJOYSTICKPORT);

		Turtwig2016Chassis chassis = new Turtwig2016Chassis();
		chassis.setLeftJoystick(left.getAxis(FlightAxis.PITCH)); 
		chassis.setRightJoystick(right.getAxis(FlightAxis.PITCH));
		
		components.add(chassis);

	}

	@Override
	public TestMode getTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutoMode getAuto() {
		// TODO Auto-generated method stub
		return null;
	}
}
