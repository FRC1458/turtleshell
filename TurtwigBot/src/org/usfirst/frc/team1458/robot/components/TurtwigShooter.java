package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.Constants;

import com.team1458.turtleshell2.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.pid.ShooterPID;
import com.team1458.turtleshell2.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigShooter {
	public final TurtleTalonSRXCAN motor;
	private ShooterPID pid;
	public final TurtleHallSensor hall;

	private boolean isManualPower = false;
	private boolean isReverse = false;
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
			motor = new TurtleTalonSRXCAN(Constants.RightShooter.MOTOR_PORT, true);
			hall = new TurtleHallSensor(Constants.RightShooter.HALL_PORT);
			con = Constants.RightShooter.PID_CONSTANTS;
		} else {
			motor = new TurtleTalonSRXCAN(Constants.LeftShooter.MOTOR_PORT, false);
			hall = new TurtleHallSensor(Constants.LeftShooter.HALL_PORT);
			con = Constants.LeftShooter.PID_CONSTANTS;
		}
		this.isRight = isRight;
		regenPID();
	}

	public void teleUpdate() {
		SmartDashboard.putNumber(getSmartDashboardTag() + "ShooterMotorCurrent", motor.getCurrent());
		SmartDashboard.putNumber("Raw Hall"+getSmartDashboardTag(), hall.getHall());
		if(isReverse) {
			motor.set(Constants.Shooter.REVERSE_SPEED);
		} else {
			if (isManualPower) {
				motor.set(new MotorValue(manualPower));
			} else {
				motor.set(new MotorValue(pid.newValue(hall.getRPM())));
			}
		}

		//agitator.set(Constants.Shooter.AGITATOR_SPEED);

		SmartDashboard.putNumber(getSmartDashboardTag() + "TurtwigShooter MotorValue", motor.get().getValue());
		SmartDashboard.putNumber(getSmartDashboardTag() + "TurtwigShooter RPM", hall.getRPM());
	}
	
	private String getSmartDashboardTag() {
		return isRight ? "Right " : "Left ";
	}

	public void startReverse() {
		isReverse = true;
	}

	private void regenPID() {
		if (Constants.Shooter.UBP) {
			pid = new ShooterPID(Constants.Shooter.PID_CONSTANTS, targetRPM, 0, new MotorValue(Constants.Shooter.UBPS * targetRPM));
		} else {
			pid = new ShooterPID(con, targetRPM, 0, targetOpenLoop);
		}
		isReverse = false;
	}

	public void stop() {
		motor.set(new MotorValue(0));
		isReverse = false;
	}

	public void setPIDConstants(PIDConstants p) {
		this.con = p;
		regenPID();
	}

	public void setIsManualPower(boolean a) {
		this.isManualPower = a;
		isReverse = false;
	}

	public void setManualPower(double d) {
		this.manualPower = d;
		isReverse = false;
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
