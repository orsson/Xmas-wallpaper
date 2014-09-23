package com.example.Xmas;

import java.util.Calendar;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class Wallpaper extends WallpaperService implements SensorEventListener {

    public static float XPIXELS;
    public static float XPIXELS2;
    
    public static Bitmap back;
    public static Bitmap fore;
    public static Bitmap beforescale;
    
    public static Bitmap snowflake;
    public static Bitmap snowman;
    public static Bitmap presents;
    public static Bitmap presents2;
    public static Bitmap blizzard;
   
    Resources res;
    DisplayMetrics displayMetrics;
    static int changeBitmap;
    float   scaledWidth;
    float   scaledHeight;
    
   public static int mScreenWidth;
   public static int mSreenHeight;
    
	private float mLastX, mLastY, mLastZ;
   	private boolean mInitialized;
   	private SensorManager mSensorManager;
   	private Sensor mAccelerometer;
   	private final float NOISE = (float) 2.0;
   	
    @Override
    public void onCreate() {
        super.onCreate();
        
        changeBitmap=0;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine(); // wazne
    }
    
    class WallpaperEngine extends Engine {

        private static final String TAG = "WallpaperEngine";

        private AnimationThread animationThread;
        private Scene scene;
        
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // create the scene
            scene = new Scene();
            
            // start animation thread; thread starts paused
            // will run onVisibilityChanged
            animationThread = new AnimationThread(surfaceHolder, scene);
            animationThread.start();
            
            res=getResources();
            Context mContext = getBaseContext();
            displayMetrics = new DisplayMetrics(); 
            displayMetrics = mContext.getResources().getDisplayMetrics();
        
             mScreenWidth = displayMetrics.widthPixels;
             mSreenHeight = displayMetrics.heightPixels;
             
           beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.dawn_background);
            
           float xScale = (float) mScreenWidth/ beforescale.getWidth();
           float yScale = (float) mSreenHeight/ beforescale.getHeight();
           float scale = Math.max(xScale, yScale); //selects the larger size to grow the images by
             
           scaledWidth = scale * beforescale.getWidth();
           scaledHeight = scale * beforescale.getHeight();
           
           loadActivity(); 
   }
        
        public  void loadActivity()
        {
        	BitmapFactory.Options options = new BitmapFactory.Options();
        	options.inPreferredConfig = Config.RGB_565;
        	options.inDither=false;                     //Disable Dithering mode
        	options.inPurgeable=true;   				//Tell to gc that whether it needs free
        	
        	 Calendar calendar = Calendar.getInstance();
        	 int  hours = calendar.get(Calendar.HOUR_OF_DAY);  // int  hours = calendar.get(Calendar.HOUR_OF_DAY);
            
               if(hours>=6 && hours<8 && changeBitmap!=1 )
               {
            	beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.dawn_background,options);
                back = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.dawn_foreground,options);
                fore = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.blizzard_day,options);
                blizzard = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                changeBitmap=1;
               }
               if(hours>=8 && hours<15 && changeBitmap!=2 )
               {
            	beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.day_background,options);
                back = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true); 
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.day_foreground,options); 
                fore = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.blizzard_day,options);
                blizzard = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                changeBitmap=2;
               }
               if(hours>=15 && hours<17 && changeBitmap!=3 )
               {
            	beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.evening_background,options);
            	back = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
            	beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.evening_foreground,options);
                fore = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.blizzard_day,options);
                blizzard = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                changeBitmap=3;
               }
               if(hours>=17 && changeBitmap!=4)
               {
            	beforescale = BitmapFactory.decodeResource(res,R.drawable.night_background,options);
                back = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                beforescale = BitmapFactory.decodeResource(res,R.drawable.night_foreground,options);	
                fore = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.blizzard_night,options);
                blizzard = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                changeBitmap=4;
               }
           	   if(hours<6 && changeBitmap!=4)
               {
           		beforescale = BitmapFactory.decodeResource(res,R.drawable.night_background,options);
                back = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                beforescale = BitmapFactory.decodeResource(res,R.drawable.night_foreground,options);	
                fore = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                
                beforescale = BitmapFactory.decodeResource(getResources(),R.drawable.blizzard_night,options);
                blizzard = Bitmap.createScaledBitmap(beforescale, (int)scaledWidth, (int)scaledHeight, true);
                changeBitmap=4;
               }
               
                snowflake = BitmapFactory.decodeResource(getResources(),R.drawable.snowflake,options);
                snowman = BitmapFactory.decodeResource(getResources(),R.drawable.balwandzien,options);
                presents = BitmapFactory.decodeResource(getResources(),R.drawable.presents,options);
                presents2 = BitmapFactory.decodeResource(getResources(),R.drawable.presents2,options);
                
                //Scale
               snowman = Bitmap.createScaledBitmap(snowman, 200, 330, true);
               presents = Bitmap.createScaledBitmap(presents, 190, 80, true);
               presents2 = Bitmap.createScaledBitmap(presents2, 190, 80, true); 
               presents2 = Bitmap.createScaledBitmap(presents2, 190, 80, true); 
              
               System.gc();      
        }
        
        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");

            animationThread.stopThread();
            joinThread(animationThread);
            animationThread = null;

            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d(TAG, "onVisibilityChanged: " + (visible ? "visible" : "invisible"));
            if (visible) {
                animationThread.resumeThread();
                
              loadActivity();
                
              Calendar calendar = Calendar.getInstance();
           	  int  minute = calendar.get(Calendar.MINUTE);  
         
                if(minute>=0 && minute<20 && Scene.elements_choosed!=1)
                {
                	Scene.elements_choosed=1;
                	Random rnd=new Random();
                    for(int j=0;j<Scene.additional_objects.length;j++)
                	    Scene.additional_objects[j]=rnd.nextInt(7);	//losuje konkretne elementy
                }
                if(minute>=20 && minute<40 && Scene.elements_choosed!=2)
                {
                	Scene.elements_choosed=2;
                	Random rnd=new Random();
                    for(int j=0;j<Scene.additional_objects.length;j++)
                	    Scene.additional_objects[j]=rnd.nextInt(7);	//losuje konkretne elementy
                }
                if(minute>=40 && Scene.elements_choosed!=3)
                {
                Scene.elements_choosed=3;
                Random rnd=new Random();
                for(int j=0;j<Scene.additional_objects.length;j++)
            	    Scene.additional_objects[j]=rnd.nextInt(7);	//losuje konkretne elementy
                }
                
           
            } else {
                animationThread.pauseThread();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.d(TAG, "onSurfaceChanged: width: " + width + ", height: " + height);

            scene.updateSize(width, height);

        }
        
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) 
        {
        	XPIXELS=xPixels*1.2f;
        	XPIXELS2=(xPixels)/2;
        }

        private void joinThread(Thread thread) {
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }
        
       /*
        public void onTouchEvent(MotionEvent event) 
        {
        	
        	 Calendar calendar = Calendar.getInstance();
        	 int  hours = calendar.get(Calendar.MINUTE);  // int  hours = calendar.get(Calendar.HOUR_OF_DAY);
        	 
        	 if(hours>=6 && hours<8 && changeBitmap!=1 )
        	 loadActivity();
        	 
        	 if(hours>=8 && hours<15 && changeBitmap!=2 )
        	 loadActivity(); 
        	 
        	 if(hours>=15 && hours<17 && changeBitmap!=3 )
        	 loadActivity(); 
        	 
        	 if(hours>=17 && changeBitmap!=4)
        	 loadActivity();
       
        	 if(hours<6 && changeBitmap!=4)
        	 loadActivity();
        }
        */

    }
    
    public void onSensorChanged(SensorEvent event) {
    	
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized)
		{
		mLastX = x;
		mLastY = y;
		mLastZ = z;
		mInitialized = true;
		}
		
		else 
		{
		float deltaX = Math.abs(mLastX - x);
		float deltaY = Math.abs(mLastY - y);
		float deltaZ = Math.abs(mLastZ - z);
		if (deltaX < NOISE) deltaX = (float)0.0;
		if (deltaY < NOISE) deltaY = (float)0.0;
		if (deltaZ < NOISE) deltaZ = (float)0.0;
		mLastX = x;
		mLastY = y;
		mLastZ = z;

		if (x> 1.7f && x<4.5f) //lewo
		
			Scene.snow=1;
	
		else if (x> 4.5f) //lewo 
		
			Scene.snow=3;
		
		else if (-1.7f > x && -4.5f<x) //prawo
			Scene.snow=2;
		
		else if (-4.5f > x) //prawo
			Scene.snow=4;
		
		else //stoj
			Scene.snow=0;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
    

}
