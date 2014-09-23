package com.example.Xmas;

import java.util.Calendar;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.service.wallpaper.WallpaperService;

public class Scene extends WallpaperService 
{
  
    private static boolean elements_launched; //znaczkik
    static int elements_choosed;
    
    private static int minutes;
    private static int hours;
    
    public static int snow;
    public static int[] additional_objects;
    static Paint paint;
    
    public static int mScreenWidth;
    public static int mSreenHeight;
    
    static int[] pkt_x;
    static int[] pkt_y;
    
    public Scene() 
    {
    	elements_launched=false; 
    	elements_choosed=1;
    	
    	 paint = new Paint();
    	 
    	additional_objects=new int[4];
    	
    	for(int i=0;i<additional_objects.length;i++)
    		additional_objects[i]=0;
    	
    	 pkt_x=new int[70];
    	 pkt_y=new int[70];
    	         
         for(int i=0;i<pkt_x.length;i++)
     	{
     		Random rnd=new Random(); 				
     		pkt_x[i]=rnd.nextInt(480);
     		pkt_y[i]=rnd.nextInt(1000);
     	}
         
    }

    public synchronized void updateSize(int width, int height)
    {
        update();      
    }

    public synchronized void update() 
    {
    	//krawedzie
    	 for(int i=0;i<pkt_x.length;i++)
    	 {
    		 
    		 if(Wallpaper.mScreenWidth < pkt_x[i])
    		 {
    		  pkt_x[i]=-20; 
    		 }
    		 
    		 if(-20 > pkt_x[i])
    		 {
    		  pkt_x[i]=Wallpaper.mScreenWidth; 
    		 }
    		 
    		 
    		 if(pkt_y[i] > Wallpaper.mSreenHeight )
    		 {
    			 pkt_y[i]=-20;
    		 }
    	 }
    	
           //snow accelerometr
    	       if(snow==1)//prawo
    	       {
    	    	   for(int i=0;i<pkt_x.length;i++)
    	      	 {
           	    		if(i%2==0)   
           	            pkt_x[i]-=9;
           	    		else
           	    		pkt_x[i]-=4;
    	      	 }
    	       }
    	       
    	       if(snow==3)// prawo 
    	       {
    	    	   for(int i=0;i<pkt_x.length;i++)
    	      	 {
           	    		if(i%2==0)   
           	            pkt_x[i]-=15;
           	    		else
           	    		pkt_x[i]-=9;
    	      	 }
    	       }
    	       
    	       
    	       if(snow==2)// prawo 
        	       {
        	    	   for(int i=0;i<pkt_x.length;i++)
        	      	 {
        	    		if(i%2==0)   
        	            pkt_x[i]+=9;
        	    		else
        	    		pkt_x[i]+=4;
        	      	 }
        	       }
    	 
    	       if(snow==4)// prawo 
    	       {
    	    	   for(int i=0;i<pkt_x.length;i++)
    	      	 {
    	    		if(i%2==0)   
    	            pkt_x[i]+=15;
    	    		else
    	    		pkt_x[i]+=9;
    	      	 }
    	       }
    	       
    	 for(int i=0;i<pkt_x.length;i++)
    	 {
    	  if(i%2==0)   
          pkt_y[i]+=4;
    	  
    	  if(i%3==0)   
          pkt_y[i]+=5;
    	      	  
    	  if(i%3!=0 && i%3!=0)
    	  pkt_y[i]+=3;  
    	 }
              
    }
    
    public synchronized static void draw(Canvas canvas) 
    {
        	   Calendar calendar = Calendar.getInstance();
        	   hours = calendar.get(Calendar.HOUR_OF_DAY);
               minutes = calendar.get(Calendar.MINUTE);
               int seconds=calendar.get(Calendar.SECOND);
               
               if(hours==6 && minutes==0 && seconds==0)
               Wallpaper.changeBitmap=1;
               
               if(hours==8 && minutes==0 && seconds==0)
               Wallpaper.changeBitmap=2;
               
               if(hours==15 && minutes==0 && seconds==0)
               Wallpaper.changeBitmap=3;
               
               if(hours==17 && minutes==0 && seconds==0)
               Wallpaper.changeBitmap=4;
                
               
               if((minutes%20==0 && seconds==0)  )//if((minutes%20==0 && seconds==0) || elements_launched==false )
               {
            	    Random rnd=new Random();
            	    
            	    for(int j=0;j<additional_objects.length;j++)
            	    {
            	    additional_objects[j]=rnd.nextInt(7);	//losuje konkretne elementy      	
            	    }
            	    
            	    if(minutes==00)elements_choosed=1;
            	    if(minutes==20)elements_choosed=2;
            	    if(minutes==40)elements_choosed=3;
            	    
            		elements_launched=true;
               }
               
            	   if(additional_objects[0]==5 || additional_objects[0]==6 ||
            	      additional_objects[1]==5 || additional_objects[1]==6 ||
            		  additional_objects[2]==5 || additional_objects[2]==6 ||
            		  additional_objects[3]==5 || additional_objects[3]==6 )
            	   {
                   canvas.drawBitmap(Wallpaper.blizzard, Wallpaper.XPIXELS2, 0, null); 
                   paint.setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF001133));
            	   }
            	   else
            	   {
            	   canvas.drawBitmap(Wallpaper.back, Wallpaper.XPIXELS2, 0, null);  
            	   paint.reset();
            	   }

               canvas.drawBitmap(Wallpaper.fore, Wallpaper.XPIXELS, 0, paint); 
               	 
               for(int j=0;j<additional_objects.length;j++)
               {
            		if(additional_objects[j]==1)
            		canvas.drawBitmap(Wallpaper.snowman, Wallpaper.XPIXELS+Wallpaper.mScreenWidth*2-100,Wallpaper.mSreenHeight-340, paint);
            		
            		if(additional_objects[j]==2)
            		canvas.drawBitmap(Wallpaper.presents, Wallpaper.XPIXELS+Wallpaper.mScreenWidth,Wallpaper.mSreenHeight-190, paint);
            		
            		if(additional_objects[j]==3)
            		canvas.drawBitmap(Wallpaper.presents2, Wallpaper.XPIXELS+Wallpaper.mScreenWidth-190,Wallpaper.mSreenHeight-180, paint);
            		
            		if(additional_objects[j]==4)
            		{
            		 for(int i=0;i<15;i++) 
                     canvas.drawBitmap(Wallpaper.snowflake,pkt_x[i] ,pkt_y[i], null);
            		}
            		
            		if(additional_objects[j]==5)
            		{
            		 for(int i=0;i<(pkt_x.length)/2;i++)
                     canvas.drawBitmap(Wallpaper.snowflake,pkt_x[i] ,pkt_y[i], null);
            		}
            		
            		if(additional_objects[j]==6)
            		{
            		 for(int i=0;i<pkt_x.length;i++)
                     canvas.drawBitmap(Wallpaper.snowflake,pkt_x[i] ,pkt_y[i], null);
            		}
               }
              
        }
    
	@Override
	public Engine onCreateEngine() {
		// TODO Auto-generated method stub
		return null;
	} 
	
}
