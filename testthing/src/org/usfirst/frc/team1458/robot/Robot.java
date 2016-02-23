package org.usfirst.frc.team1458.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Talon t = new Talon(0);
	Victor v = new Victor(1);
	Victor vL = new Victor(4);
	Victor vR = new Victor(9);
	// KE, 2/23
	// Victor vR = new Victor(6);
	Encoder e = new Encoder(4, 5);
	Joystick j = new Joystick(0);
	Joystick j2 = new Joystick(1);
	Joystick jXB = new Joystick(3);
	int session;
	Image image;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		try {
			session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			NIVision.IMAQdxConfigureGrab(session);
			image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		} catch (VisionException e) {

		}

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
	    	vL.set(-j.getAxis(Joystick.AxisType.kY));
		vR.set(j.getAxis(Joystick.AxisType.kY));
		
	    	// Commented out by KE, 0 Period 2/23
	    	/*t.set(j.getAxis(Joystick.AxisType.kY));
		v.set(j2.getAxis(Joystick.AxisType.kY));
		vL.set(jXB.getRawAxis(1));
		vR.set(-jXB.getRawAxis(1));
		SmartDashboard.putNumber("ENCODERTHING", e.get());
		try {
			NIVision.IMAQdxGrab(session, image, 1);
			CameraServer.getInstance().setImage(image);
		} catch (Exception e) {

		}
	    	*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
