package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.TestMode;

public class Competition2016Robot extends SampleRobotObjectHolder {

	public Competition2016Robot() {
		TurtleXboxController xbox = new TurtleXboxController(TurtwigConstants.XBOXCONTROLLERPORT);
	/*
		TurtleFlightStick left = new TurtleFlightStick(
				TurtwigConstants.LJOYSTICKPORT);
		TurtleFlightStick right = new TurtleFlightStick(
				TurtwigConstants.RJOYSTICKPORT);*/

		Turtwig2016Chassis chassis = new Turtwig2016Chassis();
		chassis.setLeftJoystick(xbox.getAxis(TurtleXboxController.XboxAxis.LY));
		chassis.setRightJoystick(xbox.getAxis(TurtleXboxController.XboxAxis.RY));
		//chassis.setLeftJoystick(left.getAxis(FlightAxis.PITCH));
		//chassis.setRightJoystick(right.getAxis(FlightAxis.PITCH));
		
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
