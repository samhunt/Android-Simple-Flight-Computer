package com.simpleflightcomputer;

import java.text.DecimalFormat;

import android.util.Log;

public class FlightWindTriangle {
	private String trueAirHeading,trueAirSpeed, windDirection, windSpeed, groundTrack, groundSpeed;
	private FlightVector[] fv = new FlightVector[3];
	private boolean solve = false;
	
	public FlightWindTriangle(String tah, String tas, String wd, String ws, String gt, String gs){
		if(!tah.equals("")){
			trueAirHeading = getProperAngle(Double.parseDouble(tah)).toString();
		}else{
			trueAirHeading = tah;
		}
			
		trueAirSpeed = tas;
		if(!wd.equals("")){
			windDirection = getProperAngle(Double.parseDouble(wd)).toString();
		}else{
			windDirection = wd;
		}
		windSpeed = ws;
		if(!gt.equals("")){
			groundTrack = getProperAngle(Double.parseDouble(gt)).toString();
		}else{
			groundTrack = gt;
		}
		
		groundSpeed = gs;
	}

	public void solve(){
		if(!trueAirHeading.equals("") && !trueAirSpeed.equals("") 
				&& !groundTrack.equals("") && !groundSpeed.equals("")){
			fv[0] = new FlightVector(Double.parseDouble(trueAirSpeed), Double.parseDouble(trueAirHeading));
			
			double wd = solveWindDirection();
			double ws = solveWindSpeed();
			fv[1] = new FlightVector(ws, wd);

			fv[2] = new FlightVector(Double.parseDouble(groundSpeed), Double.parseDouble(groundTrack));
			solve = true;
		}else if(!trueAirHeading.equals("") && !trueAirSpeed.equals("")
				&& !windDirection.equals("") && !windSpeed.equals("")){
			fv[0] = new FlightVector(Double.parseDouble(trueAirSpeed), Double.parseDouble(trueAirHeading));
			fv[1] = new FlightVector(Double.parseDouble(windSpeed), Double.parseDouble(windDirection));
			
			double gt = solveGroundTrack();
			double gs = solveGroundSpeed();
			fv[2] = new FlightVector(gs,gt);
			solve = true;
		}else if(!groundTrack.equals("") && !groundSpeed.equals("")
				&& !windDirection.equals("") && !windSpeed.equals("")){
			double tah = solveTrueAirHeading();
			double tas = solveTrueAirSpeed();
			
			fv[0] = new FlightVector(tas, tah);
			fv[1] = new FlightVector(Double.parseDouble(windSpeed), Double.parseDouble(windDirection));
			fv[2] = new FlightVector(Double.parseDouble(groundSpeed), Double.parseDouble(groundTrack));
			solve = true;
		}
	}
	
	public boolean isSolved(){
		return solve;
	}
	
	public FlightVector[] getFlightVector(){
		return fv;
	}
	
	
	private double solveTrueAirHeading(){
		double ws = Double.parseDouble(windSpeed);
		double wd = Math.toRadians(Double.parseDouble(windDirection));
		double gs = Double.parseDouble(groundSpeed);
		double gt = Math.toRadians(Double.parseDouble(groundTrack));
		
		/*
		double y = gs*Math.sin(gt-wd);
		double x = ws-gs*Math.cos(gt-wd);
		
		double tah = wd + Math.atan2(y, x);
		tah = Math.toDegrees(tah);
		*/
		return roundThreeDecimals(-1);
	}
	
	private double solveTrueAirSpeed(){
		return -1;
	}
	
	private double solveWindDirection(){
		double tas = Double.parseDouble(trueAirSpeed);
		double tah = Math.toRadians(Double.parseDouble(trueAirHeading));
		double gs = Double.parseDouble(groundSpeed);
		double gt = Math.toRadians(Double.parseDouble(groundTrack));
		
		double y = tas*Math.sin(tah-gt);
		double x = tas*Math.cos(tah-gt)-gs;
		
		double windDirection = gt + Math.atan2(y, x);
		windDirection += Math.PI;
		if(windDirection < 0)
			windDirection = windDirection+2*Math.PI;
		if(windDirection > 2*Math.PI)
			windDirection = windDirection-2*Math.PI;
		windDirection = Math.toDegrees(windDirection);
		return roundThreeDecimals(windDirection);
	}
	
	private double solveWindSpeed(){
		double tas = Double.parseDouble(trueAirSpeed);
		double tah = Math.toRadians(Double.parseDouble(trueAirHeading));
		double gs = Double.parseDouble(groundSpeed);
		double gt = Math.toRadians(Double.parseDouble(groundTrack));
		
		double working = Math.pow((tas-gs), 2);
		working += 4*tas*gs*Math.pow(Math.sin((tah-gt)/2),2);
		working = Math.sqrt(working);
		return roundThreeDecimals(working);
	}
	
	
	private double solveWindCorrectionAngle(){
		double tas = Double.parseDouble(trueAirSpeed);
		double tah = Math.toRadians(Double.parseDouble(trueAirHeading));
		double ws = Double.parseDouble(windSpeed);
		double wd = Math.toRadians(Double.parseDouble(windDirection));
		wd = wd-Math.PI;
		if(wd < 0)
			wd += Math.PI*2;
		
		double y = ws*Math.sin(tah-wd);
		double x = tas-ws*Math.cos(tah-wd);
		double working = Math.atan2(y, x);
		return working;
	}
	
	
	private double solveGroundTrack(){
		double tah = Math.toRadians(Double.parseDouble(trueAirHeading));
		double wca = solveWindCorrectionAngle();
		
		double working = (tah+wca)%(2*Math.PI); //makes sure that it is not greater than 2pi
		working = Math.toDegrees(working);
		return roundThreeDecimals(working);
	}
	
	private double solveGroundSpeed(){
		double tas = Double.parseDouble(trueAirSpeed);
		double tah = Math.toRadians(Double.parseDouble(trueAirHeading));
		double ws = Double.parseDouble(windSpeed);
		double wd = Math.toRadians(Double.parseDouble(windDirection));
		wd = wd-Math.PI;
		if(wd < 0)
			wd += Math.PI*2;
		
		
		double working = Math.sqrt(Math.pow(ws,2)+Math.pow(tas, 2) - 2*ws*tas*Math.cos((tah-wd)));
		return roundThreeDecimals(working);
	}
	
	private double roundThreeDecimals(double d) {
        DecimalFormat dForm = new DecimalFormat("#.###");
        return Double.valueOf(dForm.format(d));
	}
	
	private Double getProperAngle(double angle){
		while(angle > 360){
			angle -= 360;
		}
		while(angle < 0){
			angle += 360;
		}
		return angle;
	}
	
}
