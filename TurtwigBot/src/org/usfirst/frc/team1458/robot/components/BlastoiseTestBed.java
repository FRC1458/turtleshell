package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleVictorSP;
import com.team1458.turtleshell2.implementations.pid.PID;
import com.team1458.turtleshell2.implementations.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.interfaces.RobotComponent;
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
public class BlastoiseTestBed implements RobotComponent {

	TurtleLogger logger;
	boolean disabled = false;
	private TurtleHallSensor c;
	private TurtleMotor shooter;
	private PID pid;
	private MotorValue pow;

	/**
	 * Test bed for shooter
	 */
	TurtleAnalogInput shooterSpeed;
	TurtleButtonInput smart;

	double lastTarget = 4500/16.425;
	
	PIDConstants constants = new PIDConstants(0.07, 0.09, 0.04);
	
	{
		c = new TurtleHallSensor(9, false);
		shooter = new TurtleVictorSP(9);
		pid = new PID(constants, lastTarget, 0);
		SmartDashboard.putNumber("RPM TARGET", 4000);

		SmartDashboard.putNumber("kP", constants.kP);
		SmartDashboard.putNumber("kI", constants.kI);
		SmartDashboard.putNumber("kD", constants.kD);
	}
	
	public BlastoiseTestBed(TurtleLogger logger, TurtleFlightStick rightStick,
			TurtleFlightStick leftStick) {
		this.logger = logger;

		shooterSpeed = rightStick.getAxis(TurtleFlightStick.FlightAxis.THROTTLE).positive();
		smart = rightStick.getButton(TurtleFlightStick.FlightButton.TWO);

		disabled = DriverStation.getInstance().isFMSAttached();
		if (disabled) {
			logger.warn("BlastoiseTestBed was instantiated while FMS enabled");
		}
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
		
		if(SmartDashboard.getNumber("kP", 0) != constants.kP ||
				SmartDashboard.getNumber("kI", 0) != constants.kI ||
				SmartDashboard.getNumber("kD", 0) != constants.kD) {
			constants = new PIDConstants(SmartDashboard.getNumber("kP", 0),
					SmartDashboard.getNumber("kI", 0),
					SmartDashboard.getNumber("kD", 0));
			pid = new PID(constants, lastTarget, 0);
		}
		
		if(SmartDashboard.getNumber("RPM TARGET", 4500) != lastTarget){
			lastTarget = SmartDashboard.getNumber("RPM TARGET", 4500)/16.425;
			pid = new PID(constants, lastTarget, 0);
		}
		
		SmartDashboard.putNumber("Hall Ticks", c.getRotation().getRevolutions());
		SmartDashboard.putNumber("Hall Rate", c.getHall());
		SmartDashboard.putNumber("RPM", c.getHall()*16.425);
		double rawpower = pid.newValue(c.getHall());
		SmartDashboard.putNumber("Rawpower", rawpower);
		pow = new MotorValue(rawpower * 1);
		if(smart.getButton()) {
			shooter.set(new MotorValue(.6634));
		} else {
			shooter.set(pow);
		}
		
		SmartDashboard.putNumber("PIDPower",pow.getValue());
		SmartDashboard.putNumber("Shooter Speed", shooterSpeed.get());
	}
}
