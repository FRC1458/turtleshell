package com.team1458.turtleshell2.implementations.sensor;

import com.team1458.turtleshell2.interfaces.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Created by mehnadnerd on 2016-09-09.
 */
public class TurtleDistanceEncoder implements TurtleDistanceSensor {
    private final Encoder enc;
    private final double ratio;

    /**
     * @param portA The DIO port the A channel is connected to
     * @param portB The DIO port the B channel is connected to
     * @param ratio The ratio, in inches/tick, of the Encoder
     * @param isReversed Encoder is reversed
     */
    public TurtleDistanceEncoder(int portA, int portB, double ratio, boolean isReversed) {
        enc = new Encoder(portA, portB);
        enc.setDistancePerPulse(1);
        this.ratio = (isReversed ? -1.0 : 1.0) * ratio;
    }

    /**
     * @param portA The DIO port the A channel is connected to
     * @param portB The DIO port the B channel is connected to
     * @param ratio The ratio, in inches/tick, of the Encoder
     */
    public TurtleDistanceEncoder(int portA, int portB, double ratio) {
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
    public TurtleDistanceEncoder(int portA, int portB) {
        this(portA, portB, 1);
    }

    /**
     * Constructor with default ratio of 1 and B channel of A+1
     *
     * @param portA The DIO port the A channel is connected to
     */
    public TurtleDistanceEncoder(int portA) {
        this(portA, portA + 1);
    }

    @Override
    public Distance getDistance() {
        return new Distance(enc.get() * ratio);
    }

    @Override
    public Rate<Distance> getVelocity() {
        return new Rate<>(enc.getRate() * ratio);
    }

    @Override
    public void reset() {
        enc.reset();
    }
}
