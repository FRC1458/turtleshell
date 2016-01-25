package com.team1458.turtleshell;

public class TurtlePDD2Constants implements TurtlePIDConstants {
	private final double kP;
	private final double kD;
	private final double kD2;
	
	
	public TurtlePDD2Constants(double kP, double kD, double kD2) {
		this.kP=kP;
		this.kD=kD;
		this.kD2=kD2;
	}
	
	@Override
	public double getKP() {
		return kP;
	}

	@Override
	public double getKI() {
		return 0;
	}

	@Override
	public double getKD() {
		return kD;
	}

	@Override
	public double getKD2() {
		return kD2;
	}

}
