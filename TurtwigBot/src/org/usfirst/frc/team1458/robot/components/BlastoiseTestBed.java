package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleVictorSP;
import com.team1458.turtleshell2.implementations.pid.PID;
import com.team1458.turtleshell2.implementations.sensor.TurtleRotationCounter;
import com.team1458.turtleshell2.implementations.sensor.TurtleRotationEncoder;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Not to be used at competition
 *
 * @author asinghani
 */
public class BlastoiseTestBed implements TurtleComponent {

	TurtleLogger logger;
	boolean disabled = false;
	private TurtleRotationCounter c;
	private TurtleMotor shooter;
	private PID pid;
	private MotorValue pow;

	/**
	 * Test bed for shooter
	 */
	TurtleAnalogInput shooterSpeed;
	TurtleButtonInput smart;

	public BlastoiseTestBed(TurtleLogger logger, TurtleFlightStick rightStick,
			TurtleFlightStick leftStick) {
		this.logger = logger;

		shooterSpeed = rightStick.getAxis(TurtleFlightStick.FlightAxis.THROTTLE).positive();
		smart = rightStick.getButton(TurtleFlightStick.FlightButton.TWO);

		disabled = DriverStation.getInstance().isFMSAttached();
		if (disabled) {
			logger.warn("BlastoiseTestBed was instantiated while FMS enabled");
		}

		c = new TurtleRotationCounter(9, false);
		shooter = new TurtleVictorSP(9);
		pid = new PID(new PIDConstants(0.025, 0, 0), 274, 0);
	}

	public BlastoiseTestBed(TurtleLogger logger, TurtleXboxController xboxController) {
		this.logger = logger;

		shooterSpeed = xboxController.getAxis(TurtleXboxController.XboxAxis.RY);
		smart = xboxController.getButton(TurtleXboxController.XboxButton.A);

		disabled = DriverStation.getInstance().isFMSAttached();
		if (disabled) {
			logger.warn("BlastoiseTestBed was instantiated while FMS enabled");
		}
	}

	@Override
	public void teleUpdate() {
		if (disabled) {
			return;
		}
		
		SmartDashboard.putNumber("Hall Ticks", c.getRotation().getRevolutions());
		SmartDashboard.putNumber("Hall Rate", c.getRate().getValue());
		SmartDashboard.putNumber("RPM",c.getRate().getValue()*16.425);
		pow = new MotorValue(pid.newValue(c.getRate().getValue()));
		if(smart.getButton()) {
			shooter.set(new MotorValue(.6634));
		} else {
			shooter.set(pow/*new MotorValue(TurtleMaths.deadband(shooterSpeed.get(),
				RobotConstants.JOYSTICK_DEADBAND))*/);
		}
		
		SmartDashboard.putNumber("PIDPower",pow.getValue());
		SmartDashboard.putNumber("Shooter Speed", shooterSpeed.get());
	}
}
