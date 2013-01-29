package com.simpleflightcomputer;

public class FlightTime {
	public static final String[] VARIABLES = {"Seconds", "Minutes", "Hours"};
	public double time; //hours
	
	public FlightTime(double time, String timeType){
		if(timeType == FlightTime.VARIABLES[0]){
			this.time = time/3600;
		}else if(timeType == FlightTime.VARIABLES[1]){
			this.time = time/60;
		}else if(timeType == FlightTime.VARIABLES[2]){
			this.time = time;
		}
	}
	
	public FlightTime(double time){
		this.time = time;
	}
	
	public double getSeconds(){
		return time*3600;
	}
	
	public double getMinutes(){
		return time*60;
	}
	
	public double getHours(){
		return time;
	}
	
	public double getTime(String timeType){
		if(timeType == FlightTime.VARIABLES[0]){
			return getSeconds();
		}else if(timeType == FlightTime.VARIABLES[1]){
			return getMinutes();
		}else if(timeType == FlightTime.VARIABLES[2]){
			return getHours();
		}
		return -1; //error
	}
}
