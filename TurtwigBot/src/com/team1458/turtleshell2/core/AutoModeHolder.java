package com.team1458.turtleshell2.core;

import java.util.ArrayList;

/**
 * Allows switching between multiple AutoModes
 *
 * @author asinghani
 */
public interface AutoModeHolder {
	public ArrayList<? extends AutoMode> getAutoModes();

	public void setSelectedAutoModeIndex(int index);

	public int getSelectedAutoModeIndex();
}
