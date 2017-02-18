package org.usfirst.frc.team1458.robot;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.team1458.turtleshell2.sensor.PDP;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Logs various pieces of data throughout the match
 * This is going to result in a whole lot of data, packaged into a JSON format
 *
 * NOTE - This class requires the jar minimal-json-0.9.4.jar in order to function
 * (this jar should be in the lib folder in this project)
 *
 * @author asinghani
 */
public class BlastoiseDataLogger {
	private PDP pdp;
	private ArrayList<IEvent> events;

	public BlastoiseDataLogger() {
		pdp = new PDP();
	}

	public void update() {

	}

	public void event(EventType type, String data) {
		events.add(new Event(type, data));
	}

	static class Update implements IEvent {
		private double matchTime;
		private double systemTime;

		private double leftDrive;
		private double rightDrive;

		private double leftShooterMotorValue;
		private double rightShooterMotorValue;

		private double leftShooterRPM;
		private double rightShooterRPM;

		private double intakeMotorValue;

		private double climberMotorValue;

		// MEM FORMATTED: free -m | awk 'NR==2{printf "Memory Usage: %s/%sMB (%.2f%%)\n", $3,$2,$3*100/$2 }'
		// MEM PERCENT: free -m | awk 'NR==2{printf "%.2f%%", $3*100/$2 }'
		// MEM USED: free -m | awk 'NR==2{printf "%s", $3}'
		// MEM TOTAL: free -m | awk 'NR==2{printf "%s", $2}'

		// CPU FORMATTED: top -bn1 | grep load | awk '{printf "CPU Load: %.2f\n", $(NF-2)}'
		// CPU RAW: top -bn1 | grep load | awk '{printf "%.2f", $(NF-2)}'

		private RobotState robotState;

		private double batteryVoltage;

		private double pdpTemperature;
		private double pdpTotalCurrent;
		private double pdpTotalPower;
		private double pdpVoltage;

		private double[] current = new double[16];

		public Update(PDP pdp) {
			this.batteryVoltage = DriverStation.getInstance().getBatteryVoltage();
			this.pdpTemperature = pdp.getTemperature();
			this.pdpTotalCurrent = pdp.getTotalCurrent();
			this.pdpTotalPower = pdp.getTotalPower();
			this.pdpVoltage = pdp.getVoltage();

			for(int channel = 0; channel <= 15; channel++) {
				current[channel] = pdp.getCurrent(channel);
			}

			this.robotState = getRobotState();

			try {
				this.matchTime = DriverStation.getInstance().getMatchTime();
				this.systemTime = Timer.getFPGATimestamp();
			} catch (Throwable e) {
				this.matchTime = -1;
				this.systemTime = (int) Math.round(System.currentTimeMillis() / 1000.0);
			}
		}

		@Override
		public String toJSON() {
			JsonObject object = Json.object();

			object.set("type", 1);
			object.set("matchTime", matchTime);
			object.set("systemTime", systemTime);

			return object.toString(Constants.LOGGER_PRETTY_PRINT ? WriterConfig.PRETTY_PRINT : WriterConfig.MINIMAL);
		}
	}

	/**
	 * This is a type of event for the robot.
	 * The associated int values MUST NOT be changed or else parsing code will break.
	 */
	public enum EventType {
		ROBOT_INIT(0), ROBOT_AUTO(1), ROBOT_TELEOP(2), ROBOT_TEST(3), ROBOT_DISABLED(4), // Robot Status
		SHOOTER_START(5), SHOOTER_STOP(6), CLIMBER_START(7), CLIMBER_STOP(8), // Shooter / Climber
		INTAKE_START(9), INTAKE_START_REVERSE(10), INTAKE_STOP(11), // Intake
		EXCEPTION(12); // Misc

		public final int val;
		EventType(int i) {
			val = i;
		}
	}

	/**
	 * ------------- CODE BELOW IS MOSTLY BOILERPLATE, DO NOT EDIT -------------
 	 */

	private static String runCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			output.append(reader.readLine());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	interface IEvent {
		String toJSON();
	}

	static class Event implements IEvent {
		private final EventType type;
		private final String data;
		private double matchTime;
		private double systemTime;

		private RobotState robotState;

		public Event(EventType type, String data) {
			this.type = type;
			this.data = data;

			this.robotState = getRobotState();

			try {
				this.matchTime = DriverStation.getInstance().getMatchTime();
				this.systemTime = Timer.getFPGATimestamp();
			} catch (Throwable e) {
				this.matchTime = -1;
				this.systemTime = (int) Math.round(System.currentTimeMillis() / 1000.0);
			}
		}

		public EventType getType() {
			return type;
		}

		public String getData() {
			return data;
		}

		@Override
		public String toJSON() {
			JsonObject object = Json.object();

			object.set("type", 0);
			object.set("eventType", type.val);
			object.set("data", data);
			object.set("matchTime", matchTime);
			object.set("systemTime", systemTime);

			return object.toString(Constants.LOGGER_PRETTY_PRINT ? WriterConfig.PRETTY_PRINT : WriterConfig.MINIMAL);
		}
	}

	public static RobotState getRobotState() {
		try {
			if(edu.wpi.first.wpilibj.RobotState.isDisabled()) {
				return RobotState.DISABLED;
			}

			if(edu.wpi.first.wpilibj.RobotState.isAutonomous()) {
				return RobotState.AUTO;
			}

			if(edu.wpi.first.wpilibj.RobotState.isOperatorControl()) {
				return RobotState.TELEOP;
			}

			if(edu.wpi.first.wpilibj.RobotState.isTest()) {
				return RobotState.TEST;
			}
		} catch (Exception e) {

		}

		return RobotState.DISABLED;
	}

	public enum RobotState {
		DISABLED, TELEOP, AUTO, TEST
	}
}
