package org.usfirst.frc.team1458.robot;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.VisionException;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleDistance;
import com.team1458.turtleshell.sensor.TurtleTheta;
import com.team1458.turtleshell.sensor.TurtleVision;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;
import com.team1458.turtleshell.vision.Particle;
import com.team1458.turtleshell.vision.ScoreAnalyser;
import com.team1458.turtleshell.vision.Scores;
import com.team1458.turtleshell.vision.TurtleCameraServer;
import com.team1458.turtleshell.vision.VisionMaths;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigFeedVision implements TurtleVision {
    private static TurtwigFeedVision instance;

    public static TurtwigFeedVision getInstance() {
	if (instance == null) {
	    instance = new TurtwigFeedVision();
	}
	return instance;
    }

    private int session;
    private Image image;

    private Image sendImage;

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
	// SmartDashboard.putNumber("ImageAddress", image.getAddress());
	try {
	    NIVision.IMAQdxGrab(session, image, 1);// problem righhere!!!!
	    sendImage = image;
	    TurtleCameraServer.getInstance().setImage(sendImage);
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
	return sendImage;
    }
}
