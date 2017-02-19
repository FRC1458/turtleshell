package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.SampleButtonInput;
import com.team1458.turtleshell2.input.TurtleJoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Timer;
import java.util.TimerTask;

public class XboxButtonToggleThingy extends SampleButtonInput {

	boolean value = false;
	boolean hasChanged = false;

	TurtleJoystickButton on;
	TurtleJoystickButton off;

	public XboxButtonToggleThingy(TurtleJoystickButton on, TurtleJoystickButton off) {
		this.on = on;
		this.off = off;

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 50, 50);
	}

	public void update() {
		if(on.getDown()) {
			value = true;
			hasChanged = true;
		} else if (off.getDown()) {
			value = false;
			hasChanged = true;
		}
		SmartDashboard.putBoolean("is in teleop", value);
	}

	public boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public boolean getButton() {
		return value;
	}
}
