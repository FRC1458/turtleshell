package com.team1458.turtleshell2.implementations.sensor;

import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Created by mehnadnerd on 2016-09-09.
 */
public class TurtleRotationEncoder implements TurtleRotationSensor {
    private final Encoder enc;
    private final double ratio;

    /**
     * @param portA The DIO port the A channel is connected to
     * @param portB The DIO port the B channel is connected to
     * @param ratio The ratio, in degrees/tick, of the Encoder
     */
    public TurtleRotationEncoder(int portA, int portB, double ratio) {
        enc = new Encoder(portA, portB);
        enc.setDistancePerPulse(1);
        this.ratio = ratio;
    }

    /**
     * Constructor with default ratio of 1.
     *
     * @param portA The DIO port the A channel is connected to
     * @param portB The DIO port the B channel is connected to
     */
    public TurtleRotationEncoder(int portA, int portB) {
        this(portA, portB, 1);
    }

    /**
     * Constructor with default ratio of 1 and B channel of A+1
     *
     * @param portA The DIO port the A channel is connected to
     */
    public TurtleRotationEncoder(int portA) {
        this(portA, portA + 1);
    }


    @Override
    public Angle getRotation() {
        return new Angle(enc.getDistance() * ratio);
    }

    @Override
    public Rate<Angle> getRate() {
        return new Rate<>(enc.getRate() * ratio);
    }

    @Override
    public void reset() {

    }
}
