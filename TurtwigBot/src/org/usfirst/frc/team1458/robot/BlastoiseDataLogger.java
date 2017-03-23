package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.TurtleMaths;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Logs robot data throughout the match
 *
 * @author asinghani
 */
public class BlastoiseDataLogger {
	/**
	 * Data to be logged
	 */
	private static FlightStick rightStick;
	private static FlightStick leftStick;
	private static BlastoiseController controller;

	private static TurtleTalonSRXCAN left1;
	private static TurtleTalonSRXCAN left2;
	private static TurtleTalonSRXCAN left3;

	private static TurtleTalonSRXCAN right1;
	private static TurtleTalonSRXCAN right2;
	private static TurtleTalonSRXCAN right3;

	private static TurtleTalonSRXCAN intake;
	private static TurtleTalonSRXCAN climber;
	private static TurtleTalonSRXCAN agitator;

	private static TurtleTalonSRXCAN leftShooter;
	private static TurtleTalonSRXCAN rightShooter;

	private static TurtleNavX navX;
	private static TurtleDistanceSensor lidar;

	private static TurtleDistanceSensor leftEncoder;
	private static TurtleDistanceSensor rightEncoder;

	private static TurtleHallSensor leftHall;
	private static TurtleHallSensor rightHall;
	
	private static PowerDistributionPanel pdp;
	private static DriverStation driverStation;

	private static File logFile;
	private static PrintWriter writer;

	private static boolean fail = true;

	private static final DecimalFormat POINT_TWO = new DecimalFormat(".##");

