package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.InputObject;
import com.team1458.turtleshell2.interfaces.input.InputMapping;

import java.util.Map;

/**
 * @author asinghani
 */
public class BlastoiseInputMapping implements InputMapping {

	Map<String, InputObject> mapping;

	public BlastoiseInputMapping() {
		mapping.put("LEFT_JOYSTICK", new InputObject());
	}

	@Override
	public Map<String, InputObject> getMapping() {
		return mapping;
	}
}
