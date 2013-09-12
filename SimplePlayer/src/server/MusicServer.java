package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import util.Mp3Info;
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
	ArrayList<BufferedOutputStream> outputStreams;
	ConcurrentHashMap<String, Long> hostDelayMap;
	public int currentFileFrameSize;
	final int MAX_CLIENTS;

	public MusicServer(String[] playList, int port) {

		// TODO: this can be configured using a config attribute
		// max clients.
		MAX_CLIENTS = 1;

		if (playList.length < 1) {
			System.out
					.println("Invalid playlist. Playlist must have atleast one entry");
			System.exit(1);

		} else {
			this.playList = new ArrayList<File>();
			for (String filename : playList) {
				System.out
						.println("Adding file : " + filename + " to playlist");
				File f = new File(filename);
				this.playList.add(f);
			}
		}

		this.port = port;
		currIndex = 0;
		currFile = this.playList.get(currIndex);
	}

	public void startUp() {
		System.out.println("Starting music server");
		try {
			ss = new ServerSocket(port);
			outputStreams = new ArrayList<BufferedOutputStream>();
			System.out.println("Server started on port : " + port);
			fis = new FileInputStream(currFile);
			bis = new BufferedInputStream(fis);
			Mp3Info info = new Mp3Info(currFile.getAbsolutePath());
			System.out.println("SERVER: file loaded: " + currFile.getName());
			currentFileFrameSize = (Integer) info.getProperties().get(
					"mp3.framesize.bytes");
			System.out.println("SERVER : frame size = " + currentFileFrameSize);

			int count = 0;
			while (count < MAX_CLIENTS) {
				Socket s = ss.accept();
				String remoteHostname = s.getInetAddress().getHostName();
				System.out.println("Host : " + remoteHostname
						+ " added to listening list");
				BufferedOutputStream bos = new BufferedOutputStream(
						s.getOutputStream());
				outputStreams.add(bos);
				count++;
			}

			System.out.println("Max number of clients have joined.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void playSong() {

		System.out.println("Starting streaming ...");
		try {
			while (bis.available() > 0) {
				int byt = bis.read();
				for (BufferedOutputStream bos : outputStreams) {
					bos.write(byt);
				}
			}
		} catch (IOException e) {
			System.out.println("Error: IOException while playing song.");
			e.printStackTrace();
		}
		System.out.println("End of song");
	}

	public static void main(String args[]) throws IOException {

		// TODO: read port number from config file.
		int port = 9999;
		MusicServer server = new MusicServer(args, port);
		server.startUp();
		// System.out.println("Press enter to start music multicast.");
		// new BufferedReader(new InputStreamReader(System.in)).readLine();
		server.playSong();
	}

}
