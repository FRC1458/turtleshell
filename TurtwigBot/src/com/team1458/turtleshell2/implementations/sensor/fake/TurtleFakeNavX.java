package com.team1458.turtleshell2.implementations.sensor.fake;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;
import com.team1458.turtleshell2.implementations.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * NavX Micro & MXP wrapper
 *
 * @author asinghani
 */

@SuppressWarnings("unused")
public class TurtleFakeNavX extends TurtleNavX {

    /**
     * Create NavX with specified I2C port
     * @param i2cPort
     */
    public TurtleFakeNavX(I2C.Port i2cPort) {
        this();
    }

    /**
     * Create NavX with specified Serial/USB port
     * @param serialPort
     */
    public TurtleFakeNavX(SerialPort.Port serialPort) {
        this();
    }

    /**
     * Create NavX with specified SPI port (MXP only)
     * @param spiPort
     */
    public TurtleFakeNavX(SPI.Port spiPort) {
        this();
    }

    /**
     * Create fake NavX, returns all zero values
     */
    public TurtleFakeNavX() {
        super();
    }

    public boolean registerCallback(ITimestampedDataSubscriber callback, Object callback_context) {
        return false;
    }

    /**
     * Returns update rate in Hz
     */
    public int getUpdateRate() {
        return 0;
    }

    public Angle getPitch() {
        return Angle.zero;
    }

    public Angle getRoll() {
        return Angle.zero;
    }

    public Angle getYaw() {
        return Angle.zero;
    }

    public Angle getCompassHeading() {
        return Angle.zero;
    }

    public void zeroYaw() {
        
    }

    public boolean isCalibrating() {
        return false;
    }

    public boolean isConnected() {
        return true;
    }

    public double getUpdateCount() {
        return 0;
    }

    public long getLastSensorTimestamp() {
        return 0;
    }

    public float getWorldLinearAccelX() {
        return 0;
    }

    public float getWorldLinearAccelY() {
        return 0;
    }

    public float getWorldLinearAccelZ() {
        return 0;
    }

    public boolean isMoving() {
        return false;
    }

    public boolean isRotating() {
        return false;
    }

    public Angle getFusedHeading() {
        return Angle.zero;
    }

    public boolean isMagneticDisturbance() {
        return false;
    }

    public boolean isMagnetometerCalibrated() {
        return false;
    }

    public void resetDisplacement() {
        
    }

    public Rate<Distance> getVelocityX() {
        return Rate.distanceZero;
    }

    public Rate<Distance> getVelocityY() {
        return Rate.distanceZero;
    }

    public Rate<Distance> getVelocityZ() {
        return Rate.distanceZero;
    }

    public Distance getDisplacementX() {
        return Distance.zero;
    }

    public Distance getDisplacementY() {
        return Distance.zero;
    }

    public Distance getDisplacementZ() {
        return Distance.zero;
    }

    public Angle getAngle() {
        return Angle.zero;
    }

    public Rate<Angle> getRate() {
        return Rate.angleZero;
    }

    public void reset() {
        
    }

    public float getRawGyroX() {
        return 0;
    }

    public float getRawGyroY() {
        return 0;
    }

    public float getRawGyroZ() {
        return 0;
    }

    public float getRawAccelX() {
        return 0;
    }

    public float getRawAccelY() {
        return 0;
    }

    public float getRawAccelZ() {
        return 0;
    }

    public float getRawMagX() {
        return 0;
    }

    public float getRawMagY() {
        return 0;
    }

    public float getRawMagZ() {
        return 0;
    }

    public float getTempC() {
        return 0;
    }

    public String getFirmwareVersion() {
        return "Fake NavX 1.0";
    }

    public AHRS getAHRS() {
        return null;
    }
}
