package com.simpleflightcomputer;

public class FlightSpeed {
	public static final String[] VARIABLES = {"km/h", "Knots", "mph", "m/s"};
	private double speed;
	
	public FlightSpeed(double speed, String speedType) {
		if(speedType == FlightSpeed.VARIABLES[0]){ // kmh
			this.speed = speed/1.852;
		}else if(speedType == FlightSpeed.VARIABLES[1]){ // knots
			this.speed = speed;
		}else if(speedType == FlightSpeed.VARIABLES[2]){ // mph
			this.speed = speed*((double)1584000/1822831);
		}else if(speedType == FlightSpeed.VARIABLES[3]){ // ms
			this.speed = speed*((double)900/463);
		}
	}
	
	public FlightSpeed(double speed){
		this.speed = speed;
	}

	public double getKmh(){
		return speed*1.852;
	}
	
	public double getKnots(){
		return speed;
	}
	
	public double getMph(){
		return speed*((double)1822831/1584000);
	}
	
	public double getMs(){
		return speed*((double)463/900);
	}
	
	public double getSpeed(String speedType){
		if(speedType == FlightSpeed.VARIABLES[0]){ // kmh
			return getKmh(); 
		}else if(speedType == FlightSpeed.VARIABLES[1]){ // knots
			return getKnots(); 
		}else if(speedType == FlightSpeed.VARIABLES[2]){ // mph
			return getMph(); 
		}else if(speedType == FlightSpeed.VARIABLES[3]){ // ms
			return getMs(); 
		}
		return -1;
	}
}
