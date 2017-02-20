package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.Constants;

import com.team1458.turtleshell2.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.pid.ShooterPID;
import com.team1458.turtleshell2.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigShooter {
	private final TurtleSmartMotor motor;
	private ShooterPID pid;
	private final TurtleHallSensor hall;

	private boolean isManualPower = false;
	private double manualPower = 0;

	private double targetRPM = 0;
	private MotorValue targetOpenLoop = MotorValue.zero;
	private PIDConstants con;
	
	private final boolean isRight;

	/**
	 * 
	 * @param isRight
	 */
	public TurtwigShooter(boolean isRight) {
		if (isRight) {
			motor = new TurtleTalonSRXCAN(Constants.RightShooter.MOTOR_PORT,false);
			hall = new TurtleHallSensor(Constants.RightShooter.HALL_PORT);
			con = Constants.RightShooter.PID_CONSTANTS;
		} else {
			motor = new TurtleTalonSRXCAN(Constants.LeftShooter.MOTOR_PORT, true);
			hall = new TurtleHallSensor(Constants.LeftShooter.HALL_PORT);
			con = Constants.LeftShooter.PID_CONSTANTS;
		}
		this.isRight = isRight;
		regenPID();
	}

	public void teleUpdate() {
		SmartDashboard.putNumber(getSmartDashboardTag()+"ShooterMotorCurrent", motor.getCurrent());
		if (isManualPower) {
			motor.set(new MotorValue(manualPower));
		} else {
			motor.set(new MotorValue(pid.newValue(hall.getRPM())));
		}
		SmartDashboard.putNumber(getSmartDashboardTag()+"TurtwigShooter MotorValue", motor.get()
				.getValue());
		SmartDashboard.putNumber(getSmartDashboardTag()+"TurtwigShooter RPM", hall.getRPM());

	}
	
	private String getSmartDashboardTag() {
		return isRight ? "Right " : "Left ";
	}

	private void regenPID() {
		pid = new ShooterPID(con, targetRPM, 0, targetOpenLoop);
	}

	public void stop() {
		motor.set(new MotorValue(0));
	}
	
	public void setPIDConstants(PIDConstants p) {
		this.con = p;
		regenPID();
	}

	public void setIsManualPower(boolean a) {
		this.isManualPower = a;
	}

	public void setManualPower(double d) {
		this.manualPower = d;
	}

	public void setRPMTarget(double d) {
		this.targetRPM = d;
		regenPID();
	}

	public void setTargetOpenLoop(MotorValue d) {
		this.targetOpenLoop = d;
		regenPID();
	}

}
