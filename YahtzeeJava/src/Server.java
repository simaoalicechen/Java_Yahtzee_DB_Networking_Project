
import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Server extends JFrame implements Runnable {
	

	// Text area for displaying contents
	JTextArea ta;
    
	public Server() {
		super("Server");
		ta = new JTextArea();
		this.add(ta);

	    setSize(400, 200);
	    Thread t = new Thread(this);
	    t.start();
	}
	
  public void run() {
	  String message = null; 
    
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);

  
        while (true) {
        	  
	        // Listen for a connection request
	        Socket socket = serverSocket.accept();
	  
	        // Create data input and output streams
	        DataInputStream inputFromClient = new DataInputStream(
	          socket.getInputStream());
	        DataOutputStream outputToClient = new DataOutputStream(
	          socket.getOutputStream());
          int response = inputFromClient.readInt();
  
          if (response == 1) {
        	  message = "You can play the game now!"; 
        	  EventQueue.invokeLater(new Runnable() {
      			public void run() {
      				
      				try {
      					GameJFrame frame = new GameJFrame();
      					frame.setVisible(true);

      				} catch (Exception e) {
      					e.printStackTrace();
      				}
      			}
      		}) ;
        	  
          }
          else {
        	  message = "You are not ready to play the game yet, come back later!"; 
          }
          
  
          // Send area back to the client
          outputToClient.writeChars(message);
  
          ta.append(message + '\n');

            Thread.sleep(1);
          }
        
      }
      catch(IOException ex) {
        ex.printStackTrace();
      } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  
  public static void main(String[] args) {
	    Server s=  new Server();
	    s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    s.setVisible(true);
	    
	  }


}
