package com.simpleflightcomputer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class DrawVectors extends View{
	private double heading, trueAirSpeed, windDirection, windSpeed, course, groundSpeed;
	private double maxWidth, maxHeight;
	private float offset, yMiddle, xMiddle;
	private FloatPoint start, end; 
	
	public DrawVectors(Context context){
		super(context);
		maxWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}
	
	public DrawVectors(double heading, double tas, double wd, double ws, double course, double gs, Context context){
		super(context);
		this.heading = heading;
		trueAirSpeed = tas;
		windDirection = wd;
		windSpeed = ws;
		this.course = course;
		groundSpeed = gs;
		
		//Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		//Point size = new Point();
		//display.getSize(size);
		//maxWidth = size.x;
		maxWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		maxHeight = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		float dip = getResources().getDisplayMetrics().density;
		xMiddle = (float) (maxWidth/2);
		offset = (float) (maxHeight/8);
		yMiddle = offset +10;
		float len = offset*2;
		
		
		float middle = (float)(maxWidth /2);
		
		float[] pts = {0, 1, 2, 3};
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		canvas.drawLine(xMiddle, 10, xMiddle, len+10, paint);
		canvas.drawLine((xMiddle-offset), yMiddle, (xMiddle+offset), yMiddle, paint);
		paint.setTextSize(10*dip);
		canvas.drawText("N(0°)", xMiddle, 20, paint);
		canvas.drawText("E(90°)", xMiddle+offset, yMiddle, paint);
		canvas.drawText("W(180°)", xMiddle, yMiddle+offset, paint);
		canvas.drawText("S(270°)", xMiddle-offset, yMiddle, paint);
		
		float headingEndX, headingEndY, courseEndX, courseEndY, scale =1;
		
		if(trueAirSpeed > groundSpeed){ // scale everything around tas
			scale = (float) (offset/trueAirSpeed);
		}else{ //scale everything around gs
			scale = (float) (offset/groundSpeed); 
		}
		trueAirSpeed *= scale;
		groundSpeed *= scale;
		windSpeed *= scale;
		
		//draw heading
		start = drawLine(trueAirSpeed, heading, 1, canvas);
		
		//draw course
		end = drawLine(groundSpeed, course, 2, canvas);

		//draw wind
		drawLine(windSpeed, windDirection, 3, canvas);
		
		canvas.save();
	}
	
	
	private void arrow(FloatPoint start, FloatPoint end, double length, double angle, int amount, Canvas canvas, Paint paint){
		if(amount == 1){
			FloatPoint middle = start.getMiddle(end);
			double leftAngle = angle+45;
			double rightAngle = angle-45+180;
	
			float x = middle.x+findX(10, leftAngle+90);
			float y = middle.y+findY(10, leftAngle+90);
			canvas.drawLine(middle.x, middle.y, x, y, paint);
			x = middle.x+findX(10, rightAngle+90);
			y = middle.y+findY(10, rightAngle+90);
			canvas.drawLine(middle.x, middle.y, x, y, paint);
		} else if(amount == 2){
			FloatPoint vector = start.getVector(end);
			double len = Math.sqrt(Math.pow(vector.x,2)+Math.pow(vector.y,2));
			float arrowLen = (float) (length/10);
			FloatPoint unitVector = new FloatPoint((float)(vector.x/len), (float)(vector.y/len));
			FloatPoint middle = start.getMiddle(end);
			
			FloatPoint arrow1 = middle.add(new FloatPoint(unitVector.x*arrowLen, unitVector.y*arrowLen));
			FloatPoint arrow2 = middle.subtract(new FloatPoint(unitVector.x*arrowLen, unitVector.y*arrowLen));

			double leftAngle = angle+45;
			double rightAngle = angle-45+180;
			
			float x = arrow1.x+findX(10, leftAngle+90);
			float y = arrow1.y+findY(10, leftAngle+90);
			canvas.drawLine(arrow1.x, arrow1.y, x, y, paint);
			x = arrow1.x+findX(10, rightAngle+90);
			y = arrow1.y+findY(10, rightAngle+90);
			canvas.drawLine(arrow1.x, arrow1.y, x, y, paint);
			
			x = arrow2.x+findX(10, leftAngle+90);
			y = arrow2.y+findY(10, leftAngle+90);
			canvas.drawLine(arrow2.x, arrow2.y, x, y, paint);
			x = arrow2.x+findX(10, rightAngle+90);
			y = arrow2.y+findY(10, rightAngle+90);
			canvas.drawLine(arrow2.x, arrow2.y, x, y, paint);
			
		}else{
			FloatPoint vector = start.getVector(end);
			double len = Math.sqrt(Math.pow(vector.x,2)+Math.pow(vector.y,2));
			float arrowLen = (float) (length/10);
			FloatPoint unitVector = new FloatPoint((float)(vector.x/len), (float)(vector.y/len));
			FloatPoint middle = start.getMiddle(end);
			
			FloatPoint arrow1 = middle.add(new FloatPoint(unitVector.x*arrowLen, unitVector.y*arrowLen));
			FloatPoint arrow2 = middle.subtract(new FloatPoint(unitVector.x*arrowLen, unitVector.y*arrowLen));

			double leftAngle = angle+45;
			double rightAngle = angle-45+180;
			
			float x = arrow1.x+findX(10, leftAngle+90);
			float y = arrow1.y+findY(10, leftAngle+90);
			canvas.drawLine(arrow1.x, arrow1.y, x, y, paint);
			x = arrow1.x+findX(10, rightAngle+90);
			y = arrow1.y+findY(10, rightAngle+90);
			canvas.drawLine(arrow1.x, arrow1.y, x, y, paint);

			x = middle.x+findX(10, leftAngle+90);
			y = middle.y+findY(10, leftAngle+90);
			canvas.drawLine(middle.x, middle.y, x, y, paint);
			x = middle.x+findX(10, rightAngle+90);
			y = middle.y+findY(10, rightAngle+90);
			canvas.drawLine(middle.x, middle.y, x, y, paint);
			
			x = arrow2.x+findX(10, leftAngle+90);
			y = arrow2.y+findY(10, leftAngle+90);
			canvas.drawLine(arrow2.x, arrow2.y, x, y, paint);
			x = arrow2.x+findX(10, rightAngle+90);
			y = arrow2.y+findY(10, rightAngle+90);
			canvas.drawLine(arrow2.x, arrow2.y, x, y, paint);
			
		}
	}
	
	
	private FloatPoint drawLine(double length, double angle, int arrows, Canvas canvas){
		float x = 0,y = 0;
		FloatPoint p = new FloatPoint(x,y);
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		if(offset > 50){
			if(arrows == 1){ //heading
				paint.setColor(Color.WHITE);
				x = xMiddle+findX(length, angle);
				y = yMiddle+findY(length, angle);
				canvas.drawLine(xMiddle, yMiddle, x, y, paint);
				//find the point in the middle of (halfx, halfy, x, y);
				p = new FloatPoint(x,y);
				FloatPoint start = new FloatPoint(xMiddle, yMiddle);
				FloatPoint middle = start.getMiddle(p);
				//add and subtract 90 + 45° therefore being able to draw arrow in the middle of the line.
				arrow(start, p, length, angle, arrows, canvas, paint);
				
			}else if(arrows == 2){ //ground
				paint.setColor(Color.GREEN);
				x = xMiddle+findX(length, angle);
				y = yMiddle+findY(length, angle);
				canvas.drawLine(xMiddle, yMiddle, x, y, paint);
				FloatPoint start = new FloatPoint(xMiddle, yMiddle);
				p = new FloatPoint(x,y);
				arrow(start, p, length, angle, arrows, canvas, paint);
				
			}else{ // =3 wind
				paint.setColor(Color.RED);
				canvas.drawLine(start.x, start.y, end.x, end.y, paint);
				arrow(start, end, length, angle, arrows, canvas, paint);
	
				p = new FloatPoint(x,y);
			}
		}
		return p;
	}
	
	
	
	private float findX(double length, double angle){
		angle = angle - 90;
		float ans;
		ans =  (float) (Math.cos(Math.toRadians(angle))*length);
		return ans;
	}
	
	private float findY(double length, double angle){
		angle = angle -90;
		float ans;
		ans = (float) (Math.sin(Math.toRadians(angle))*length);
		return ans;
		
	}
	
	
	private class FloatPoint extends Point{
		float x, y;
		
		public FloatPoint(float x, float y){
			this.x = x; 
			this.y = y;
		}
		
		public FloatPoint subtract(FloatPoint f) {
			return new FloatPoint(this.x-f.x, this.y-f.y);
		}

		public FloatPoint add(FloatPoint f) {
			return new FloatPoint(this.x+f.x, this.y+f.y);
		}

		public FloatPoint getMiddle(FloatPoint end){
			float x = (this.x + end.x)/2;
			float y = (this.y + end.y)/2;
			return new FloatPoint(x,y);
		}
		
		public float getGradient(FloatPoint end){
			float run = end.x - this.x;
			float rise = end.y - this.y;
			return rise/run;
		}
		
		public FloatPoint getVector(FloatPoint end){
			return this.subtract(end);
		}
		
		public String toString(){
			return "X: " + x + " Y: " + y;
		}
		
	}
	
}
