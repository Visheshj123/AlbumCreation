//import jm.music.*;
import jm.JMC;

import jm.music.data.*;
import jm.util.*;

import jm.util.Play;
import jm.util.Read;
import jm.util.Write;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import java.util.Random;

public class MusicPlayer extends JFrame implements Runnable{
	String userName = "Player 1";
	String nowPlayingInfo = "hello";
	
	LinkedList <SongItem> playList = new LinkedList();
	int listSize = 0;
	//ArrayList <String> songTitles = new ArrayList<>();
	String [] songTitles = new String [100];
	//songTitles[0] = "Hello";
	JList songList;
	int playListIndex = 0;
	
	InputStreamReader isr;
	OutputStreamWriter outWriter;
	BufferedReader reader;
	PrintWriter pWriter;
	Socket clientConn;
	ServerSocket serverSock;
	boolean connected = false;
	
	ServerSocket server;
	
	Socket sock;
	InputStreamReader isrClient;
	OutputStreamWriter outWriter2;
	BufferedReader reader2;
	PrintWriter pWriter2;
	
	JButton addSongButton;
	JButton NextSong;
	JButton RandomSong;
	JButton viewSongList;
	JButton returnToMenu;
	JButton playSong;
	
	Image pic;
	
	JTextField songTitle1;
	JTextField artistName1;
	JTextField albumName1;
	JTextField songFile1;
	JTextField coverFile1;
	
	
	JTextArea networkPlaying = new JTextArea(10, 20);
	JTextArea nowPlaying = new JTextArea(10, 20);
	JTextArea currentSong = new JTextArea("Current Song:  ");
	
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;
	JPanel panel6;
	//AlbumPanel albumCover;
	
	JLabel testLabel;
	JLabel coverLabel;
	
	private BufferedReader in;
	private PrintWriter out;
	
    
   
	public void createFrame() {
		
		//testLabel = new JLabel("Text displayed here");
		coverLabel = new JLabel("Album Art");
		
		songTitle1 = new JTextField("Enter Song Title:");
		artistName1 = new JTextField("Enter artist name:");
		albumName1 = new JTextField("Enter album name:  ");
		songFile1 = new JTextField("Enter song file:");
		coverFile1 = new JTextField("Enter album cover file:");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		
		addSongButton = new JButton("Add Song");
			addSongButton.addActionListener(new addSongListener());
		NextSong = new JButton("Next Song");
			NextSong.addActionListener(new NextSongListener());
		RandomSong = new JButton("Play Random Song");
			RandomSong.addActionListener(new RandomSongListener());
		viewSongList = new JButton("View Playlist");
			viewSongList.addActionListener(new viewListListener());
		returnToMenu = new JButton("Add Another Song");
			returnToMenu.addActionListener(new returnActionListener());
		playSong = new JButton("Play");
			playSong.addActionListener(new playSongListener());
			
		//songTitles[0] = "Song 1";

		songList = new JList(songTitles);
	
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(songTitle1);
		panel1.add(artistName1);
		panel1.add(albumName1);
		panel1.add(songFile1);
		panel1.add(coverFile1);
		//panel1.add(testLabel);
		//panel1.add(coverLabel);
		panel1.setVisible(true);
		
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(addSongButton);
		panel2.add(viewSongList);
		
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		//panel3.add(nowPlaying);
		//panel3.add(songList);
		panel3.add(playSong);
		panel3.add(NextSong);
		panel3.add(RandomSong);
		panel3.add(returnToMenu);
		panel3.setVisible(false);
		
	
		//panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
		//panel5.add(nowPlaying);
		panel5.add(BorderLayout.NORTH, panel3);
		panel5.add(BorderLayout.WEST, currentSong);
		panel5.add(BorderLayout.SOUTH, networkPlaying);
		panel5.add(BorderLayout.EAST, nowPlaying);
		panel5.add(BorderLayout.WEST, currentSong);
		panel5.add(panel6);
		panel5.add(coverLabel);
		panel5.setVisible(false);
		//panel5.add(nowPlaying);
		//panel5.add(songList);
		
		
		this.setSize(700, 700);
		this.getContentPane().add(BorderLayout.NORTH, panel1);
		this.getContentPane().add(BorderLayout.SOUTH, panel2);
		//this.getContentPane().add(BorderLayout.EAST, new JScrollPane(songList));
		//this.getContentPane().add(BorderLayout.EAST, nowPlaying);
		this.getContentPane().add(BorderLayout.CENTER, panel5);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//serverNetwork();
		//clientNetwork();
		//setUpNetworking();
	}
	
