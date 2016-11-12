package com.team1458.turtleshell.vision;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

/**
 * Multiple camera server
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

    public void setImage(Image i) {
	cs.setImage(i);
    }
}
