package org.usfirst.frc.team1458.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.VisionException;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleVision;
import com.team1458.turtleshell.vision.TurtleCameraServer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigVision implements TurtleVision {
    private static TurtwigVision instance;

    public static TurtwigVision getInstance() {
	if (instance == null) {
	    instance = new TurtwigVision();
	}
	return instance;
    }

    private int session;
    private Image image;

    private Image sendImage;

    private boolean targetRecognised;

    private TurtwigVision() {
	initCamera();
    }

    private void initCamera() {
	try {
	    session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	    NIVision.IMAQdxConfigureGrab(session);
	  //  NIVision.IMAQdx

	    // create images
	    image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);

	    // Put default values to SmartDashboard so fields will appear
	    SmartDashboard.putBoolean("use binary", false);
	} catch (VisionException e) {
	    TurtleLogger.critical("Camera Not found");
	    e.printStackTrace();
	}

    }

    @Override
    public void update() {
	try {
	    NIVision.IMAQdxGrab(session, image, 1);// problem righhere!!!!

	    if (!SmartDashboard.getBoolean("use binary")) {
		sendImage = image;
		TurtleCameraServer.getInstance().setImage(image);
	    }
	} catch (Exception e) {
	    TurtleLogger.severe("Camera code failed");
	}

    }


    @Override
    public boolean targetRecognised() {
	return targetRecognised;
    }

    @Override
    public Image getImage() {
	return sendImage;
    }
}