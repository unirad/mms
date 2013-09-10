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

import javax.swing.SwingWorker;

import util.Mp3Info;
import javazoom.jl.player.Player;


	public class MP3 extends SwingWorker{
	    private String filename;
	    private Player player; 

	    // constructor that takes the name of an MP3 file
	    public MP3(String filename) {
	        this.filename = filename;
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
	        

	        // do whatever computation you like, while music plays
	      
	        // when the computation is done, stop playing it
	        

	        // play from the beginning
	        
	       

	    }

		@Override
		protected Object doInBackground() throws Exception {
			  try { player.play(); }
              catch (Exception e) { System.out.println(e); }
			return player.getPosition();
		}

	}

