import java.net.*;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TextWindowServer extends JFrame implements Runnable {
	JTextField text;
	JTextArea textArea;
	
	InputStreamReader isr;
	OutputStreamWriter outWriter;
	BufferedReader bReader;
	BufferedWriter bw;
	
	PrintWriter pWriter;
	Socket clientConn;
	ServerSocket serverSock;
	boolean connected = false;
	
	String albumInfo = "hello";
	JTextArea networkPlaying;

	
	String messageToReturn = "hello";
	/*public TextWindowServer(String songInfo, JTextArea area1) {
		this.songInfo = songInfo;
		networkPlaying = area1;
	}*/
	public void startServer() {
		text = new JTextField("Enter Text Here");
		textArea = new JTextArea(10, 20);
		//text.addActionListener(this);
		this.getContentPane().add(BorderLayout.SOUTH, text);
		this.getContentPane().add(BorderLayout.CENTER, textArea);
		this.setSize(500, 500);
		this.setTitle("Test Server Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		setUpNetworking();
	}
	public void run() {
		
		try {
			System.out.println("Thread running.");
			messageToReturn = bReader.readLine();
			//textArea.append(albumInfo + "\n");
	
			//pWriter.println(albumInfo);
			//pWriter.flush();
			if(albumInfo == null) {
				System.out.println("Album info is empty.");
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void setUpNetworking() {
		try {
			
			serverSock = new ServerSocket(4500);
			clientConn = serverSock.accept();
			System.out.println("Successful Connection.");
			
			isr = new InputStreamReader(clientConn.getInputStream());
			bReader = new BufferedReader(isr);
			
			messageToReturn = bReader.readLine();
			System.out.println("Message from client:  " + messageToReturn);
			outWriter = new OutputStreamWriter(clientConn.getOutputStream());
			
			pWriter = new PrintWriter(outWriter);
			if(messageToReturn != null) {
				System.out.println("Message to return is not empty");
			}
			while(messageToReturn != null) {
				pWriter.println(messageToReturn);
				pWriter.flush();
				
				textArea.append(messageToReturn + "\n");
				messageToReturn = bReader.readLine();
			
		}
			connected = true;
			
			if(connected) {
				pWriter.println(messageToReturn);
				pWriter.flush();
			}
			//bw = new BufferedWriter(outWriter);
			//bw.write(messageToReturn);
			System.out.println("Message sent back to client is:  " + messageToReturn);
			
			
			//pWriter = new PrintWriter(outWriter);
			//pWriter.println("Message from server");
			//pWriter.println("This is a message from the server.");
			
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}	
		
		
		/*public void actionPerformed(ActionEvent e) {
			if(connected) {
				pWriter.println(text.getText());
				pWriter.flush();
			}
			else {
				System.out.println("The server has not established a connection.");
			}
		}*/
		
		public static void main(String [] args) {
			TextWindowServer server1 = new TextWindowServer();
			server1.startServer();
			Thread t = new Thread(server1);
			t.start();
		}
	
}
