package com.calebbrose.loofiti;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.calebbrose.loofiti.drawing.DrawingView;

public class StallActivity extends Activity implements OnClickListener {

	private DrawingView drawView;
	
	private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
	
	//private Stall currStall;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Action Bar appears then disappears
      	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
      	getActionBar().hide();
        
        setContentView(R.layout.activity_stall);
        
        
        
        
        drawView = (DrawingView)findViewById(R.id.drawing);
        
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        
        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);
        
        eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);
        
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);
        
        saveBtn = (ImageButton)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void paintClicked(View view){
    	drawView.setErase(false);
    	
    	if(view!=currPaint){
    		//update color
    		ImageButton imgView = (ImageButton)view;
    		String color = view.getTag().toString();
    		drawView.setColor(color);
    		
    		//update UI to reflect new chosen paint
    		imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint));
    		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
    		currPaint=(ImageButton)view;
    	}
    }
    
    @Override
    public void onClick(View view) {
    	//respond to clicks
    	if (view.getId()==R.id.draw_btn){
    		
    		drawView.setErase(false);
    	}
    	else if(view.getId()==R.id.erase_btn){
    		drawView.setErase(true);
    	}		    		
    		
    	else if(view.getId()==R.id.new_btn){
    		drawView.startNew();
    	}
    	
    	else if (view.getId()==R.id.save_btn){
    		//save drawing
    		AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
    		newDialog.setTitle("Flush");
    		newDialog.setMessage("Flush and leave?");
    		newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					drawView.flush();
					Toast savedToast = Toast.makeText(
							getApplicationContext(), 
							"You've left your mark! Thanks for flushing!", 
							Toast.LENGTH_SHORT);
					savedToast.show();
				}
			});
    		newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();	
				}
			});
    		newDialog.show();
    	}
    }

}
