package com.simpleflightcomputer;

public class FlightVector {
	private double speed;
	private double direction;
	
	public FlightVector(double speed, double direction){
		this.speed = speed;
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public double getDirection() {
		return direction;
	}
}
