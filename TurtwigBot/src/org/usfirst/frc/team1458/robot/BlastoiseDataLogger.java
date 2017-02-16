package org.usfirst.frc.team1458.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

import java.util.ArrayList;

/**
 * Logs various pieces of data throughout the match
 * This is going to result in a whole lot of data, packaged into a JSON format
 *
 * @author asinghani
 */
public class BlastoiseDataLogger {
	private PowerDistributionPanel pdp;
	private ArrayList<IEvent> events;

	public BlastoiseDataLogger() {
		pdp = new PowerDistributionPanel();
		// need to do the thing
	}

	public void update() {

	}

	class Update implements IEvent {
		private final double matchTime;
		private final double systemTime;

		public Update() {
			this.matchTime = DriverStation.getInstance().getMatchTime();
			this.systemTime = Timer.getFPGATimestamp();
		}

		@Override
		public String toString() {
			return String.format("{type: 1, matchTime: %f, systemTime: %f",
					matchTime, systemTime);
		}
	}

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

	// TODO Fix

	interface IEvent {
		String toString();
	}

	class Event implements IEvent {
		private final EventType type;
		private final String data;
		private final double matchTime;
		private final double systemTime;

		public Event(EventType type, String data) {
			this.type = type;
			this.data = data;
			this.matchTime = DriverStation.getInstance().getMatchTime();
			this.systemTime = Timer.getFPGATimestamp();
		}

		public EventType getType() {
			return type;
		}

		public String getData() {
			return data;
		}

		@Override
		public String toString() {
			return String.format("{type: 0, eventType: %f, data: \"%s\", matchTime: %f, systemTime: %f",
					type.val, data, matchTime, systemTime);
		}
	}
}
