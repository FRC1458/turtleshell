package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;

public class Turtwig2016DumbArm {
	// Xbox axis, when thing pulled, arm moves?
	private final TurtleMotor armRight = new TurtleVictor888(
			TurtwigConstants.RIGHTINTAKEVICTORPORT);
	private final TurtleMotor armLeft = new TurtleVictor888(
			TurtwigConstants.LEFTINTAKEVICTORPORT);
	private final TurtleMotor armIntake = new TurtleVictor888(
			TurtwigConstants.SPININTAKEVICTORSPPORT);
	private TurtleAnalogInput armPower;
	private TurtleDigitalInput spinForward;
	private TurtleDigitalInput spinBackward;

	public void setXBoxJoystick(TurtleAnalogInput a) {
		armPower = a;
	}

	public void setLeftXBoxButton(TurtleDigitalInput d) {
		spinForward = d;
	}

	public void setRightXBoxButton(TurtleDigitalInput d) {
		spinBackward = d;
	}

	public void teleUpdate() {
		MotorValue armPowerness = new MotorValue(armPower.get());
		armRight.set(armPowerness);
		armLeft.set(armPowerness);
		if (spinForward.get() == 1) {
			armIntake.set(MotorValue.fullForward);
		} else if (spinBackward.get() == 1) {
			armIntake.set(MotorValue.fullBackward);
		} else {
			armIntake.set(MotorValue.zero);
		}
	}

	public void autoUpdate() {

	}
}
