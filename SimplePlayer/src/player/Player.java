package player;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.SpringLayout;
import javax.swing.JLabel;

import util.Mp3Info;
import javax.swing.JProgressBar;


public class Player {
	    //this class will be able to accept a single or list of files 
		//and play it like a media player. We will integrate pplay pause and stop as well
		//key goal of this class will be to learn about the progress bar usage
		//and learn how to see the time corresponding to the frame of a song being played.

	private JFrame frame;
	JLabel lblsongName;
	private JLabel lblCurrPos;
	private JLabel lblduration;
	private JProgressBar progressBar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String args[]) {
		final String file = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Player window = new Player();
					MP3 mp3song = new MP3(file);
					Mp3Info info = new Mp3Info(file);
					window.lblsongName.setText(info.getTitle());
					window.lblCurrPos.setText("0:00");
					window.lblduration.setText(info.getDuration().getHumanReadableForm());
					window.frame.setVisible(true);
					
					Thread.sleep(2000);
					
					mp3song.play();
					System.out.println("done");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Player() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		lblsongName = new JLabel("/song name");
		sl_panel.putConstraint(SpringLayout.NORTH, lblsongName, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblsongName, 10, SpringLayout.WEST, panel);
		panel.add(lblsongName);
		
		progressBar = new JProgressBar();
		sl_panel.putConstraint(SpringLayout.NORTH, progressBar, 24, SpringLayout.SOUTH, lblsongName);
		sl_panel.putConstraint(SpringLayout.WEST, progressBar, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, progressBar, 424, SpringLayout.WEST, panel);
		panel.add(progressBar);
		
		lblCurrPos = new JLabel("New label");
		sl_panel.putConstraint(SpringLayout.NORTH, lblCurrPos, 6, SpringLayout.SOUTH, progressBar);
		sl_panel.putConstraint(SpringLayout.WEST, lblCurrPos, 10, SpringLayout.WEST, panel);
		panel.add(lblCurrPos);
		
		lblduration = new JLabel("New label");
		sl_panel.putConstraint(SpringLayout.NORTH, lblduration, 6, SpringLayout.SOUTH, progressBar);
		sl_panel.putConstraint(SpringLayout.EAST, lblduration, -10, SpringLayout.EAST, panel);
		panel.add(lblduration);
	}
}
