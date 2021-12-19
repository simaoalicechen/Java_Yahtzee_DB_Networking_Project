
//package yahtzeeFinal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;

public class Client extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;

	private JPanel contentPane = null;
	private JTextField textField = null;
	
	
	private JTextArea textAreaResponse = null;
    private Socket socket = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Client frame = new Client();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
    
    public static void main(String[] args) {
        Client c = new Client();
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setVisible(true);
      }

	/**
	 * Create the frame.
	 */
	public Client() {
		
		super("Client");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(5, 53, 440, 47);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton jBtnOpenConnection = new JButton("Open Connection");
		jBtnOpenConnection.setBounds(31, 112, 158, 29);
		contentPane.add(jBtnOpenConnection);
		
		JButton jBtnCloseConnection = new JButton("Close Connection");
		jBtnCloseConnection.setBounds(243, 112, 158, 29);
		contentPane.add(jBtnCloseConnection);
		
		JEditorPane dtrpnIfYouAre = new JEditorPane();
		dtrpnIfYouAre.setText("If you are ready to play, please type 1. Enter your name on the Game board. Once you have pressed all the buttons, your score will be recorded. You must press a button within every three rolls.");
		dtrpnIfYouAre.setBounds(5, 6, 440, 47);
		contentPane.add(dtrpnIfYouAre);
		
		textAreaResponse = new JTextArea();
		textAreaResponse.setBounds(5, 153, 439, 113);
		contentPane.add(textAreaResponse);
		textField.addActionListener(new TextFieldListener());

			  jBtnCloseConnection.addActionListener((e) -> { try { socket.close(); textAreaResponse.append("connection closed");} catch (Exception e1) {System.err.println("error"); }});
			  jBtnOpenConnection.addActionListener(new OpenConnectionListener());
//		      setSize(400, 200);
		  }
	
	  class OpenConnectionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					socket = new Socket("localhost", 8000);
					textAreaResponse.append("connected");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textAreaResponse.append("connection Failure");
				}
			}
		  }
	  
	  class TextFieldListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Socket socket = null;
			    try {
				      fromServer = new DataInputStream(socket.getInputStream());

				      // Create an output stream to send data to the server
				      toServer = new DataOutputStream(socket.getOutputStream());
				    }
				    catch (IOException ex) {
				      textAreaResponse.append(ex.toString() + '\n');
				    }
			    
			    try {
			        int message = Integer.parseInt(textField.getText().trim());
			  
			        toServer.writeInt(message);
			        toServer.flush();
			  
			        int messageSaved = fromServer.readInt();
			  
			      }
			      catch (IOException ex) {
			        System.err.println(ex);
			      }
			}
		  }
	}

