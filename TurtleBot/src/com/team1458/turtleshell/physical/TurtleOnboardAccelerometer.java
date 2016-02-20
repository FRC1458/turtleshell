package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.sensor.TurtleSmartAccelerometer;
import com.team1458.turtleshell.util.TurtleCalibratable;
import com.team1458.turtleshell.util.TurtleCalibration;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class TurtleOnboardAccelerometer implements TurtleSmartAccelerometer, TurtleCalibratable {
	private BuiltInAccelerometer acc;
	private TurtleSmartAccelerometerCalibration cal;

	public TurtleOnboardAccelerometer() {
		acc = new BuiltInAccelerometer();
	}

	@Override
	public double[] getAcceleration() {

		return new double[] { acc.getX(), acc.getY(), acc.getZ() };

	}

	@Override
	public double[] getDown() {
		double[] toRet = new double[] { Math.toDegrees(Math.atan2(acc.getZ(), acc.getX())),
				Math.toDegrees(Math.atan2(acc.getZ(), acc.getY())) };
		toRet[0]-=cal.getValues()[0];
		toRet[1]-=cal.getValues()[1];
		return toRet;
	}

	@Override
	public TurtleCalibration getCalibration() {
		return cal;
	}

	@Override
	public void setCalibration(TurtleCalibration calibration) {
		this.cal = (TurtleSmartAccelerometerCalibration) calibration;
	}

	@Override
	public TurtleCalibration generateCalibration() {
		double[] a = this.getDown();
		return new TurtleSmartAccelerometerCalibration(a[0], a[1]);
	}

}
