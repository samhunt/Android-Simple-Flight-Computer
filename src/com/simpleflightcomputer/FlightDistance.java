package com.simpleflightcomputer;

public class FlightDistance {
	public static final String[] VARIABLES = {"KM", "N. Miles", "Miles"};
	private double distance;
	
	public FlightDistance(double distance, String distanceType){
		if(distanceType == FlightDistance.VARIABLES[0]){
			this.distance = distance/1.852;
		}else if(distanceType == FlightDistance.VARIABLES[1]){
			this.distance = distance;
		}else if (distanceType == FlightDistance.VARIABLES[2]){
			double mToNmi = (double)1584000/1822831;
			this.distance = distance*mToNmi;
		}
	}
	
	public FlightDistance(double distance){
		this.distance = distance;
	}
	
	public double getKm(){
		return distance*1.852;
	}
	
	public double getNauticalMiles(){
		return distance;
	}
	
	public double getMiles(){
		double nmiToM = (double)1822831/1584000;
		return distance*nmiToM;
	}
	
	public double getDistance(String distanceType){
		if(distanceType == FlightDistance.VARIABLES[0]){
			return getKm();
		}else if(distanceType == FlightDistance.VARIABLES[1]){
			return getNauticalMiles();
		}else if (distanceType == FlightDistance.VARIABLES[2]){
			return getMiles();
		}
		return -1;
	}
}
