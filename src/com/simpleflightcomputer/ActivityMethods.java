package com.simpleflightcomputer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityMethods{

	
	public static LinearLayout calcLayout(Context context, int clearId, int calcId){
		LinearLayout calculateLayout = newHorizontalLinearLayout(context);
		float dip = context.getResources().getDisplayMetrics().density;
		Log.d("DIP", ""+dip);
		calculateLayout.setPadding((int)(60*dip), 0, (int)(60*dip), 0);
		Button clear = new Button(context);
		Button calc = new Button(context);
		clear.setId(clearId);
		calc.setId(calcId);
		
		clear.setText("Clear");
		clear.setGravity(Gravity.LEFT);
		calc.setText("Calculate");
		calc.setGravity(Gravity.RIGHT);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
		clear.setLayoutParams(lp);
		calc.setLayoutParams(lp);
		
		calculateLayout.addView(clear);
		calculateLayout.addView(calc);
		
		return calculateLayout;
		
	}
	

    public static Spinner newSpinner(Context context, String[] stringArray, int id){
    	List<String> c = Arrays.asList(stringArray);
    	
    	Spinner s = new Spinner(context);
		List<String> array = new ArrayList<String>(c);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 
				android.R.layout.simple_spinner_item, array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		s.setId(id);
		s.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
				));
    	return s;
    }
    
    public static LinearLayout newHorizontalLinearLayout(Context context){
    	LinearLayout l = new LinearLayout(context);
    	l.setOrientation(LinearLayout.HORIZONTAL);
		l.setLayoutParams(new LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
		return l;
    }
    
    
    public static EditText newEditText(Context context, int id, int ems, String hint){
    	EditText e = newEditText(context, id, ems);
    	e.setHint(hint);
    	return e;
    }
    
    public static EditText newEditText(Context context, int id, int ems){
    	EditText e = new EditText(context);
    	e.setId(id);
    	e.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
				));
		e.setEms(ems);
		e.setKeyListener( new MyDigitsKeyListener(e));
		return e;
    }
    
    public static TextView newTextView(Context context, String text, int ems, int textAppearance){
    	TextView t = newTextView(context, text, ems);
    	t.setTextAppearance(context, textAppearance);
    	return t;
    }
    
    public static TextView newTextView(Context context, String text, int ems){
    	TextView t = newTextView(context, text);
    	t.setEms(ems);
    	return t;
    }
    
    public static TextView newTextView(Context context, String text){
    	TextView t = new TextView(context);
    	t.setText(text);
    	
    	return t;
    }
}
