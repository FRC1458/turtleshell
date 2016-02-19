package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleSmartChassis;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleAnalogGyro;
import com.team1458.turtleshell.physical.TurtleOnboardAccelerometer;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.pid.TurtleDualPID;
import com.team1458.turtleshell.pid.TurtleStraightDrivePID;
import com.team1458.turtleshell.pid.TurtleTurnPID;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.sensor.TurtleSmartAccelerometer;
import com.team1458.turtleshell.sensor.TurtleTheta;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Output;

public class TurtwigSmartTankChassis implements TurtleSmartChassis {
	private final TurtleMotor lMotor1 = new TurtleVictor(TurtwigConstants.LEFT1VICTORPORT, false);
	private final TurtleMotor rMotor1 = new TurtleVictor(TurtwigConstants.RIGHT1VICTORPORT, true);
	private final TurtleMotor lMotor2 = new TurtleVictor(TurtwigConstants.LEFT2VICTORPORT, false);
	private final TurtleMotor rMotor2 = new TurtleVictor(TurtwigConstants.RIGHT2VICTORPORT, true);

	private final TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTENCODERPORT1,
			TurtwigConstants.LEFTENCODERPORT2, true);
	private final TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTENCODERPORT1,
			TurtwigConstants.RIGHTENCODERPORT2, false);
	private final TurtleTheta gyro = new TurtleAnalogGyro(TurtwigConstants.GYROPORT);

	private TurtleDualPID pid;
	
	private final TurtleSmartAccelerometer accel = new TurtleOnboardAccelerometer();

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// nothing to do
	}

	@Override
	public void autoUpdate() {
		this.driveMotors(pid.newValue(new double[] { lEncoder.getTicks(), rEncoder.getTicks(), lEncoder.getRate(),
				rEncoder.getRate(), gyro.getContinousTheta(), gyro.getRate(), accel.getDown()[0], accel.getDown()[1] }));
	}

	@Override
	public void teleUpdate() {

		this.driveMotors(new MotorValue[] { new MotorValue(Input.getLPower()), new MotorValue(Input.getRPower()) });

	}

	@Override
	public void setLinearTarget(double target) {
		TurtleLogger.info("Setting linear target: " + target);
		Output.outputNumber("target", target);
		lEncoder.reset();
		rEncoder.reset();
		gyro.reset();
		pid = new TurtleStraightDrivePID(TurtwigConstants.straightConstants,
				target * 360 / (TurtwigConstants.WHEELDIAMETER * Math.PI), 0.00005, TurtwigConstants.pidTolerance);

	}

	public void setRoughTerrainLinearTarget(double target) {
		TurtleLogger.info("Setting rough terrain linear target: " + target);
		Output.outputNumber("target", target);
		gyro.reset();
		pid = new RoughTerrainPID();
	}

	@Override
	public void setThetaTarget(double target) {
		TurtleLogger.info("Setting theta target: " + target);
		lEncoder.reset();
		rEncoder.reset();
		gyro.reset();
		pid = new TurtleTurnPID(TurtwigConstants.turnConstants, target, .45, 8, 26.5,
				TurtwigConstants.turnGyroConstants, 1.26, TurtwigConstants.pidTolerance);

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
		lMotor1.set(motorPowers[0]);
		lMotor2.set(motorPowers[0]);
		rMotor1.set(motorPowers[1]);
		rMotor2.set(motorPowers[1]);

	}

	public void stop() {
		lMotor1.set(new MotorValue(0));
		lMotor2.set(new MotorValue(0));
		rMotor1.set(new MotorValue(0));
		rMotor2.set(new MotorValue(0));
	}
	
	private class RoughTerrainPID implements TurtleDualPID {
		private double pitch;
		private double roll;
		@Override
		public boolean atTarget() {
			// TODO Auto-generated method stub
			return false;
		}

		/**
		 * @param inputs discard, discard, discard, discard, theta, theta rate, pitch, roll
		 */
		@Override
		public MotorValue[] newValue(double[] inputs) {
			pitch = inputs[6];
			roll = inputs[7];
			
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
