package player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.SwingWorker;

import org.tritonus.share.TCircularBuffer.Trigger;

import util.Duration;
import util.Mp3Info;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

	public class MP3 extends SwingWorker{
	    private String filename;
	    private Player player; 
	    Mp3Info mp3Info;

	    // constructor that takes the name of an MP3 file
	    public MP3(String filename) {
	        this.filename = filename;
	        FileInputStream fis;
			try {
				fis = new FileInputStream(filename);
				BufferedInputStream bis = new BufferedInputStream(fis);	  
	            mp3Info = new Mp3Info(filename);
	            player = new Player(bis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	    }

	    public void close() { if (player != null) player.close(); }

	    // play the MP3 file to the sound card
	    public void play() {
	        try {
	            FileInputStream fis     = new FileInputStream(filename);
	            BufferedInputStream bis = new BufferedInputStream(fis);	  
	            Mp3Info mp3Info = new Mp3Info(filename);
	            System.out.println(mp3Info.toString());
	            System.out.println("Thank you!");
	            player = new Player(bis);
	        }
	        catch (Exception e) {
	            System.out.println("Problem playing file " + filename);
	            System.out.println(e);
	        }

	        // run in new thread to play in background
	        new Thread() {
	            public void run() {
	                try { player.play(); }
	                catch (Exception e) { System.out.println(e); }
	            }
	        }.start();
	    }


	    // test client
	    public static void main(String[] args) {
	        String filename = args[0];
	        MP3 mp3 = new MP3(filename);
	        mp3.play();
	    }

		@Override
		protected Object doInBackground() throws Exception {
			  try { 
				  	new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								player.play();
							} catch (JavaLayerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}).start();
				  	
				  	long pos = 0 ;
				  	long duration = (Long)mp3Info.getProperties().get("duration");
				  	Duration currentTime =  new Duration(0);
				  	double percent = 0 ;
				  	while(!player.isComplete()){
				  		pos = player.getPosition();
				  		pos *=1000;
				  		firePropertyChange("currentTime", currentTime, new Duration(pos));
				  		currentTime = new Duration(pos);
				  		percent = (double)pos/duration;
				  		percent *= 100;
				  		System.out.println("percent : " + percent);
				  		setProgress((int)percent);
				  		Thread.sleep(1000);
				  	}
				  	setProgress(100);
				  	
				  
				  }
              catch (Exception e) {
            	  System.out.println(e); e.printStackTrace(); 
              }
			  
			return player.getPosition();
		}

	}

