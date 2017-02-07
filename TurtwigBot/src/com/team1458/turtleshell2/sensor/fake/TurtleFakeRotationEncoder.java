package com.team1458.turtleshell2.sensor.fake;

import com.team1458.turtleshell2.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

/**
 * @author asinghani
 */
public class TurtleFakeRotationEncoder implements TurtleRotationSensor {

    public TurtleFakeRotationEncoder(Object... args) {

    }

    @Override
    public Angle getRotation() {
        return new Angle(0);
    }

    @Override
    public Rate<Angle> getRate() {
        return new Rate<>(0);
    }

    @Override
    public void reset() {

    }
}
