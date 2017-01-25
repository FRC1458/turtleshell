package com.team1458.turtleshell2.interfaces.input;

import com.team1458.turtleshell2.implementations.input.InputObject;

import java.util.Map;

/**
 * Mapping of joystick and button inputs to Strings
 * @author asinghani
 */
public interface InputMapping {
	Map<String, InputObject> getMapping();
}
