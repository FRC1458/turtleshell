package com.team1458.turtleshell2.sensor;

import com.kauailabs.navx.AHRSProtocol;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;
import com.team1458.turtleshell2.util.types.Time;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * NavX Micro & MXP wrapper
 *
 * @author asinghani & mehnadnerd
 */

public class TurtleNavX {
	private AHRS navX;

	private double last_world_linear_accel_x = 0;
	private double last_world_linear_accel_y = 0;

	public abstract class TurtleNavXAxis implements TurtleRotationSensor {
		protected abstract void setRate(Rate<Angle> rate);
	}

	private final TurtleNavXAxis yawAxis = new TurtleNavXAxis() {
		@Override
		public Angle getRotation() {
			return Angle.createDegrees(navX.getYaw());
		}

		@Override
		public Rate<Angle> getRate() {
			return new Rate<>(navX.getRate());
		}

		@Override
		public void reset() {
			navX.zeroYaw();
		}

		@Override
		protected void setRate(Rate<Angle> rate) {

		}
	};

	private final TurtleNavXAxis pitchAxis = new TurtleNavXAxis() {
		public Rate<Angle> rate = Rate.angleZero;

		@Override
		public Angle getRotation() {
			return Angle.createDegrees(navX.getPitch());
		}

		@Override
		public Rate<Angle> getRate() {
			return rate;
		}

		@Override
		protected void setRate(Rate<Angle> rate) {
			this.rate = rate;
		}

		/**
		 * Unsuppored operation, how do you think this would work? The robot flips?
		 * @throws UnsupportedOperationException
		 */
		@Override
		public void reset() {
			throw new UnsupportedOperationException(
					"Cannot reset the pitch, how do you think this would work? The robot flips?");
		}

	};
	
	private final TurtleNavXAxis rollAxis = new TurtleNavXAxis() {
		public Rate<Angle> rate = Rate.angleZero;

		@Override
		public Angle getRotation() {
			return Angle.createDegrees(navX.getRoll());
		}

		@Override
		public Rate<Angle> getRate() {
			return rate;
		}

		@Override
		protected void setRate(Rate<Angle> rate) {
			this.rate = rate;
		}

		/**
		 * Unsuppored operation, how do you think this would work? The robot flips on its side?
		 * @throws UnsupportedOperationException
		 */
		@Override
		public void reset() {
			throw new UnsupportedOperationException(
					"Cannot reset the roll, how do you think this would work? The robot flips on its side?");
		}
		
	};

	private final ITimestampedDataSubscriber dataSubscriber = new ITimestampedDataSubscriber() {
		float lastPitch = 0;
		float lastRoll = 0;

		long last_timestamp = -1;

		@Override
		public void timestampedDataReceived(long system_time, long sensor_time, AHRSProtocol.AHRSUpdateBase data, Object ctx) {
			if(last_timestamp > -1) {
				float pitchDifference;
				pitchDifference = data.pitch - lastPitch;
				if (pitchDifference > 180.0f) {
					pitchDifference = 360.0f - pitchDifference;
				} else if (pitchDifference < -180.0f) {
					pitchDifference = 360.0f + pitchDifference;
				}

				float rollDifference;
				rollDifference = data.roll - lastRoll;
				if (rollDifference > 180.0f) {
					rollDifference = 360.0f - rollDifference;
				} else if (rollDifference < -180.0f) {
					rollDifference = 360.0f + rollDifference;
				}

				pitchAxis.setRate(new Rate<>(new Angle(pitchDifference), new Time(((double) (last_timestamp - sensor_time)) / 1000.0)));
				rollAxis.setRate(new Rate<>(new Angle(rollDifference), new Time(((double) (last_timestamp - sensor_time)) / 1000.0)));
			}

			lastPitch = data.pitch;
			lastRoll = data.roll;
			last_timestamp = sensor_time;
		}
	};

	public TurtleNavXAxis getYawAxis() {
		return yawAxis;
	}
	
	public TurtleNavXAxis getPitchAxis() {
		return pitchAxis;
	}
	
	public TurtleNavXAxis getRollAxis() {
		return rollAxis;
	}

	/**
	 * Create NavX with specified I2C port
	 * 
	 * @param i2cPort
	 */
	public TurtleNavX(I2C.Port i2cPort) {
		navX = new AHRS(i2cPort);
		registerCallback(dataSubscriber, this);
	}

	/**
	 * Create NavX with specified Serial/USB port
	 * 
	 * @param serialPort
	 */
	public TurtleNavX(SerialPort.Port serialPort) {
		navX = new AHRS(serialPort);
		registerCallback(dataSubscriber, this);
	}

	/**
	 * Create NavX with specified SPI port (MXP only)
	 * 
	 * @param spiPort
	 */
	public TurtleNavX(SPI.Port spiPort) {
		navX = new AHRS(spiPort);
		registerCallback(dataSubscriber, this);
	}

	/**
	 * DO NOT USE THIS FUNCTION. It is only available for internal use.
	 */
	protected TurtleNavX() {

	}

