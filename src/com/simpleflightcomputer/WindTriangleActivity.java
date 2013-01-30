package com.simpleflightcomputer;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WindTriangleActivity extends Activity {

    private static final boolean TOGGLE_ON_CLICK = true;
    private static int current_position = 1;
	private View contentView;
    
    private static boolean setup = true;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wind_triangle);
		contentView = findViewById(R.id.fullscreen_content_controls);
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
		
		Button main_menu = (Button)findViewById(R.id.main_menu);
		main_menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

    
	private void clearWindTriangle(View v) {
		((EditText)findViewById(R.id.true_heading_edit_text)).setText("");
		((EditText)findViewById(R.id.true_air_speed_edit_text)).setText("");
		((EditText)findViewById(R.id.wind_direction_edit_text)).setText("");
		((EditText)findViewById(R.id.wind_speed_edit_text)).setText("");
		((EditText)findViewById(R.id.ground_track_edit_text)).setText("");
		((EditText)findViewById(R.id.ground_speed_edit_text)).setText("");
		((LinearLayout)findViewById(R.id.draw_vector)).removeAllViews();
	}

	private void calculateWindTriangle(View v) {
		String tah = ((EditText)findViewById(R.id.true_heading_edit_text)).getText().toString();
		String tas = ((EditText)findViewById(R.id.true_air_speed_edit_text)).getText().toString();
		String wd = ((EditText)findViewById(R.id.wind_direction_edit_text)).getText().toString();
		String ws = ((EditText)findViewById(R.id.wind_speed_edit_text)).getText().toString();
		String gt = ((EditText)findViewById(R.id.ground_track_edit_text)).getText().toString();
		String gs = ((EditText)findViewById(R.id.ground_speed_edit_text)).getText().toString();
		FlightWindTriangle fwt = new FlightWindTriangle(tah, tas, wd, ws, gt, gs);
		fwt.solve();
		if(fwt.isSolved()){
			Toast.makeText(WindTriangleActivity.this, "Calculations completed successfully.",
					Toast.LENGTH_SHORT).show();
			FlightVector[] fva = fwt.getFlightVector();
			((EditText)findViewById(R.id.true_heading_edit_text)).setText(((Double)fva[0].getDirection()).toString());
			((EditText)findViewById(R.id.true_air_speed_edit_text)).setText(((Double)fva[0].getSpeed()).toString());
			((EditText)findViewById(R.id.wind_direction_edit_text)).setText(((Double)fva[1].getDirection()).toString());
			((EditText)findViewById(R.id.wind_speed_edit_text)).setText(((Double)fva[1].getSpeed()).toString());
			((EditText)findViewById(R.id.ground_track_edit_text)).setText(((Double)fva[2].getDirection()).toString());
			((EditText)findViewById(R.id.ground_speed_edit_text)).setText(((Double)fva[2].getSpeed()).toString());
			
			
			LinearLayout dvl = (LinearLayout) findViewById(R.id.draw_vector);
			DrawVectors dv = new DrawVectors(fva[0].getDirection(), fva[0].getSpeed(), fva[1].getDirection(), 
					fva[1].getSpeed(),fva[2].getDirection(), fva[2].getSpeed(),
					WindTriangleActivity.this);
			dvl.addView(dv);
		}else{
	    	CharSequence text = "Only solving for wind speed and wind direction, or "+
	    			" course and ground speed currently works.";
	    	Toast.makeText(WindTriangleActivity.this, text, Toast.LENGTH_LONG).show();
		}
	}
	
	private void setMain() {
		FrameLayout content = (FrameLayout)findViewById(R.id.mainContent);
		float dip = getResources().getDisplayMetrics().density;
		
		content.removeAllViews();
			
		Bundle extras = getIntent().getExtras();
		String solving = "cgs"; // solve for course ground speed by default;
		if(setup && extras != null) {
			solving = extras.getString("solving");
			if(solving.equals("cgs")){
				current_position = 1;
			}else{
				current_position = 0;
			}
			setup = false;
		}else{
			//current_position marks what we are solving.
			if(current_position == 0){
				solving = "wswd";
			}else{
				solving = "cgs";
			}
		}
		
		
		//setup the content to look how I want it to look.
		TextView titleText = ActivityMethods.newTextView(WindTriangleActivity.this, "Wind Triangle");
		titleText.setLayoutParams(new LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT,
	            ViewGroup.LayoutParams.WRAP_CONTENT));
		titleText.setGravity(Gravity.CENTER);
		titleText.setTextAppearance(WindTriangleActivity.this, android.R.style.TextAppearance_Large);
		
		LinearLayout wrapper = new LinearLayout(WindTriangleActivity.this);
		wrapper.setLayoutParams(new LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT,
	            ViewGroup.LayoutParams.WRAP_CONTENT));
		wrapper.setOrientation(LinearLayout.VERTICAL);

		LinearLayout changeSolvingLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this);
		changeSolvingLayout.setPadding(0, 0, 0, (int)(15*dip));
		LinearLayout thLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this); // true heading
		LinearLayout wdLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this); // wind direction
		LinearLayout gtLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this); // ground track
		LinearLayout emptyLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this);
		LinearLayout calculateLayout;
		LinearLayout drawVectorsLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this);
		LinearLayout solvingForLayout = ActivityMethods.newHorizontalLinearLayout(WindTriangleActivity.this);
		drawVectorsLayout.setId(R.id.draw_vector);
		
		TextView solveTextView = ActivityMethods.newTextView(WindTriangleActivity.this,
				"Solve for: ", 4, android.R.style.TextAppearance_Large);
		String[] solveArray = getResources().getStringArray(R.array.pick_calc_array);
		Spinner solveSpinner = ActivityMethods.newSpinner(WindTriangleActivity.this, 
				solveArray, R.id.change_solve_wind_triangle);
		solveSpinner.setSelection(current_position);
		solveSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				//Log.d("Position", ""+position);
				//Log.d("cur", ""+current_position);
				if(position != current_position){
					current_position = position;
					setMain();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		TextView thTextView = ActivityMethods.newTextView(WindTriangleActivity.this, "Heading Vec.", 
				6, android.R.style.TextAppearance_Small);
		thTextView.setTextColor(Color.WHITE);
		TextView thUnit = ActivityMethods.newTextView(WindTriangleActivity.this, "°");
		TextView tasUnit = ActivityMethods.newTextView(WindTriangleActivity.this, "KTS");
		TextView wdTextView = ActivityMethods.newTextView(WindTriangleActivity.this, "Wind Vec.", 
				6, android.R.style.TextAppearance_Small);
		wdTextView.setTextColor(Color.RED);
		TextView wdUnit = ActivityMethods.newTextView(WindTriangleActivity.this, "°");
		TextView wsUnit = ActivityMethods.newTextView(WindTriangleActivity.this, "KTS");
		TextView gtTextView = ActivityMethods.newTextView(WindTriangleActivity.this, "Course Vec.",
				6, android.R.style.TextAppearance_Small);
		gtTextView.setTextColor(Color.GREEN);
		TextView gtUnit = ActivityMethods.newTextView(WindTriangleActivity.this, "°");
		TextView gsUnit = ActivityMethods.newTextView(WindTriangleActivity.this, "KTS");
		
		EditText thEditText = ActivityMethods.newEditText(WindTriangleActivity.this, R.id.true_heading_edit_text,
				5, "Heading");
		EditText tasEditText = ActivityMethods.newEditText(WindTriangleActivity.this, R.id.true_air_speed_edit_text, 
				4, "TAS");
		EditText wdEditText = ActivityMethods.newEditText(WindTriangleActivity.this, R.id.wind_direction_edit_text, 
				5, "Direction");
		EditText wsEditText = ActivityMethods.newEditText(WindTriangleActivity.this, R.id.wind_speed_edit_text, 4, "WS");
		EditText gtEditText = ActivityMethods.newEditText(WindTriangleActivity.this, R.id.ground_track_edit_text, 5, "Course");
		EditText gsEditText = ActivityMethods.newEditText(WindTriangleActivity.this, R.id.ground_speed_edit_text, 4, "GS");
		
		
		TextView solvingText = ActivityMethods.newTextView(WindTriangleActivity.this, "Answer");
		solvingText.setLayoutParams(new LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT,
	            ViewGroup.LayoutParams.WRAP_CONTENT));
		solvingText.setGravity(Gravity.CENTER);
		solvingText.setTextAppearance(WindTriangleActivity.this, android.R.style.TextAppearance_Medium);
		
		solvingForLayout.addView(solvingText); 
		
		if(solving.equals("wswd")){
			wsEditText.setFocusable(false);
			wsEditText.setFocusableInTouchMode(false); 
			wsEditText.setClickable(false);
			
			wdEditText.setFocusable(false);
			wdEditText.setFocusableInTouchMode(false); 
			wdEditText.setClickable(false);
		}else{
			gsEditText.setFocusable(false);
			gsEditText.setFocusableInTouchMode(false); 
			gsEditText.setClickable(false);
			
			gtEditText.setFocusable(false);
			gtEditText.setFocusableInTouchMode(false); 
			gtEditText.setClickable(false);
		}
		
		changeSolvingLayout.addView(solveTextView);
		changeSolvingLayout.addView(solveSpinner);
		
		
		thLayout.addView(thTextView);
		thLayout.addView(thEditText);
		thLayout.addView(thUnit);
		thLayout.addView(tasEditText);
		thLayout.addView(tasUnit);
		
		wdLayout.addView(wdTextView);
		wdLayout.addView(wdEditText);
		wdLayout.addView(wdUnit);
		wdLayout.addView(wsEditText);
		wdLayout.addView(wsUnit);
		
		gtLayout.addView(gtTextView);
		gtLayout.addView(gtEditText);
		gtLayout.addView(gtUnit);
		gtLayout.addView(gsEditText);
		gtLayout.addView(gsUnit);
		
		emptyLayout.setPadding(0, (int)(5*dip), 0, 0);
		
		calculateLayout = ActivityMethods.calcLayout(WindTriangleActivity.this, 
				R.id.clear_wind_triangle, R.id.calculate_wind_triangle);

		wrapper.addView(changeSolvingLayout);
		wrapper.addView(thLayout);
		
		if(solving.equals("wswd")){
			wrapper.addView(gtLayout);
			wrapper.addView(emptyLayout);
			wrapper.addView(calculateLayout);
			wrapper.addView(solvingForLayout);
			wdLayout.setPadding(0, (int)(5*dip), 0, 0);
			wrapper.addView(wdLayout);
		}else{
			wrapper.addView(wdLayout);
			wrapper.addView(emptyLayout);
			wrapper.addView(calculateLayout);
			wrapper.addView(solvingForLayout);
			gtLayout.setPadding(0, (int)(5*dip), 0, 0);
			wrapper.addView(gtLayout);
		}
		
		wrapper.addView(drawVectorsLayout);
		
		content.addView(wrapper);
		
		Button clrWindTriangle = (Button)findViewById(R.id.clear_wind_triangle);
		Button calWindTriangle = (Button)findViewById(R.id.calculate_wind_triangle);

		clrWindTriangle.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clearWindTriangle(v);
            	InputMethodManager imm = (InputMethodManager)getSystemService(
            		      Context.INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
			}
        	
        });
		
		calWindTriangle.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            	InputMethodManager imm = (InputMethodManager)getSystemService(
            		      Context.INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
				calculateWindTriangle(v);
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_wind_triangle, menu);
		return true;
	}

}
