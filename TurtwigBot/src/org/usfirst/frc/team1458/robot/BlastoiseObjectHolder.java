package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.AutoModeHolder;
import com.team1458.turtleshell2.interfaces.TestMode;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestDistanceAutonomous;
import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestTimedAutonomous;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Object Holder for BlastoiseRobot.
 * This controls switching between multiple autonomous modes.
 *
 * @author asinghani
 */
public class BlastoiseObjectHolder extends SampleRobotObjectHolder implements AutoModeHolder {

	private ArrayList<BlastoiseAutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;

	private TestMode testMode;

	private TurtleLogger logger;
	private BlastoiseChassis chassis;
	
	/**
	 * Instantiates BlastoiseObjectHolder
	 * @param logger Logger
	 */
	public BlastoiseObjectHolder(TurtleLogger logger) {
		this.logger = logger;

		// Setup controller and chassis
		
		if(BlastoiseConstants.USE_FLIGHT_STICK){
			TurtleFlightStick leftStick = new TurtleFlightStick(BlastoiseConstants.UsbPorts.LEFT_STICK);
	        TurtleFlightStick rightStick = new TurtleFlightStick(BlastoiseConstants.UsbPorts.RIGHT_STICK);
	        chassis = new BlastoiseChassis(leftStick, rightStick, logger);
		} else {
			TurtleXboxController xboxController = new TurtleXboxController(BlastoiseConstants.UsbPorts.XBOX_CONTROLLER);
			chassis = new BlastoiseChassis(xboxController, logger);
		}
		
	    addComponent(chassis);

		// Setup AutoModes
	    autoModes.add(new BlastoiseTestDistanceAutonomous(chassis, logger));
		autoModes.add(new BlastoiseTestTimedAutonomous(chassis, logger));

		selectedAutoMode = 0;

		// Setup TestMode
		testMode = () -> {
			
			SmartDashboard.putNumber("TestValueJetson", 0);
			
			while(RobotState.isTest() && RobotState.isEnabled()){
				
				try {					
					URL url = new URL("http://tegra-ubuntu.local:5801/test");
					
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					InputStream is = connection.getInputStream();
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					
					String line = reader.readLine();
					
					logger.info("Got data: "+line);
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
				Timer.delay(0.5);
			}
			
		}; // Creates a TestMode with empty test() function

		TurtleDashboard.setAutoModeHolder(this);
	}

	private void addComponent(TurtleComponent component) {
		if (component != null) components.add(component);
	}

	/**
	 * Get the list of auto modes
	 */
	public ArrayList<BlastoiseAutoMode> getAutoModes() {
		return autoModes;
	}

	/**
	 * Set the index of the selected auto mode
	 * @param index
	 */
	public void setSelectedAutoModeIndex(int index) {
		if(index < autoModes.size()){
			selectedAutoMode = index;
			//logger.info("Selected auto mode "+index+": "+autoModes.get(index).getName());
		}
	}

	/**
	 * Get index of selected AutoMode
	 */
	public int getSelectedAutoModeIndex() {
		return selectedAutoMode;
	}

	@Override
	public TestMode getTest() {
		return testMode;
	}

	@Override
	public AutoMode getAuto() {
		return autoModes.get(selectedAutoMode);
	}
}