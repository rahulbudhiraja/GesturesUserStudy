package com.example.gesturesuserstudy;

import java.util.Vector;



import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class MainActivity extends Activity {
  
   public class TouchView extends View {
     
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float x, y;
    boolean touching = false;
     
    float pressure = 0; //Touch pressure
    float size = 0;  //Touch size
    final static float PRESET_PRESSURE = 0xFF;
    final static float PRESET_SIZE = 1000;
    
    private int noFingers;
    private Vector<Float> locations;
 
  public TouchView(Context context) {
   super(context);
   
   locations=new Vector<Float>();
   // TODO Auto-generated constructor stub
  }
 
  @Override
  protected void onDraw(Canvas canvas) {
   // TODO Auto-generated method stub
   if(touching){
     
    //paint.setStyle(Paint.Style.STROKE);
    //paint.setStrokeWidth(1);
    //paint.setColor(Color.WHITE);
    //canvas.drawCircle(x, y, 50f, paint);
     
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setStrokeWidth(1);
    paint.setColor(Color.BLUE);
    
    Log.d("number of fingers", ""+noFingers);
    for(int j=0;j<2*noFingers;j+=2)
       canvas.drawCircle(locations.get(j),locations.get(j+1), 100, paint);
   
    locations.clear();
   }
   
   
  }
 
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
   // TODO Auto-generated method stub
   setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
         MeasureSpec.getSize(heightMeasureSpec));
  }
 
  @Override
  public boolean onTouchEvent(MotionEvent event) {
   // TODO Auto-generated method stub
    
   pressure = event.getPressure();
   if(pressure > 1){
    pressure = 1;
   }
    
   size =event.getSize();
   noFingers=event.getPointerCount();

   for(int i=0;i<noFingers;i++)
   {
	   
	   locations.add(event.getX(i));
	   locations.add(event.getY(i));
   }
    
   
   if(noFingers>0)
   {
	   String sendString="";
	   sendString+=noFingers+",";
       
	   for(int i=0;i<locations.size();i++)
	   	   sendString+=locations.get(i)+",";
	   sendString+=323;
	   
	   Log.d("message is",sendString+"");
	   new Thread(new Client(sendString)).start();
   }
   String act;
 
   int action = event.getAction();
   switch(action){
   case MotionEvent.ACTION_MOVE:
    act = "ACTION_MOVE\n";
    x = event.getX();
    y = event.getY();
 
    touching = true;
    break;
   case MotionEvent.ACTION_DOWN:
    act = "ACTION_DOWN\n";
    x = event.getX();
    y = event.getY();
     
    touching = true;
    break;
   case MotionEvent.ACTION_UP:
    act = "ACTION_UP\n";
    touching = false;
    break;
   default:
    act = "XXX\n";
    touching = false;
   }
    
   act += event.toString();
   textView.setText(act);
    
   invalidate();
   return true;
  }
 
 }
   
   TextView textView;
 
 /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       //setContentView(new TouchView(this));
       
       textView = new TextView(this);
       textView.setText("Waiting");
       TouchView touchView = new TouchView(this);
       LinearLayout mainScreen = new LinearLayout(this);
       mainScreen.setOrientation(LinearLayout.VERTICAL);
       //mainScreen.addView(textView);
       mainScreen.addView(touchView);
       
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(mainScreen);
       
     
 
   }
   
}
    	
	

