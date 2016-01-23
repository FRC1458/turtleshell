package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleDualPID;
import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleSmartChassis;
import com.team1458.turtleshell.TurtleStraightDrivePID;
import com.team1458.turtleshell.TurtleTheta;
import com.team1458.turtleshell.TurtleTurnPID;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleAnalogGyro;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigSmartTankChassis implements TurtleSmartChassis {
	private final TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFT1VICTORPORT, false);
	private final TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHT1VICTORPORT, true);

		private final TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTENCODERPORT1,
				TurtwigConstants.LEFTENCODERPORT2,true);
		private final TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTENCODERPORT1,
				TurtwigConstants.RIGHTENCODERPORT2,false);
		private final TurtleTheta gyro = new TurtleAnalogGyro(TurtwigConstants.GYROPORT);
	
	
	private TurtleDualPID pid;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// nothing to do
	}

	@Override
	public void autoUpdate() {
		this.driveMotors(pid.newValue(
				new double[] { lEncoder.getTicks(), rEncoder.getTicks(), lEncoder.getRate(), rEncoder.getRate(), gyro.getContinousTheta()}));

	}

	@Override
	public void teleUpdate() {
		this.driveMotors(new MotorValue[] { new MotorValue(Input.getLPower()), new MotorValue(Input.getRPower()) });

	}

	@Override
	public void setLinearTarget(double target) {
		lEncoder.reset();
		rEncoder.reset();
		pid = new TurtleStraightDrivePID(.002, .0008, .0001, target * 360
				/ (TurtwigConstants.WHEELDIAMETER * Math.PI), 0.00005);

	}

	@Override
	public void setThetaTarget(double target) {
		lEncoder.reset();
		rEncoder.reset();
		pid = new TurtleTurnPID(0.0022, .0004, .0004, target, 0.3, 8, 28, 0.001, 0.0002, 0.0002);

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
		Output.outputNumber("lPower", motorPowers[0].getValue());
		Output.outputNumber("rPower", motorPowers[1].getValue());
		lMotor.set(motorPowers[0]);
		rMotor.set(motorPowers[1]);
	}

}
