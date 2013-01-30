package com.simpleflightcomputer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DistSpeedTimeActivity extends Activity {

    private static final boolean TOGGLE_ON_CLICK = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dist_speed_time);
		
		final View contentView = findViewById(R.id.fullscreen_content_controls);
		setMain();

		contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	InputMethodManager imm = (InputMethodManager)getSystemService(
            		      Context.INPUT_METHOD_SERVICE);
                if (TOGGLE_ON_CLICK) {
                	imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
                } else {
                	imm.showSoftInputFromInputMethod(contentView.getWindowToken(), 0);
                }
            }
        });
		
		Button clrDistSpeed = (Button)findViewById(R.id.clear_distance_speed);
		Button calDistSpeed = (Button)findViewById(R.id.calculate_distance_speed);

        clrDistSpeed.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clearDistanceSpeed(v);
            	InputMethodManager imm = (InputMethodManager)getSystemService(
            		      Context.INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
			}
        	
        });
        
        calDistSpeed.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            	InputMethodManager imm = (InputMethodManager)getSystemService(
            		      Context.INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
				calculateDistanceSpeed(v);
			}
        	
        });
		
        Button main_menu = (Button)findViewById(R.id.main_menu);
		main_menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
	}


	private void clearDistanceSpeed(View v) {
		((EditText)findViewById(R.id.time_edit_text)).setText("");
		((EditText)findViewById(R.id.distance_edit_text)).setText("");
		((EditText)findViewById(R.id.speed_edit_text)).setText("");
	}

    
    private void calculateDistanceSpeed(View v) {
    	//Log.d("Calculate", "DISTANCE SPEED TIME");
    	// get distancevalue
    	// get speedvalue
    	// get timevalue
    	String distance = ((EditText)findViewById(R.id.distance_edit_text)).getText().toString();
    	String speed = ((EditText)findViewById(R.id.speed_edit_text)).getText().toString();
    	String time = ((EditText)findViewById(R.id.time_edit_text)).getText().toString();
    	String distanceType = ((Spinner)findViewById(R.id.distance_type)).getSelectedItem().toString(); 
    	String speedType = ((Spinner)findViewById(R.id.speed_type)).getSelectedItem().toString(); 
    	String timeType = ((Spinner)findViewById(R.id.time_type)).getSelectedItem().toString();
    	// convert d into nautical miles.
    	// convert s into knots
    	// convert t into minutes.
    	FlightDistanceSpeedTime fdst = new FlightDistanceSpeedTime();
    	Double result = fdst.getDistanceSpeedTime(distance, speed, time, distanceType, speedType, timeType);
    	String type = fdst.getType();
    	if(result != -1){
	    	if(type == "distance"){
	    		((EditText)findViewById(R.id.distance_edit_text)).setText(result.toString());
	    	}else if (type == "speed"){
	    		((EditText)findViewById(R.id.speed_edit_text)).setText(result.toString());
	    	}else if (type == "time"){
	    		((EditText)findViewById(R.id.time_edit_text)).setText(result.toString());
	    	}
    	}else{
	    	CharSequence text = "You must fill out 2 of the 3 variables.";
	    	Toast t = Toast.makeText(DistSpeedTimeActivity.this, text, Toast.LENGTH_LONG);
	    	t.show();
    	}
	}
	
	private void setMain() {
		FrameLayout content = (FrameLayout)findViewById(R.id.mainContent);
		float dip = getResources().getDisplayMetrics().density;
		content.removeAllViews();
		
		//setup the content to look how I want it to look.
		
		/*
		TextView titleText = ActivityMethods.newTextView(DistSpeedTimeActivity.this, "Distance/Speed/Time");
		titleText.setLayoutParams(new LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT,
	            ViewGroup.LayoutParams.WRAP_CONTENT));
		titleText.setGravity(Gravity.CENTER);
		titleText.setTextAppearance(DistSpeedTimeActivity.this,
				android.R.style.TextAppearance_Large);
		*/
		LinearLayout wrapper = new LinearLayout(DistSpeedTimeActivity.this);
		wrapper.setLayoutParams(new LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT,
	            ViewGroup.LayoutParams.WRAP_CONTENT));
		wrapper.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout distanceLayout = ActivityMethods.newHorizontalLinearLayout(DistSpeedTimeActivity.this);
		LinearLayout speedLayout = ActivityMethods.newHorizontalLinearLayout(DistSpeedTimeActivity.this);
		LinearLayout timeLayout = ActivityMethods.newHorizontalLinearLayout(DistSpeedTimeActivity.this);
		LinearLayout emptyLayout = ActivityMethods.newHorizontalLinearLayout(DistSpeedTimeActivity.this);
		LinearLayout calculateLayout;
		
		//TextView distanceTextView = newTextView("Distance", 4, android.R.style.TextAppearance_Large);
		ImageView  distanceImageView = new ImageView(DistSpeedTimeActivity.this);
		distanceImageView.setImageResource(R.drawable.ruler);
		distanceImageView.setPadding((int)(15*dip), 0, (int)(15*dip), 0);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(65*dip), (int)(65*dip));
		distanceImageView.setLayoutParams(layoutParams);
		//Log.d("layout params", ""+distanceImageView.getLayoutParams());
		//TextView speedTextView = newTextView("Speed", 4, android.R.style.TextAppearance_Large);
		ImageView  speedImageView = new ImageView(DistSpeedTimeActivity.this);
		speedImageView.setImageResource(R.drawable.speed);
		speedImageView.setPadding((int)(15*dip), 0, (int)(15*dip), 0);
		speedImageView.setLayoutParams(layoutParams);
		ImageView  timeImageView = new ImageView(DistSpeedTimeActivity.this);
		timeImageView.setImageResource(R.drawable.time);
		timeImageView.setPadding((int)(15*dip), 0, (int)(15*dip), 0);
		timeImageView.setLayoutParams(layoutParams);
		//TextView timeTextView = newTextView("Time", 4 , android.R.style.TextAppearance_Large);

		EditText distanceEditText = ActivityMethods.newEditText(DistSpeedTimeActivity.this, 
				R.id.distance_edit_text, 7, "Distance");
		//distanceEditText.setb
		EditText speedEditText = ActivityMethods.newEditText(DistSpeedTimeActivity.this, 
				R.id.speed_edit_text, 7, "Speed");
		EditText timeEditText = ActivityMethods.newEditText(DistSpeedTimeActivity.this, 
				R.id.time_edit_text, 7, "Time");
		
		Spinner distanceSpinner = ActivityMethods.newSpinner(DistSpeedTimeActivity.this, 
				FlightDistance.VARIABLES, R.id.distance_type);
		distanceSpinner.setSelection(1);
		Spinner speedSpinner = ActivityMethods.newSpinner(DistSpeedTimeActivity.this, 
				FlightSpeed.VARIABLES,
				R.id.speed_type);
		speedSpinner.setSelection(1);
		Spinner timeSpinner = ActivityMethods.newSpinner(DistSpeedTimeActivity.this, 
				FlightTime.VARIABLES, R.id.time_type);
		timeSpinner.setSelection(2);
		
		distanceLayout.addView(distanceImageView);
		distanceLayout.addView(distanceEditText);
		distanceLayout.addView(distanceSpinner);
		
		speedLayout.addView(speedImageView);
		speedLayout.addView(speedEditText);
		speedLayout.addView(speedSpinner);

		timeLayout.addView(timeImageView);
		timeLayout.addView(timeEditText);
		timeLayout.addView(timeSpinner);
		
		emptyLayout.setPadding(0, (int)(25*dip), 0, 0);
		
		calculateLayout = ActivityMethods.calcLayout(DistSpeedTimeActivity.this, 
				R.id.clear_distance_speed, R.id.calculate_distance_speed);
		
		//wrapper.addView(titleText);
		wrapper.addView(distanceLayout);
		wrapper.addView(speedLayout);
		wrapper.addView(timeLayout);
		wrapper.addView(emptyLayout);
		wrapper.addView(calculateLayout);
		
		content.addView(wrapper);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dist_speed_time, menu);
		return true;
	}

}