	public boolean registerCallback(ITimestampedDataSubscriber callback, Object callback_context) {
		return navX.registerCallback(callback, callback_context);
	}

	/**
	 * Returns update rate in Hz
	 */
	public int getUpdateRate() {
		return navX.getActualUpdateRate();
	}

	public Angle getCompassHeading() {
		return new Angle(navX.getCompassHeading());
	}

	public void zeroYaw() {
		navX.zeroYaw();
	}

	public boolean isCalibrating() {
		return navX.isCalibrating();
	}

	public boolean isConnected() {
		return navX.isConnected();

	}

	public double getUpdateCount() {
		return navX.getUpdateCount();

	}

	public long getLastSensorTimestamp() {
		return navX.getLastSensorTimestamp();

	}

	public float getWorldLinearAccelX() {
		return navX.getWorldLinearAccelX();

	}

	public float getWorldLinearAccelY() {
		return navX.getWorldLinearAccelY();

	}

	public float getWorldLinearAccelZ() {
		return navX.getWorldLinearAccelZ();

	}

	public boolean isMoving() {
		return navX.isMoving();

	}

	public boolean isRotating() {
		return navX.isRotating();

	}

	public Angle getFusedHeading() {
		return new Angle(navX.getFusedHeading());

	}

	public boolean isMagneticDisturbance() {
		return navX.isMagneticDisturbance();

	}

	public boolean isMagnetometerCalibrated() {
		return navX.isMagnetometerCalibrated();

	}

	public void resetDisplacement() {
		navX.resetDisplacement();
	}

	public Rate<Distance> getVelocityX() {
		return new Rate<Distance>(navX.getVelocityX());

	}

	public Rate<Distance> getVelocityY() {
		return new Rate<Distance>(navX.getVelocityY());

	}

	public Rate<Distance> getVelocityZ() {
		return new Rate<Distance>(navX.getVelocityZ());

	}

	public Distance getDisplacementX() {
		return Distance.createMetres(navX.getDisplacementX());

	}

	public Distance getDisplacementY() {
		return Distance.createMetres(navX.getDisplacementY());

	}

	public Distance getDisplacementZ() {
		return Distance.createMetres(navX.getDisplacementZ());

	}

	public void reset() {
		navX.reset();
	}

	public float getRawGyroX() {
		return navX.getRawGyroX();

	}

	public float getRawGyroY() {
		return navX.getRawGyroY();

	}

	public float getRawGyroZ() {
		return navX.getRawGyroZ();

	}

	public float getRawAccelX() {
		return navX.getRawAccelX();

	}

	public float getRawAccelY() {
		return navX.getRawAccelY();

	}

	public float getRawAccelZ() {
		return navX.getRawAccelZ();

	}

	public float getRawMagX() {
		return navX.getRawMagX();

	}

	public float getRawMagY() {
		return navX.getRawMagY();

	}

	public float getRawMagZ() {
		return navX.getRawMagZ();

	}

	public float getTempC() {
		return navX.getTempC();

	}

	public boolean isInCollision(double threshold) {
		double curr_world_linear_accel_x = getWorldLinearAccelX();
		double currentJerkX = curr_world_linear_accel_x - last_world_linear_accel_x;
		last_world_linear_accel_x = curr_world_linear_accel_x;
		double curr_world_linear_accel_y = getWorldLinearAccelY();
		double currentJerkY = curr_world_linear_accel_y - last_world_linear_accel_y;
		last_world_linear_accel_y = curr_world_linear_accel_y;

		SmartDashboard.putNumber("JerkX", currentJerkX);
		SmartDashboard.putNumber("JerkY", currentJerkY);

		return (Math.abs(currentJerkX) > threshold) || (Math.abs(currentJerkY) > threshold);
	}

	public String getFirmwareVersion() {
		return navX.getFirmwareVersion();
	}

	public AHRS getAHRS() {
		return navX;
	}

	/**
	 * Instantiation utility methods are below
	 */

	/**
	 * Get TurtleNavX instance from default I2C port
	 * 
	 * @return TurtleNavX
	 */
	public static TurtleNavX getInstanceI2C() {
		return new TurtleNavX(I2C.Port.kOnboard);
	}

	/**
	 * Get TurtleNavX instance from specified USB port
	 * 
	 * @param portNumber
	 * @return TurtleNavX
	 */
	public static TurtleNavX getInstanceUSB(int portNumber) {
		SerialPort.Port port;

		switch (portNumber) {
		case 1:
			port = SerialPort.Port.kUSB1;
			break;
		case 2:
			port = SerialPort.Port.kUSB2;
			break;
		default:
			port = SerialPort.Port.kUSB;
			break;
		}

		return new TurtleNavX(port);
	}

	/**
	 * Get TurtleNavX from MXP port (through SPI)
	 * 
	 * @return TurtleNavX
	 */
	public static TurtleNavX getInstanceMXP() {
		return new TurtleNavX(SPI.Port.kMXP);
	}
}