	public void addSong(String artistName, String songTitle, String albumTitle, String songFile, String coverFile){
        
		SongItem newSong = new SongItem(artistName, songTitle, albumTitle, songFile, coverFile);
		playList.add(newSong);
		//songTitles.add(songTitle1.getText());
		listSize++;
		//for(int i = 0; i < listSize; i++) {
			//songTitles[listSize] = songTitle1.getText();
			nowPlaying.append("Title:  " + songTitle1.getText() + "\nBy: " + artistName1.getText() + "\nFrom:  " + albumName1.getText() + "\n");
		//}
		System.out.println("Number of songs:  " + listSize);
    //songList.add(newSong.getSongTitle());
	}

	
	public class addSongListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			addSong(artistName1.getText(), songTitle1.getText(), albumName1.getText(), songFile1.getText(), coverFile1.getText());
			System.out.println("List size:  " + listSize);
			System.out.println(songTitle1.getText());
    			//songTitles[0] = songTitle1.getText(); 			
		}
	}
	
	
	
	public class playSongListener extends Thread implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			ImageIcon icon = new ImageIcon(playList.get(playListIndex).getAlbumCoverPath(), "Album cover");
			//coverLabel = new JLabel(new ImageIcon(this.getClass().getResource(playList.get(playListIndex).getAlbumCoverPath())));
			//coverLabel.setPreferredSize(new Dimension(150, 150));
			//coverLabel = new JLabel(icon);
			coverLabel.setIcon(icon);
			panel1.add(coverLabel);
			panel5.add(coverLabel);
			nowPlayingInfo = userName + " is playing:  " + playList.get(playListIndex).getSongTitle() + ", by:  " + playList.get(playListIndex).getArtistName() + ", from:  " + playList.get(playListIndex).getAlbumName();
			System.out.println("Sent from client:  " + nowPlayingInfo);
			
			pWriter.println(nowPlayingInfo);
			pWriter.flush();
			
			currentSong.setText("Now Playing:  " + playList.get(playListIndex).getSongTitle() + "\nby:  " + playList.get(playListIndex).getArtistName()
						+ "\nfrom:  " + playList.get(playListIndex).getAlbumName());
			
			playList.get(playListIndex).playSong();
			
		}
	}
	
	public class RandomSongListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Random random = new Random();
			playListIndex = random.nextInt(listSize );
			System.out.print(playListIndex);
			//playListIndex = (int)Math.random()*(listSize + 1);
		}
	}
	public class NextSongListener implements ActionListener{
			//Set information for nowPlayingInfo string, and play music from list.  
			//out.println(nowPlayingInfo);
			//pWriter.println(nowPlayingInfo);
			//pWriter.flush();
			
		public void actionPerformed(ActionEvent e) {
			Play.stopAudio();
			//playList.get(playListIndex).stopAudio();
			playListIndex++;
			coverLabel.setText("Album Art here");
			
		}
	}
	public class viewListListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			panel5.setVisible(true);
			panel3.setVisible(true);
			
			panel1.setVisible(false);
			panel2.setVisible(false);
			//panel3.repaint();
		}
	}
	
	public class returnActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			panel3.setVisible(false);
			panel5.setVisible(false);
			panel1.setVisible(true);
			panel2.setVisible(true);
			
		}
	}
	public void run(){
		
		try {
			System.out.println("Run method started.");
			sock = new Socket("127.0.0.1", 4500);
			isr = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isr);
			pWriter = new PrintWriter(sock.getOutputStream());
			String message = reader.readLine();
			//System.out.println("Message from server"  + message);
			while(nowPlayingInfo != null) {
				networkPlaying.append("Message received:  " + message + "\n");
				message = reader.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public static void main(String [] args) throws Exception{
		MusicPlayer player1 = new MusicPlayer();
		player1.createFrame();
		Thread t2 = new Thread(player1);
		t2.start();
		//player1.setUpNetworking();
		//player1.connectToServer();
		//Thread thread1 = new Thread(player1);
		//thread1.start();
		
	}
	
	
}
/*
 once play button is pressed, send the corresponding data from the sender.  
 Where to integrate server and client to receive and send data while music playing.  
 With music playing, no other functions are available, how to add music playing to a thread.  
 How to separate individual song information, when playing from playlist file, to send the current song information.  
 
 public void setUpNetworking(){
 	serverSock
 
 */
