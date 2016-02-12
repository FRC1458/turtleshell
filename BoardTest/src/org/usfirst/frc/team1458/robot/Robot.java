
package org.usfirst.frc.team1458.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    Joystick stick;
    Victor v1, v2, v3, v4, v5, v6;
    Talon t1, t2;

    public Robot() {
        stick = new Joystick(0);
        v1 = new Victor(0);
        v2 = new Victor(1);
        v3 = new Victor(2);
        v4 = new Victor(3);
        v5 = new Victor(4);
        v6 = new Victor(5);
        t1 = new Talon(6);
        t2 = new Talon(7);
    }
    
    public void robotInit() {
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
            v1.set(stick.getAxis(Joystick.AxisType.kY));
            v2.set(stick.getAxis(Joystick.AxisType.kY));
            v3.set(stick.getAxis(Joystick.AxisType.kY));
            v4.set(stick.getAxis(Joystick.AxisType.kY));
            v5.set(stick.getAxis(Joystick.AxisType.kY));
            v6.set(stick.getAxis(Joystick.AxisType.kY));
            t1.set(stick.getAxis(Joystick.AxisType.kY));
            t2.set(stick.getAxis(Joystick.AxisType.kY));
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
