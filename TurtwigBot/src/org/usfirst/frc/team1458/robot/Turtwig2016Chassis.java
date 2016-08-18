package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;

public class Turtwig2016Chassis implements TurtleComponent {

	private final TurtleMotor lDrive1 = new TurtleVictor888(
			TurtwigConstants.LEFT1VICTORPORT);
	private final TurtleMotor lDrive2 = new TurtleVictor888(
			TurtwigConstants.LEFT2VICTORPORT);
	private final TurtleMotor rDrive1 = new TurtleVictor888(
			TurtwigConstants.RIGHT1VICTORPORT);
	private final TurtleMotor rDrive2 = new TurtleVictor888(
			TurtwigConstants.RIGHT2VICTORPORT);
	private TurtleAnalogInput rightJoystickPower;
	private TurtleAnalogInput leftJoystickPower;

	public void setRightJoystick(TurtleAnalogInput a) {
		rightJoystickPower = a;
	}

	public void setLeftJoystick(TurtleAnalogInput a) {
		leftJoystickPower = a;
	}

	@Override
	public void teleUpdate() {
		MotorValue leftPower = new MotorValue(leftJoystickPower.get());
		MotorValue rightPower = new MotorValue(rightJoystickPower.get());
		lDrive1.set(leftPower);
		lDrive2.set(leftPower);
		rDrive1.set(rightPower);
		rDrive2.set(rightPower);

	}

	@Override
	public void autoUpdate() {
		// TODO Auto-generated method stub

	}

}
