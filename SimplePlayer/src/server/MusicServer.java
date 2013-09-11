package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javazoom.jl.player.Player;

public class MusicServer {
	
	
	int port;
	File currFile;
	int currIndex;
	ArrayList<File> playList;
	Player backgroundPlayer;
	BufferedInputStream bis;
	FileInputStream fis;
	ServerSocket ss;
	
	public MusicServer(String[] playList, int port){
		if(playList.length <1)
		{
			System.out.println("Invalid playlist. Playlist must have atleast one entry");
			System.exit(10);
			
		}
		else
		{
			this.playList = new ArrayList<File>();
			for(String filename : playList){
				System.out.println("Adding file : " + filename + " to playlist");
				File f = new File(filename);
				this.playList.add(f);
			}
		}
		
		this.port  = port;
		currIndex = 0;
		currFile = this.playList.get(currIndex);
	}
	
	public void startUp()
	{
		System.out.println("Starting music server");
		try {
			ss = new ServerSocket(port);
			System.out.println("Server started on port : " + port );
			fis = new FileInputStream(currFile);
			bis = new BufferedInputStream(fis);
			Socket s = ss.accept();
			BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
			while(bis.available()>0)
			{
				bos.write(bis.read());
			}
			System.out.println("End of song");
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String args[]){
		
		//TODO: read port number from config file.
		int port = 9999;
		MusicServer server = new MusicServer(args, port);
		server.startUp();
	}
 
}
