package com.team1458.turtleshell2.implementations.sensor;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;
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
public class TurtleNavX {
    AHRS navX;

    /**
     * Create NavX with specified I2C port
     * @param i2cPort
     */
    public TurtleNavX(I2C.Port i2cPort) {
        navX = new AHRS(i2cPort);
    }

    /**
     * Create NavX with specified Serial/USB port
     * @param serialPort
     */
    public TurtleNavX(SerialPort.Port serialPort) {
        navX = new AHRS(serialPort);
    }

    /**
     * Create NavX with specified SPI port (MXP only)
     * @param spiPort
     */
    public TurtleNavX(SPI.Port spiPort) {
        navX = new AHRS(spiPort);
    }

    /**
     * Create "fake" NavX, returns all zero values
     */
    public TurtleNavX() {
        navX = null;
    }

    public boolean registerCallback(ITimestampedDataSubscriber callback, Object callback_context) {
        if(navX != null) return navX.registerCallback(callback, callback_context);
        return false;
    }

    /**
     * Returns update rate in Hz
     */
    public int getUpdateRate() {
        if(navX != null) return navX.getActualUpdateRate();
        return 0;
    }

    public Angle getPitch() {
        if(navX != null) return new Angle(navX.getPitch());
        return Angle.zero;
    }

    public Angle getRoll() {
        if(navX != null) return Angle(navX.getRoll());
        return Angle.zero;
    }

    public Angle getYaw() {
        if(navX != null) return Angle(navX.getYaw());
        return Angle.zero;
    }

    public Angle getCompassHeading() {
        if(navX != null) return new Angle(navX.getCompassHeading());
        return Angle.zero;
    }

    public void zeroYaw() {
        if(navX != null) navX.zeroYaw();
    }

    public boolean isCalibrating() {
        if(navX != null) return navX.isCalibrating();
        return false;
    }

    public boolean isConnected() {
        if(navX != null) return navX.isConnected();
        return true;
    }

    public double getUpdateCount() {
        if(navX != null) return navX.getUpdateCount();
        return 0;
    }

    public long getLastSensorTimestamp() {
        if(navX != null) return navX.getLastSensorTimestamp();
        return 0;
    }

    public float getWorldLinearAccelX() {
        if(navX != null) return navX.getWorldLinearAccelX();
        return 0;
    }

    public float getWorldLinearAccelY() {
        if(navX != null) return navX.getWorldLinearAccelY();
        return 0;
    }

    public float getWorldLinearAccelZ() {
        if(navX != null) return navX.getWorldLinearAccelZ();
        return 0;
    }

    public boolean isMoving() {
        if(navX != null) return navX.isMoving();
        return false;
    }

    public boolean isRotating() {
        if(navX != null) return navX.isRotating();
        return false;
    }

    public Angle getFusedHeading() {
        if(navX != null) return new Angle(navX.getFusedHeading());
        return Angle.zero;
    }

    public boolean isMagneticDisturbance() {
        if(navX != null) return navX.isMagneticDisturbance();
        return false;
    }

    public boolean isMagnetometerCalibrated() {
        if(navX != null) return navX.isMagnetometerCalibrated();
        return false;
    }

    public void resetDisplacement() {
        if(navX != null) navX.resetDisplacement();
    }

    public Rate<Distance> getVelocityX() {
        if(navX != null) return new Rate<Distance>(navX.getVelocityX());
        return Rate.distanceZero;
    }

    public Rate<Distance> getVelocityY() {
        if(navX != null) return new Rate<Distance>(navX.getVelocityY());
        return Rate.distanceZero;
    }

    public Rate<Distance> getVelocityZ() {
        if(navX != null) return new Rate<Distance>(navX.getVelocityZ());
        return Rate.distanceZero;
    }

    public Distance getDisplacementX() {
        if(navX != null) return Distance.createMetres(navX.getDisplacementX());
        return Distance.zero;
    }

    public Distance getDisplacementY() {
        if(navX != null) return Distance.createMetres(navX.getDisplacementY());
        return Distance.zero;
    }

    public Distance getDisplacementZ() {
        if(navX != null) return Distance.createMetres(navX.getDisplacementZ());
        return Distance.zero;
    }

    public Angle getAngle() {
        if(navX != null) return new Angle(navX.getAngle());
        return Angle.zero;
    }

    public Rate<Angle> getRate() {
        if(navX != null) return new Rate<Angle>(navX.getRate());
        return Rate.angleZero;
    }

    public void reset() {
        if(navX != null) navX.reset();
    }

    public float getRawGyroX() {
        if(navX != null) return navX.getRawGyroX();
        return 0;
    }

    public float getRawGyroY() {
        if(navX != null) return navX.getRawGyroY();
        return 0;
    }

    public float getRawGyroZ() {
        if(navX != null) return navX.getRawGyroZ();
        return 0;
    }

    public float getRawAccelX() {
        if(navX != null) return navX.getRawAccelX();
        return 0;
    }

    public float getRawAccelY() {
        if(navX != null) return navX.getRawAccelY();
        return 0;
    }

    public float getRawAccelZ() {
        if(navX != null) return navX.getRawAccelZ();
        return 0;
    }

    public float getRawMagX() {
        if(navX != null) return navX.getRawMagX();
        return 0;
    }

    public float getRawMagY() {
        if(navX != null) return navX.getRawMagY();
        return 0;
    }

    public float getRawMagZ() {
        if(navX != null) return navX.getRawMagZ();
        return 0;
    }

    public float getTempC() {
        if(navX != null) return navX.getTempC();
        return 0;
    }

    public String getFirmwareVersion() {
        if(navX != null) return navX.getFirmwareVersion();
        return "Fake NavX 1.0";
    }

    @Override
    public String toString() {
        return "";
    }

    public AHRS getAHRS() {
        return navX;
    }

    /**
     * Instantiation utility methods are below
     */

    /**
     * Get TurtleNavX instance from default I2C port
     * @return TurtleNavX
     */
    public static TurtleNavX getInstanceI2C(){
        return new TurtleNavX(I2C.Port.kOnboard);
    }

    /**
     * Get TurtleNavX instance from specified USB port
     * @param portNumber
     * @return TurtleNavX
     */
    public static TurtleNavX getInstanceUSB(int portNumber){
        SerialPort.Port port;

        switch(portNumber) {
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
     * @return TurtleNavX
     */
    public static TurtleNavX getInstanceMXP(){
        return new TurtleNavX(SPI.Port.kMXP);
    }
}
