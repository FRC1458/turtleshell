package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.JoystickAxis;
import com.team1458.turtleshell2.input.XboxController;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import java.util.ArrayList;

/**
 * Logs various pieces of data throughout the match
 * This is going to result in a whole lot of data, packaged into a JSON format
 *
 * @author asinghani
 */
public class BlastoiseDataLogger {
	private PowerDistributionPanel pdp;
	private ArrayList<String> events;

	public BlastoiseDataLogger() {
		pdp = new PowerDistributionPanel();
		// need to do the thing
	}

}
