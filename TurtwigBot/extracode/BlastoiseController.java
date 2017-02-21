package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.TurtleAnalogInput;
import com.team1458.turtleshell2.input.TurtleFlightStick;
import com.team1458.turtleshell2.input.TurtleXboxController;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * High-Level controller
 *
 * Manages Autonomous/Teleop modes and Control Systems
 *
 * @author asinghani
 */
public class BlastoiseController {
	private TurtleAnalogInput rightJoystick;
	private TurtleAnalogInput leftJoystick;

	private BlastoiseChassis chassis;

	private TurtleLogger logger;

	/**
	 * Instantiate BlastoiseController with an Xbox controller (Using y-axis of left and right thumbsticks)
	 */
	public BlastoiseController(TurtleXboxController controller, BlastoiseChassis chassis){
		leftJoystick = controller.getAxis(TurtleXboxController.XboxAxis.LY);
		rightJoystick = controller.getAxis(TurtleXboxController.XboxAxis.RY);
		this.chassis = chassis;

		logger = TurtleLogger.getLogger();
	}

	/**
	 * Instantiate BlastoiseController with two flight-stick controllers
	 */
	public BlastoiseController(TurtleFlightStick left, TurtleFlightStick right, BlastoiseChassis chassis){
		leftJoystick = left.getAxis(TurtleFlightStick.FlightAxis.PITCH);
		rightJoystick = right.getAxis(TurtleFlightStick.FlightAxis.PITCH);
		this.chassis = chassis;

		logger = TurtleLogger.getLogger();
	}

	/**
	 * Basic tele-op mode, joystick values (forward/backward) sent directly to chassis train
	 */
	public void teleUpdate() {
		if(chassis == null) {
			logger.error("No Chassis Configured");
			return;
		}
		MotorValue leftPower = new MotorValue(leftJoystick.get());
		MotorValue rightPower = new MotorValue(rightJoystick.get());

		chassis.updateMotors(leftPower, rightPower);
	}

	/**
	 * Autonomous mode
	 */
	public void autonomous() {
		new BlastoiseTestTimedAutonomous(chassis).run();
	}

	public void setRightJoystick(TurtleAnalogInput rightJoystick) {
		this.rightJoystick = rightJoystick;
	}

	public void setLeftJoystick(TurtleAnalogInput leftJoystick) {
		this.leftJoystick = leftJoystick;
	}

	public void setChassis(BlastoiseChassis chassis){
		this.chassis = chassis;
	}
}
