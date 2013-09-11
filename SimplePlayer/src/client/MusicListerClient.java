package client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicListerClient {

	public static void main(String args[])
	{
		if(args.length<2)
		{
			System.out.println("Invalid parameters. You must provide "
					+ "server hostname and port");
		}
		
		try {
			Socket s = new Socket(args[0], Integer.parseInt(args[1]));
			BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
			Player backgroundPlayer = new Player(bis);
			backgroundPlayer.play();
			
			
		} catch (NumberFormatException e) {
			System.out.println("Error: invalid port number. Pleae provide a valid "
					+ "port number for remote music server");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("Error: the hostname entered could not be located");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: communicating to server.");
			e.printStackTrace();
		} catch (JavaLayerException e) {
			System.out.println("Error: stream from server not supported.");
			e.printStackTrace();
		}
	}
	
}
