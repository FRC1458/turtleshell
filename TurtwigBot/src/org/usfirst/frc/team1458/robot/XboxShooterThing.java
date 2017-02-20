package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.DigitalInput;
import com.team1458.turtleshell2.input.TurtleJoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Timer;
import java.util.TimerTask;

public class XboxShooterThing implements DigitalInput {

	int value = 70; // between 0 and 11 inclusive
	
	TurtleJoystickButton low;
	TurtleJoystickButton high;

	public XboxShooterThing(TurtleJoystickButton low, TurtleJoystickButton high) {
		this.low = low;
		this.high = high;

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 50, 50);
	}

	public void update() {
		if(low.getDown()) {
			value--;
			if(value == -1) value = 0;
		} else if (high.getDown()) {
			value++;
			if(value == 101) value = 100;
		}
		SmartDashboard.putNumber("XboxShooterNumberValueThingy", value);
	}
	
	@Override
	public int get() {
		return value;
	}
}
