package player;

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
	    }

		@Override
		protected Object doInBackground() throws Exception {
			  try { player.play(); }
              catch (Exception e) { System.out.println(e); }
			return player.getPosition();
		}

	}

