package player;



	/*************************************************************************
	 *  Compilation:  javac -classpath .:jl1.0.jar MP3.java         (OS X)
	 *                javac -classpath .;jl1.0.jar MP3.java         (Windows)
	 *  Execution:    java -classpath .:jl1.0.jar MP3 filename.mp3  (OS X / Linux)
	 *                java -classpath .;jl1.0.jar MP3 filename.mp3  (Windows)
	 *  
	 *  Plays an MP3 file using the JLayer MP3 library.
	 *
	 *  Reference:  http://www.javazoom.net/javalayer/sources.html
	 *
	 *
	 *  To execute, get the file jl1.0.jar from the website above or from
	 *
	 *      http://www.cs.princeton.edu/introcs/24inout/jl1.0.jar
	 *
	 *  and put it in your working directory with this file MP3.java.
	 *
	 *************************************************************************/

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.SwingWorker;

import util.Duration;
import util.Mp3Info;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


	public class MP3 extends SwingWorker<Integer, String>{
	    private String filename;
	    private Player player;
	    private Mp3Info mp3Info;

	    // constructor that takes the name of an MP3 file
	    public MP3(String filename) {
	        this.filename = filename;
	        FileInputStream fis = null;
			try {
				fis = new FileInputStream(filename);
				BufferedInputStream bis = new BufferedInputStream(fis);
	            player = new Player(bis);
	            mp3Info = new Mp3Info(filename);
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
	            mp3Info = new Mp3Info(filename);
	            System.out.println(mp3Info.toString());
	            System.out.println("Thank you!");
	            player = new Player(bis);
	            player.play();
	           
	        }
	        catch (Exception e) {
	            System.out.println("Problem playing file " + filename);
	            System.out.println(e);
	        }

	        // run in new thread to play in background
	       /* new Thread() {
	            public void run() {
	                try { player.play(); }
	                catch (Exception e) { System.out.println(e); }
	            }
	        }.start();*/
        }


	    // test client
	    public static void main(String[] args) {
	        String filename = args[0];
	        MP3 mp3 = new MP3(filename);
	        mp3.play();

	    }

		@Override
		protected Integer doInBackground() throws Exception {
			  try {
				    //start a secondary background thread that plays the song
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
				    
				    //keep track of the progress in the primary background thread
				    //System.out.println("Frames-length" + mp3Info.getFramesLength());
				    //System.out.println(mp3Info.getProperties().toString());
				    long duration = (Long)mp3Info.getProperties().get("duration");
				    double progressNew = 0;
				    Duration prevTime = new Duration(0);
				    Duration currTime = new Duration(0);
				    while(!player.isComplete())
				    {
					    				
					    long pos = player.getPosition()*1000;
					    progressNew = ((double)pos/duration)*100; 
					    int percent = (int)progressNew;
					    setProgress(percent);
					    prevTime = currTime;
					    currTime = new Duration(pos);
					    firePropertyChange("currentTime",prevTime, currTime);
				    	//System.out.println("pos : " + pos +"/" + 
					    //duration + " = " + progressNew +  " = " + percent +"%"  );
				    	Thread.sleep(1000);
				    }
				  }
              catch (Exception e) { System.out.println(e); e.printStackTrace();}
			return 1;
		}

	}

