package org.usfirst.frc.team1458.robot;

import org.usfirst.frc.team1458.robot.constants.RobotConstants;

import com.team1458.turtleshell2.vision.Contour;
import com.team1458.turtleshell2.vision.GripInterface;

import java.util.Arrays;

/**
 * Vision Utilities, must be static
 *
 * @author asinghani
 */
public class BlastoiseVision {
    public static int getSpringX() {
        try {
            Contour[] contours = GripInterface.getContours(RobotConstants.GRIP_URL);
            Arrays.sort(contours);

            if(contours.length < 2) return -1;

            Contour contour1 = contours[contours.length - 1];
            Contour contour2 = contours[contours.length - 2];

            double x = (contour1.getCenterX() + contour2.getCenterX()) / 2.0;

            return (int) x;

        } catch(Exception e) {
            return -1;
        }
    }
}
