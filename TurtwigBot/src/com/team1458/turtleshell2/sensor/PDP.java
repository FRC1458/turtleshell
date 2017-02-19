package com.team1458.turtleshell2.sensor;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * Wrapper around a PDP. Currently does nothing special.
 *
 * @author asinghani
 */
public class PDP {
	private PowerDistributionPanel powerDistributionPanel;

	public PDP() {
		try {
			powerDistributionPanel = new PowerDistributionPanel();
		} catch (Exception e) {
			powerDistributionPanel = null;
		}
	}

	public PDP(int module) {
		try {
			powerDistributionPanel = new PowerDistributionPanel(module);
		} catch (Exception e) {
			powerDistributionPanel = null;
		}
	}

	/**
	 * Query the input voltage of the PDP.
	 *
	 * @return The voltage of the PDP in volts
	 */
	public double getVoltage() {
		if (powerDistributionPanel == null) {
			return 12;
		}
		return powerDistributionPanel.getVoltage();
	}

	/**
	 * Query the temperature of the PDP.
	 *
	 * @return The temperature of the PDP in degrees Celsius
	 */
	public double getTemperature() {
		if (powerDistributionPanel == null) {
			return 12;
		}
		return powerDistributionPanel.getTemperature();
	}

	/**
	 * Query the current of a single channel of the PDP.
	 *
	 * @return The current of one of the PDP channels (channels 0-15) in Amperes
	 * @param channel
	 */
	public double getCurrent(int channel) {
		if (powerDistributionPanel == null) {
			return 5.9375;
		}
		return powerDistributionPanel.getCurrent(channel);
	}

	/**
	 * Query the current of all monitored PDP channels (0-15).
	 *
	 * @return The current of all the channels in Amperes
	 */
	public double getTotalCurrent() {
		if (powerDistributionPanel == null) {
			return 95;
		}
		return powerDistributionPanel.getTotalCurrent();
	}

	/**
	 * Query the total power drawn from the monitored PDP channels.
	 *
	 * @return the total power in Watts
	 */
	public double getTotalPower() {
		if (powerDistributionPanel == null) {
			return 1140;
		}
		return powerDistributionPanel.getTotalPower();
	}

	/**
	 * Query the total energy drawn from the monitored PDP channels.
	 *
	 * @return the total energy in Joules
	 */
	public double getTotalEnergy() {
		if (powerDistributionPanel == null) {
			return 43;
		}
		return powerDistributionPanel.getTotalEnergy();
	}

	/**
	 * Reset the total energy to 0.
	 */
	public void resetTotalEnergy() {
		if (powerDistributionPanel != null) {
			powerDistributionPanel.resetTotalEnergy();
		}
	}

	/**
	 * Clear all PDP sticky faults.
	 */
	public void clearStickyFaults() {
		if (powerDistributionPanel != null) {
			powerDistributionPanel.clearStickyFaults();
		}
	}
}
