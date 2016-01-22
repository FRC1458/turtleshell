package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleAutoable;
import com.team1458.turtleshell.TurtleDualPID;
import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleSmartChassis;
import com.team1458.turtleshell.TurtleStraightDrivePID;
import com.team1458.turtleshell.TurtleTeleoperable;
import com.team1458.turtleshell.TurtleTurnPID;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigSmartTankChassis implements TurtleSmartChassis, TurtleTeleoperable, TurtleAutoable {
	private final TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFT1VICTORPORT, false);
	private final TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHT1VICTORPORT, true);

	private final TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTENCODERPORT1,
			TurtwigConstants.LEFTENCODERPORT2);
	private final TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTENCODERPORT1,
			TurtwigConstants.RIGHTENCODERPORT2);

	private TurtleDualPID pid;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// nothing to do
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void autoUpdate() {
		this.driveMotors(pid.newValue(
				new double[] { lEncoder.getTicks(), rEncoder.getTicks(), lEncoder.getRate(), rEncoder.getRate() }));

	}

	@Override
	public void teleUpdate() {
		this.driveMotors(new MotorValue[] { new MotorValue(Input.getLPower()), new MotorValue(Input.getRPower()) });

	}

	@Override
	public void setLinearTarget(double target) {
		pid = new TurtleStraightDrivePID(.002, .0008, .0001, target * 360
				/ (TurtwigConstants.WHEELDIAMETER * Math.PI), 0.00005);

	}

	@Override
	public void setThetaTarget(double target) {
		pid = new TurtleTurnPID(0.0015, .0001, .0001, target, 0.00005, 8, 24);

	}

	@Override
	public boolean atTarget() {
		return pid.atTarget();
	}

	/**
	 * 
	 * @param motorPowers
	 *            Left, right
	 */
	private void driveMotors(MotorValue[] motorPowers) {
		lMotor.set(motorPowers[0]);
		rMotor.set(motorPowers[1]);
	}

}
