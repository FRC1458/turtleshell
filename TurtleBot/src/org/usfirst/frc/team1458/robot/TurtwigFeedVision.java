package org.usfirst.frc.team1458.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.VisionException;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleVision;
import com.team1458.turtleshell.vision.TurtleCameraServer;

public class TurtwigFeedVision implements TurtleVision{
    private static TurtwigFeedVision instance;

    public static TurtwigFeedVision getInstance() {
	if (instance == null) {
	    instance = new TurtwigFeedVision();
	}
	return instance;
    }

    private int session;
    private Image image;

    private TurtwigFeedVision() {
	initCamera();
    }

    private void initCamera() {
	try {
	    session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	    NIVision.IMAQdxConfigureGrab(session);
	    
	    // create images
	    image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	} catch (VisionException e) {
	    TurtleLogger.critical("Camera Not found");
	    e.printStackTrace();
	}

    }

    @Override
    public void update() {
	try {
	    NIVision.IMAQdxGrab(session, image, 1);// problem righhere!!!!
	    //NIVision.imaqDrawLineOnImage(image, image, NIVision.DrawMode.DRAW_INVERT, start, end, newPixelValue);
	    TurtleCameraServer.getInstance().setImage(image);
	} catch (Exception e) {
	    TurtleLogger.severe("Camera code failed");
	}

    }

    @Override
    public boolean targetRecognised() {
	return false;
    }

    @Override
    public Image getImage() {
	return image;
    }
    
    private void switchCamera(String camera) {
	NIVision.IMAQdxCloseCamera(session);
	session = NIVision.IMAQdxOpenCamera(camera, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	    NIVision.IMAQdxConfigureGrab(session);
    }

}
