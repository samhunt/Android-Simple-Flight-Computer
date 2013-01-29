package com.simpleflightcomputer;

import java.text.DecimalFormat;

import android.widget.EditText;

public class FlightDistanceSpeedTime {
	private String type;

	public FlightDistanceSpeedTime(){
		
	}
	
	public double getDistanceSpeedTime(String distance, String speed, String time, 
			String distanceType, String speedType, String timeType){
		if(!distance.matches("") && !speed.matches("")) {//distance and speed are the two.
			FlightDistance fd = new FlightDistance(Double.parseDouble(distance),distanceType);
			FlightSpeed fs = new FlightSpeed(Double.parseDouble(speed),speedType);
			FlightTime ft = calculateTime(fd, fs); 
			type = "time";
			return roundThreeDecimals(ft.getTime(timeType));
    	}else if(!distance.matches("") && !time.matches("")) {// distance and time are the two.
			FlightDistance fd = new FlightDistance(Double.parseDouble(distance),distanceType);
			FlightTime ft = new FlightTime(Double.parseDouble(time),timeType);
			FlightSpeed fs = calculateSpeed(fd,ft);
			type = "speed";
			return roundThreeDecimals(fs.getSpeed(speedType));
    	}else if(!speed.matches("") && !time.matches("")){// speed and time are the two.
			FlightSpeed fs = new FlightSpeed(Double.parseDouble(speed),speedType);
			FlightTime ft = new FlightTime(Double.parseDouble(time),timeType);
			FlightDistance fd = calculateDistance(fs,ft);
			type = "distance";
			return roundThreeDecimals(fd.getDistance(distanceType));
    	}else{ // Something is missing
    		return -1;
    	}
	}
	
	public String getType(){
		return type;
	}
	
	private FlightDistance calculateDistance(FlightSpeed fs, FlightTime ft){
		double speed = fs.getKnots();
		double time = ft.getHours();
		double distance = speed*time;
		
		return new FlightDistance(distance);
	}
	
	private FlightSpeed calculateSpeed(FlightDistance fd, FlightTime ft){
		double distance = fd.getNauticalMiles();
		double time = ft.getHours();
		double speed = distance/time;
		
		return new FlightSpeed(speed);
	}

	private FlightTime calculateTime(FlightDistance fd, FlightSpeed fs){
		double distance = fd.getNauticalMiles(); 
		double speed = fs.getKnots();
		double time = distance/speed;
		return new FlightTime(time);
	
	}
	
	private double roundThreeDecimals(double d) {
        DecimalFormat dForm = new DecimalFormat("#.###");
        return Double.valueOf(dForm.format(d));
	}
}
