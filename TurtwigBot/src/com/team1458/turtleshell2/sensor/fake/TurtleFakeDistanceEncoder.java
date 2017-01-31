package com.team1458.turtleshell2.sensor.fake;

import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;
import edu.wpi.first.wpilibj.Encoder;

/**
 * @author asinghani
 */
public class TurtleFakeDistanceEncoder implements TurtleDistanceSensor {

    public TurtleFakeDistanceEncoder(Object... args) {

    }

    @Override
    public Distance getDistance() {
        return new Distance(0);
    }

    @Override
    public Rate<Distance> getVelocity() {
        return new Rate<>(0);
    }

    @Override
    public void reset() {

    }
}
