package com.team1458.turtleshell2.interfaces.movement;
/**
 * Represents a movable object wih two states, extended and retracted
 * @author mehnadnerd
 *
 */
public interface TurtleSolenoid {
	public static enum SolenoidState {
		EXTENDED(true),RETRACTED(false);
		SolenoidState(boolean isExtended) {
			this.isExtended=isExtended;
		}
		private final boolean isExtended;
		public boolean isExtended() {
			return isExtended;
		}
	}
	
	public void setState(SolenoidState state);
	public SolenoidState getState();
	public default void extend() {
		this.setState(SolenoidState.EXTENDED);
	}
	public default void retract() {
		this.setState(SolenoidState.RETRACTED);
	}
}
