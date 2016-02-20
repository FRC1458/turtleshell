package com.team1458.turtleshell.vision;

import com.ni.vision.NIVision.Image;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Multiple camera server Extended from
 * https://gist.github.com/Wazzaps/bb9e72696f8980e7e727
 * 
 * @author mehnadnerd
 */
public class TurtleCameraServer {
    private static TurtleCameraServer instance;

    public static TurtleCameraServer getInstance() {
	if (instance == null) {
	    instance = new TurtleCameraServer();
	}
	return instance;
    }

    private CameraServer cs;

    private TurtleCameraServer() {
	cs = CameraServer.getInstance();
    }

    /**
     * Send the image to the dashboard
     */
    /*
    @Override
    public void run() {
	try {
	    wait(100);
	} catch (InterruptedException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	SmartDashboard.putBoolean("Doing Camera Server Loop", true);
	while (true) {
	    try {
		SmartDashboard.putBoolean("Doing Camera Server Loop three", true);
		System.out.println("Camera Server looping");
		SmartDashboard.putString("Image", v.getImage().toString());
		cs.setImage(v.getImage());
		SmartDashboard.putBoolean("Doing Camera Server Loop four", true);

		SmartDashboard.putBoolean("Doing Camera Server Set", true);
	    } catch (NullPointerException e) {
		TurtleLogger.warning("Vision not providing image");
	    }

	    try {
		wait(50);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }*/

    public void setImage(Image i) {
	cs.setImage(i);
    }
}