	public static void setup(TurtleTalonSRXCAN left1, TurtleTalonSRXCAN left2, TurtleTalonSRXCAN left3, TurtleTalonSRXCAN right1, TurtleTalonSRXCAN right2, TurtleTalonSRXCAN right3, TurtleTalonSRXCAN intake, TurtleTalonSRXCAN climber, TurtleTalonSRXCAN agitator, TurtleTalonSRXCAN leftShooter, TurtleTalonSRXCAN rightShooter, TurtleNavX navX, TurtleDistanceSensor lidar, TurtleDistanceSensor leftEncoder, TurtleDistanceSensor rightEncoder, TurtleHallSensor leftHall, TurtleHallSensor rightHall) {
		BlastoiseDataLogger.left1 = left1;
		BlastoiseDataLogger.left2 = left2;
		BlastoiseDataLogger.left3 = left3;
		BlastoiseDataLogger.right1 = right1;
		BlastoiseDataLogger.right2 = right2;
		BlastoiseDataLogger.right3 = right3;
		BlastoiseDataLogger.intake = intake;
		BlastoiseDataLogger.climber = climber;
		BlastoiseDataLogger.agitator = agitator;
		BlastoiseDataLogger.leftShooter = leftShooter;
		BlastoiseDataLogger.rightShooter = rightShooter;
		BlastoiseDataLogger.navX = navX;
		BlastoiseDataLogger.lidar = lidar;
		BlastoiseDataLogger.leftEncoder = leftEncoder;
		BlastoiseDataLogger.rightEncoder = rightEncoder;
		BlastoiseDataLogger.leftHall = leftHall;
		BlastoiseDataLogger.rightHall = rightHall;

		// Instantiate everything
		BlastoiseDataLogger.driverStation = DriverStation.getInstance();
		BlastoiseDataLogger.pdp = new PowerDistributionPanel();

		BlastoiseDataLogger.rightStick = new FlightStick(Constants.DriverStation.UsbPorts.RIGHT_STICK);
		BlastoiseDataLogger.leftStick = new FlightStick(Constants.DriverStation.UsbPorts.LEFT_STICK);
		BlastoiseDataLogger.controller = new BlastoiseController(Constants.DriverStation.UsbPorts.ARDUINO_CONTROLLER);

		String path;
		if(driverStation.isFMSAttached()) {
			path = Constants.LOG_PATH_FMS;
		} else {
			path = Constants.LOG_PATH_NO_FMS;
		}
		DateFormat dateFormat = new SimpleDateFormat("MM-dd_HH:mm:ss");
		path += "log"+dateFormat.format(new Date())+".txt";

		logFile = new File(path);
		try {
			if(!logFile.createNewFile()) {
				throw new Exception("File already exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail = true;
			return;
		}

		try {
			writer = new PrintWriter(new FileOutputStream(logFile));
		} catch (Exception e) {
			e.printStackTrace();
			fail = true;
			return;
		}

		fail = false;
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				log();
			}
			
		}, 500, 500);
	}

	private static void log() {
		if(fail) return;

		StringBuilder line = new StringBuilder();

		line.append(getRobotState());  line.append(","); // Robot State: 0 = Disabled, 1 = Auto, 2 = Teleop, 3 = Test
		line.append((int) driverStation.getMatchTime());  line.append(",");

		// DS data
		line.append(toInt(driverStation.isFMSAttached()));  line.append(",");
		line.append(toInt(driverStation.isDSAttached()));  line.append(",");
		line.append(toInt(driverStation.isBrownedOut()));  line.append(",");

		// PDP
		line.append(POINT_TWO.format(pdp.getVoltage()));  line.append(",");
		line.append(POINT_TWO.format(pdp.getTotalCurrent()));  line.append(",");
		line.append(POINT_TWO.format(pdp.getTemperature()));  line.append(",");

		for(int channel = 0; channel < 16; channel++) {
			line.append(POINT_TWO.format(pdp.getCurrent(channel)));  line.append(",");
		}

		appendFlightStickData(line, rightStick);
		appendFlightStickData(line, leftStick);
		appendArduinoData(line, controller);

		line.append(POINT_TWO.format(navX.getVelocityX().getValue()));  line.append(",");
		line.append(POINT_TWO.format(navX.getVelocityY().getValue()));  line.append(",");
		line.append(POINT_TWO.format(navX.getVelocityZ().getValue()));  line.append(",");

		line.append(POINT_TWO.format(navX.getWorldLinearAccelX()));  line.append(",");
		line.append(POINT_TWO.format(navX.getWorldLinearAccelY()));  line.append(",");
		line.append(POINT_TWO.format(navX.getWorldLinearAccelZ()));  line.append(",");

		line.append(POINT_TWO.format(navX.getPitchAxis().getRotation().getDegrees()));  line.append(",");
		line.append(POINT_TWO.format(navX.getRollAxis().getRotation().getDegrees()));  line.append(",");
		line.append(POINT_TWO.format(navX.getYawAxis().getRotation().getDegrees()));  line.append(",");

		line.append(POINT_TWO.format(navX.getTempC()));  line.append(",");
		line.append(POINT_TWO.format(navX.getCompassHeading().getDegrees()));  line.append(",");

		line.append(POINT_TWO.format(lidar.getDistance()));  line.append(",");
		
		logTalonData(line, left1);
		logTalonData(line, left2);
		logTalonData(line, left3);

		logTalonData(line, right1);
		logTalonData(line, right2);
		logTalonData(line, right3);

		logTalonData(line, intake);
		logTalonData(line, climber);
		logTalonData(line, agitator);
		logTalonData(line, leftShooter);
		logTalonData(line, rightShooter);

		line.append(POINT_TWO.format(leftEncoder.getDistance()));  line.append(",");
		line.append(POINT_TWO.format(rightEncoder.getDistance()));  line.append(",");
		

		line.append(POINT_TWO.format(leftHall.getRPM()));  line.append(",");
		line.append(POINT_TWO.format(rightHall.getRPM()));

		try {
			writer.println(line.toString());
			writer.flush();
			System.out.println("logging line");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void logTalonData(StringBuilder line, TurtleTalonSRXCAN motor) {
		line.append(POINT_TWO.format(motor.get().getValue()));  line.append(",");
		line.append(POINT_TWO.format(motor.getRaw()));  line.append(",");

		line.append(POINT_TWO.format(motor.getOutputVoltage()));  line.append(",");
	}

	private static void appendArduinoData(StringBuilder line, BlastoiseController controller) {
		line.append(controller.getClimber().get()); line.append(",");
		line.append(controller.getFeeder().get()); line.append(",");
		line.append(controller.getPanic().get()); line.append(",");
		line.append(controller.getShooterToggle().get()); line.append(",");
		line.append(controller.getShooterAutoManual().get()); line.append(",");

		line.append(intakeAnalogToDigital(controller.getIntake().get())); line.append(",");
		line.append(shooterAnalogToDigital(controller.getShooterSpeed().get())); line.append(",");
	}

	private static void appendFlightStickData(StringBuilder string, FlightStick flightStick) {
		string.append(POINT_TWO.format(flightStick.getRawAxis(FlightStick.FlightAxis.PITCH)));  string.append(",");
		string.append(POINT_TWO.format(flightStick.getRawAxis(FlightStick.FlightAxis.ROLL)));  string.append(",");
		string.append(POINT_TWO.format(flightStick.getRawAxis(FlightStick.FlightAxis.YAW)));  string.append(",");
		string.append(POINT_TWO.format(flightStick.getRawAxis(FlightStick.FlightAxis.THROTTLE)));  string.append(",");
		string.append(toInt(flightStick.getRawButton(FlightStick.FlightButton.TRIGGER)));  string.append(",");
		string.append(toInt(flightStick.getRawButton(FlightStick.FlightButton.TWO)));  string.append(",");
		string.append(toInt(flightStick.getRawButton(FlightStick.FlightButton.THREE)));  string.append(",");
		string.append(toInt(flightStick.getRawButton(FlightStick.FlightButton.FOUR)));  string.append(",");
		string.append(toInt(flightStick.getRawButton(FlightStick.FlightButton.FIVE)));  string.append(",");
		string.append(toInt(flightStick.getRawButton(FlightStick.FlightButton.SIX)));  string.append(",");
	}

	private static int intakeAnalogToDigital(double value) {
		if(TurtleMaths.absDiff(value, -1) < 0.5) {
			return 2;
		} else if(TurtleMaths.absDiff(value, 1) < 0.5) {
			return 1;
		} else {
			return 0;
		}
	}

	private static int shooterAnalogToDigital(double value) {
		return (int) Math.round(TurtleMaths.shift(value, -1, 1, 0, 11));
	}

	private static int getRobotState() {
		if(RobotState.isDisabled()){
			return 0;
		} else if (RobotState.isAutonomous()) {
			return 1;
		} else if (RobotState.isOperatorControl()) {
			return 2;
		} else if (RobotState.isTest()) {
			return 3;
		}
		return -1;
	}

	private static int toInt(boolean value) {
		return value ? 1 : 0;
	}
}
