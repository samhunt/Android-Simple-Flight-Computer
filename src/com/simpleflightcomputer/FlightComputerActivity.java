package com.simpleflightcomputer;


import com.simpleflightcomputer.util.SystemUiHider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FlightComputerActivity extends Activity {

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_flight_computer);

        final View contentView = findViewById(R.id.fullscreen_content);

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
        
        Button dist_speed_time = (Button) findViewById(R.id.dis_speed_time);
        Button wind_triangle = (Button) findViewById(R.id.wind_triangle);
        
        dist_speed_time.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(FlightComputerActivity.this,
	                    DistSpeedTimeActivity.class);
				FlightComputerActivity.this.startActivity(mainIntent);
			}
		});
        
        wind_triangle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(FlightComputerActivity.this);
				builder.setTitle(R.string.pick_calc);
				builder.setItems(R.array.pick_calc_array, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   Intent intent = new Intent(FlightComputerActivity.this,
		            			   WindTriangleActivity.class);
		            	   if(which == 0){
		            		   intent.putExtra("solving", "wswd");
		            	   }else{
		            		   intent.putExtra("solving", "cgs");
		            	   }
		            	   FlightComputerActivity.this.startActivity(intent);
		               }
				});
				
				AlertDialog alert = builder.create(); 
				alert.show();
			}
		});
    }

	//Opens Settings.
    public void settingsClicked(View view) {
    	
    }
}