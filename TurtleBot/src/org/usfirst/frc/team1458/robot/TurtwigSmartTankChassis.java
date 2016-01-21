package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleAutoable;
import com.team1458.turtleshell.TurtleDualPID;
import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleSmartChassis;
import com.team1458.turtleshell.TurtleStraightDrivePID;
import com.team1458.turtleshell.TurtleTeleoperable;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigSmartTankChassis implements TurtleSmartChassis,
		TurtleTeleoperable, TurtleAutoable {
	private final TurtleMotor lMotor = new TurtleVictor(
			Constants.LEFT1VICTORPORT, false);
	private final TurtleMotor rMotor = new TurtleVictor(
			Constants.RIGHT1VICTORPORT, true);

	private final TurtleEncoder lEncoder = new Turtle4PinEncoder(
			Constants.LEFTENCODERPORT1, Constants.LEFTENCODERPORT2, true);
	private final TurtleEncoder rEncoder = new Turtle4PinEncoder(
			Constants.RIGHTENCODERPORT1, Constants.RIGHTENCODERPORT2, false);

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
		Output.outputNumber("lEncoderTicks", lEncoder.getTicks());
		Output.outputNumber("rEncoderTicks", rEncoder.getTicks());
		Output.outputNumber("lEncoderRate", lEncoder.getRate());
		Output.outputNumber("rEncoderRate", rEncoder.getRate());
		this.driveMotors(pid.newValue(new double[] { lEncoder.getTicks(),
				rEncoder.getTicks(), lEncoder.getRate(), rEncoder.getRate() }));

	}

	@Override
	public void teleUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLinearTarget(double target) {
		pid = new TurtleStraightDrivePID(.002, .0008, .0001, target * 360
				/ (Constants.WHEELDIAMETER * Math.PI), 0.00005);
		lEncoder.reset();
		rEncoder.reset();

	}

	@Override
	public void setThetaTarget(double target) {
		// TODO Auto-generated method stub

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

	@Override
	public void stop() {
		driveMotors(new MotorValue[] { new MotorValue(0), new MotorValue(0) });

	}

}
