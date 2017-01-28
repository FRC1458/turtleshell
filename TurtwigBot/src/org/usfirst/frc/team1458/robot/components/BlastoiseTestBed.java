package org.usfirst.frc.team1458.robot.components;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleFakeMotor;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.implementations.movement.TurtleVictorSP;
import com.team1458.turtleshell2.implementations.pid.TurtlePDD2;
import com.team1458.turtleshell2.implementations.sensor.TurtleRotationCounter;
import com.team1458.turtleshell2.implementations.sensor.TurtleRotationEncoder;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.interfaces.pid.TurtlePID;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.TurtlePIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Not to be used at competition
 *
 * @author asinghani
 */
public class BlastoiseTestBed implements TurtleComponent {
	TurtleLogger logger;
	boolean disabled = false;
	private TurtleRotationCounter c;
	private TurtleRotationEncoder e;
	private TurtleMotor shooter;
	private TurtlePID pid;
	private MotorValue pow;

	/**
	 * Test bed for shooter
	 */
	TurtleAnalogInput shooterSpeed;
	TurtleButtonInput smart;

	public BlastoiseTestBed(TurtleLogger logger, TurtleFlightStick rightStick,
			TurtleFlightStick leftStick) {
		this.logger = logger;

		shooterSpeed = rightStick
				.getAxis(TurtleFlightStick.FlightAxis.THROTTLE);

		disabled = DriverStation.getInstance().isFMSAttached();
		if (disabled) {
			logger.warn("BlastoiseTestBed was instantiated while FMS enabled");
		}
	}

	public BlastoiseTestBed(TurtleLogger logger,
			TurtleXboxController xboxController) {
		this.logger = logger;
		c = new TurtleRotationCounter(9, false);
		e = new TurtleRotationEncoder(7,8);
		shooter = new TurtleVictorSP(9);
		pid = new TurtlePDD2(new TurtlePIDConstants(0.025, 0, 0, 0),
				274, 0);

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
		pow = pid.newValue(new double[] { c.getRate().getValue(), 0, 0 });
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
