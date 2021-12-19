
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import yahtzeegame.GameLogic;

import javax.swing.ImageIcon;

//import yahtzeegame.GameModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.Painter;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class GameJFrame extends JFrame implements Runnable {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		  
		    Client c = new Client();
		    c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    c.setVisible(true);
		    
		    Server s=  new Server();
		    s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    s.setVisible(true);
//		  
	
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				
//				try {
//					yahtzeeJFrame frame = new yahtzeeJFrame();
//					frame.setVisible(true);
//
//				} catch (Exception e) {
//					e.printStackTrace(); 
//				}
//			}
//		});
	}

	JButton rollButton = new JButton("Roll");
	private JToggleButton toggleAces;
	private JToggleButton toggleTwos;
	private JToggleButton toggleThrees;
	private JToggleButton toggleFours;
	private JToggleButton toggleSixes;
	private JToggleButton toggleFives;
	private JToggleButton toggle3ofAKind;
	private JToggleButton toggle4ofAKind;
	private JToggleButton toggleFullHouse;
	private JToggleButton toggleLargeStr;
	private JToggleButton toggleSmallStr;
	private JToggleButton toggleBtnYahtzee;
	private JToggleButton toggleChance;
//	private ImageIcon dieFace; 
	private DbConnect dbConnect;
//	private JToggleButton toggleTwos;



	/**
	 * Create the frame.
	 */
	public GameJFrame() throws SQLException {


		// Create an array of jButtons to fill with the dice buttons
		DiceArray = new JToggleButton[numberOfDice];
		
		dbConnect = new DbConnect();
		
		myDice = new Dice(numberOfDice, numberOfSides);
//		numRolls = 0; 

		
		// GUI stuff
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		// rollButton and its function 
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rollValue;

				for (int i = 0; i < numberOfDice; i++) {
					if (!holdArray[i]) {
						
						rollValue = myDice.rollDie(i);
						DiceArray[i].setText("" + rollValue);
					}
				}
				
				// Used the state methods, instead of numRoll to track which state where the roll is. 
				// Move every current state to the next state. 
				switch (currentUIState) {
				case STATE_2_ROLL_1:
					try {
						determineUIState(STATE_3_ROLL_2);
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
					break;
				case STATE_3_ROLL_2:
					try {
						determineUIState(STATE_4_ROLL_3);
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
					break;
				case STATE_4_ROLL_3:
					try {
						determineUIState(STATE_5_ROLL_DONE);
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
				}
			}
		});
		rollButton.setBounds(6, 6, 88, 78);
		contentPane.add(rollButton);

		// Click the toggle button, and it will go to the opposite position
		// Use an array of boolean to control if the jToggle buttons are enabled or not. 
		JToggleButton Dice1 = new JToggleButton("");
		Dice1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				holdArray[0] = !holdArray[0];
			}
		});
		Dice1.setBounds(102, 6, 92, 78);
		contentPane.add(Dice1);

		JToggleButton Dice2 = new JToggleButton("");
		Dice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				holdArray[1] = !holdArray[1];
			}
		});
		Dice2.setBounds(206, 6, 92, 78);
		contentPane.add(Dice2);

		JToggleButton Dice3 = new JToggleButton("");
		Dice3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				holdArray[2] = !holdArray[2];
			}
		});
		Dice3.setBounds(304, 6, 92, 78);
		contentPane.add(Dice3);

		JToggleButton Dice4 = new JToggleButton("");
		Dice4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				holdArray[3] = !holdArray[3];
			}
		});
		Dice4.setBounds(406, 6, 92, 78);
		contentPane.add(Dice4);

		JToggleButton Dice5 = new JToggleButton("");
		Dice5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				holdArray[4] = !holdArray[4];
			}
		});
		Dice5.setBounds(510, 6, 92, 78);
		contentPane.add(Dice5);

		// Create an array to hold the position of each toggle button/dice
		DiceArray[0] = Dice1;
		DiceArray[1] = Dice2;
		DiceArray[2] = Dice3;
		DiceArray[3] = Dice4;
		DiceArray[4] = Dice5;
	
		
		// The way to manage the upper score is to use the optimized scoreUpperCategory function to pass in the score you are looking for
		// And then, you will need to determine if the Dice actually have that number, and sum up all the dice that have that value. 
		toggleAces = new JToggleButton("Aces");
		toggleAces.addActionListener(new ActionListener() {
//			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					scoreUpperCategory(1);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
//				// TODO Auto-generated method stub

			}
		});
		toggleAces.setBounds(6, 96, 88, 43);
		contentPane.add(toggleAces);

		toggleTwos = new JToggleButton("Twos");
		toggleTwos.addActionListener(new ActionListener() {
//			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					scoreUpperCategory(2);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
//				// TODO Auto-generated method stub

		});
		toggleTwos.setBounds(354, 96, 88, 43);
		contentPane.add(toggleTwos);

		toggleThrees = new JToggleButton("Threes");
		toggleThrees.addActionListener(new ActionListener() {
//			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					scoreUpperCategory(3);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		toggleThrees.setBounds(6, 151, 88, 43);
		contentPane.add(toggleThrees);

		toggleFours = new JToggleButton("Fours");
		toggleFours.addActionListener(new ActionListener() {
//			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					scoreUpperCategory(4);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		toggleFours.setBounds(354, 151, 88, 43);
		contentPane.add(toggleFours);

		toggleFives = new JToggleButton("Fives");
		toggleFives.addActionListener(new ActionListener() {
//			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					scoreUpperCategory(5);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		toggleFives.setBounds(6, 204, 88, 43);
		contentPane.add(toggleFives);

		toggleSixes = new JToggleButton("Sixes");
		toggleSixes.addActionListener(new ActionListener() {
//			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					scoreUpperCategory(6);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		toggleSixes.setBounds(354, 204, 88, 43);
		contentPane.add(toggleSixes);


		txtAces = new JTextField();
		txtAces.setBounds(102, 103, 93, 26);
		contentPane.add(txtAces);
		txtAces.setColumns(10);

		txtTwos = new JTextField();
		txtTwos.setColumns(10);
		txtTwos.setBounds(477, 103, 93, 26);
		contentPane.add(txtTwos);

		txtThrees = new JTextField();
		txtThrees.setColumns(10);
		txtThrees.setBounds(102, 158, 93, 26);
		contentPane.add(txtThrees);

		txtFours = new JTextField();
		txtFours.setColumns(10);
		txtFours.setBounds(477, 158, 93, 26);
		contentPane.add(txtFours);

		txtFives = new JTextField();
		txtFives.setColumns(10);
		txtFives.setBounds(102, 211, 93, 26);
		contentPane.add(txtFives);

		txtSixes = new JTextField();
		txtSixes.setColumns(10);
		txtSixes.setBounds(477, 221, 93, 26);
		contentPane.add(txtSixes);
		
		// 3 of a kind and 4 of a kind are basically the same, they are asking if the dice array has 3 of the same kind of dice values
		// and 4 of the same kind of dice values. It is done by a hashTable in game logic. 
		// Reference: used the hashTable idea to determin the value from the Youtube video. 
		toggle3ofAKind = new JToggleButton("3 of A Kind");
		toggle3ofAKind.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int score = 0;
				game.setUsedLowerScoreCategory(THREE_OF_A_KIND, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (game.isOfAKind(myDice, 3)) {
					score = game.addAllDiceNumber(myDice);
				}
				game.setLowerScoreCategory(THREE_OF_A_KIND, score);
				lowerScoreTxtArray[THREE_OF_A_KIND].setText("" + score);

				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		toggle3ofAKind.setBounds(6, 320, 88, 43);
		contentPane.add(toggle3ofAKind);

		toggle4ofAKind = new JToggleButton("4 of A Kind");
		toggle4ofAKind.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int score = 0;
				game.setUsedLowerScoreCategory(FOUR_OF_A_KIND, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (game.isOfAKind(myDice, 4)) {
					score = game.addAllDiceNumber(myDice);
				}
				game.setLowerScoreCategory(FOUR_OF_A_KIND, score);
				lowerScoreTxtArray[FOUR_OF_A_KIND].setText("" + score);

				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}

		});
		toggle4ofAKind.setBounds(354, 320, 88, 43);
		contentPane.add(toggle4ofAKind);
		
		// Reference: 
		// Used the hashTable idea from the Youtube video. 
		// Similar to 3 of a kind, and 4 of a kind, but you need to make sure exactly 2 of them are the same, and exactly 3 of them are the same. 
		toggleFullHouse = new JToggleButton("Full House");
		toggleFullHouse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int score = 0;
				game.setUsedLowerScoreCategory(FULL_HOUSE, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}

				if (game.isFullHouse(myDice)) {
					score = 25;
				}

				game.setLowerScoreCategory(FULL_HOUSE, score);
				lowerScoreTxtArray[FULL_HOUSE].setText("" + score);
				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}

			}
		});
		toggleFullHouse.setBounds(6, 375, 88, 43);
		contentPane.add(toggleFullHouse);

		// Reference: Youtube video. 
		// This is the hardest, you need to make sure the dice array must have 3 and 4, and then 
		// check if there are 2 and 5, or 1 and 2, or 5 and 6. 
		toggleSmallStr = new JToggleButton("Small Straight");
		toggleSmallStr.addActionListener(new ActionListener() {
//			private Object[] lowerScoreTxtArray;

			public void actionPerformed(ActionEvent e) {
				int score = 0;

				game.setUsedLowerScoreCategory(SMALL_STRAIGHT, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}

				if (game.isSmallStraight(myDice)) {
					score = 30;
				}

				game.setLowerScoreCategory(SMALL_STRAIGHT, score);

				lowerScoreTxtArray[SMALL_STRAIGHT].setText("" + score);

				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				

			}
		});
		toggleSmallStr.setBounds(6, 424, 88, 43);
		contentPane.add(toggleSmallStr);

		// Similar to small straight, but easier, you only need to make sure every number appears once in the dice array.
		toggleLargeStr = new JToggleButton("Larges Straight");
		toggleLargeStr.addActionListener(new ActionListener() {
//			private Object[] lowerScoreTxtArray;

			public void actionPerformed(ActionEvent e) {
				int score = 0;
				game.setUsedLowerScoreCategory(LARGE_STRAIGHT, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}

				if (game.isLargeStraight(myDice)) {
					score = 40;
				}
				game.setLowerScoreCategory(LARGE_STRAIGHT, score);

				lowerScoreTxtArray[LARGE_STRAIGHT].setText("" + score);

				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				


			}
		});
		toggleLargeStr.setBounds(354, 375, 88, 43);
		contentPane.add(toggleLargeStr);

		// The same logic as is5OfKind
		toggleBtnYahtzee = new JToggleButton("YAHTZEE");
		toggleBtnYahtzee.addActionListener(new ActionListener() {
//			private Object[] lowerScoreTxtArray;

			public void actionPerformed(ActionEvent e) {
				int score = 0;

				game.setUsedLowerScoreCategory(YAHTZEE, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				if (game.isOfAKind(myDice, 5)) {
					score = 50;
				}
				game.setLowerScoreCategory(YAHTZEE, score);
				lowerScoreTxtArray[YAHTZEE].setText("" + score);
				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				

			}
		});
		toggleBtnYahtzee.setBounds(354, 424, 88, 43);
		contentPane.add(toggleBtnYahtzee);

		txt3ofAKind = new JTextField();
		txt3ofAKind.setColumns(10);
		txt3ofAKind.setBounds(102, 327, 93, 26);
		contentPane.add(txt3ofAKind);

		txt4ofAKind = new JTextField();
		txt4ofAKind.setColumns(10);
		txt4ofAKind.setBounds(477, 327, 93, 26);
		contentPane.add(txt4ofAKind);

		txtFullHouse = new JTextField();
		txtFullHouse.setColumns(10);
		txtFullHouse.setBounds(101, 382, 93, 26);
		contentPane.add(txtFullHouse);

		txtSmallStr = new JTextField();
		txtSmallStr.setColumns(10);
		txtSmallStr.setBounds(102, 431, 93, 26);
		contentPane.add(txtSmallStr);

		txtLargeStr = new JTextField();
		txtLargeStr.setColumns(10);
		txtLargeStr.setBounds(477, 392, 93, 26);
		contentPane.add(txtLargeStr);

		txtYahtzee = new JTextField();
		txtYahtzee.setColumns(10);
		txtYahtzee.setBounds(477, 431, 93, 26);
		contentPane.add(txtYahtzee);

		toggleChance = new JToggleButton("Chance");
		toggleChance.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int score = 0;
				game.setUsedLowerScoreCategory(CHANCE, true);
				try {
					determineUIState(STATE_6_SCORING);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				score = game.addAllDiceNumber(myDice);
				game.setLowerScoreCategory(CHANCE, score);
				lowerScoreTxtArray[CHANCE].setText("" + score);
				try {
					showTotalsAndAdvanceTurn();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}

		});

		toggleChance.setBounds(354, 468, 88, 43);
		contentPane.add(toggleChance);

		txtChance = new JTextField();
		txtChance.setColumns(10);
		txtChance.setBounds(477, 475, 93, 26);
		contentPane.add(txtChance);

		

		// Once the game is over, your score will be recorded to the database. 
		JButton jButtonNewGame = new JButton("New Game");
		jButtonNewGame.addActionListener(new ActionListener() {
//				private Object[] lowerScoreTxtArray;

			public void actionPerformed(ActionEvent e) {
				try {
					determineUIState(STATE_1_RESET);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				try {
					determineUIState(STATE_2_ROLL_1);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		jButtonNewGame.setBounds(212, 211, 117, 29);
		contentPane.add(jButtonNewGame);

		JLabel jLabelTotalUpperScore = new JLabel("Total Upper Score");
		jLabelTotalUpperScore.setBounds(316, 259, 123, 18);
		contentPane.add(jLabelTotalUpperScore);

		JLabel jLabelBonus = new JLabel("Bonus if > 63");
		jLabelBonus.setBounds(6, 259, 111, 18);
		contentPane.add(jLabelBonus);

		txtBonus = new JTextField();
		txtBonus.setColumns(10);
		txtBonus.setBounds(102, 255, 93, 26);
		contentPane.add(txtBonus);

		txtUpperScore = new JTextField();
		txtUpperScore.setColumns(10);
		txtUpperScore.setBounds(477, 259, 93, 26);
		contentPane.add(txtUpperScore);

		txtLowerScore = new JTextField();
		txtLowerScore.setBounds(129, 475, 130, 26);
		contentPane.add(txtLowerScore);
		txtLowerScore.setColumns(10);

		JLabel jLabelTotalLowerScore = new JLabel("Total Lower Score");
		jLabelTotalLowerScore.setBounds(6, 479, 111, 18);
		contentPane.add(jLabelTotalLowerScore);

		JLabel jLabelGrandTotal = new JLabel("Grand Total");
		jLabelGrandTotal.setBounds(135, 526, 111, 18);
		contentPane.add(jLabelGrandTotal);

		txtGrandTotal = new JTextField();
		txtGrandTotal.setColumns(10);
		txtGrandTotal.setBounds(292, 522, 130, 26);
		contentPane.add(txtGrandTotal);

		// 1-index
		upperScoreBtnArray = new JToggleButton[GameLogic.NUM_UPPER_SCORE_CATEGORY + 1];
		upperScoreBtnArray[1] = this.toggleAces;
		upperScoreBtnArray[2] = this.toggleTwos;
		upperScoreBtnArray[3] = this.toggleThrees;
		upperScoreBtnArray[4] = this.toggleFours;
		upperScoreBtnArray[5] = this.toggleFives;
		upperScoreBtnArray[6] = this.toggleSixes;

		upperScoreTxtArray = new JTextField[GameLogic.NUM_UPPER_SCORE_CATEGORY + 1];

		upperScoreTxtArray[1] = this.txtAces;
		upperScoreTxtArray[2] = this.txtTwos;
		upperScoreTxtArray[3] = this.txtThrees;
		upperScoreTxtArray[4] = this.txtFours;
		upperScoreTxtArray[5] = this.txtFives;
		upperScoreTxtArray[6] = this.txtSixes;

		lowerScoreBtnArray = new JToggleButton[GameLogic.NUM_LOWER_SCORE_CATEGORY];

		lowerScoreBtnArray[THREE_OF_A_KIND] = this.toggle3ofAKind;
		lowerScoreBtnArray[FOUR_OF_A_KIND] = this.toggle4ofAKind;
		lowerScoreBtnArray[FULL_HOUSE] = this.toggleFullHouse;
		lowerScoreBtnArray[SMALL_STRAIGHT] = this.toggleSmallStr;
		lowerScoreBtnArray[LARGE_STRAIGHT] = this.toggleLargeStr;
		lowerScoreBtnArray[YAHTZEE] = this.toggleBtnYahtzee;
		lowerScoreBtnArray[CHANCE] = this.toggleChance;

		lowerScoreTxtArray = new JTextField[GameLogic.NUM_LOWER_SCORE_CATEGORY];

		lowerScoreTxtArray[THREE_OF_A_KIND] = this.txt3ofAKind;
		lowerScoreTxtArray[FOUR_OF_A_KIND] = this.txt4ofAKind;
		lowerScoreTxtArray[FULL_HOUSE] = this.txtFullHouse;
		lowerScoreTxtArray[SMALL_STRAIGHT] = this.txtSmallStr;
		lowerScoreTxtArray[LARGE_STRAIGHT] = this.txtLargeStr;
		lowerScoreTxtArray[YAHTZEE] = this.txtYahtzee;
		lowerScoreTxtArray[CHANCE] = this.txtChance;

		textField = new JTextField();
		textField.setBounds(212, 156, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText("Your Name");

		lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(241, 108, 78, 16);
		contentPane.add(lblNewLabel);
		
		JLabel JLabelLower = new JLabel("Lower Level");
		JLabelLower.setBounds(226, 305, 117, 16);
		contentPane.add(JLabelLower);
		
		JLabel JLabelUpper = new JLabel("Upper Level");
		JLabelUpper.setBounds(216, 85, 117, 16);
		contentPane.add(JLabelUpper);

		game = new GameLogic();

		holdArray = new boolean[numberOfDice]; 
//		determineUIState(STATE_1_RESET);
//		determineUIState(STATE_2_ROLL_1);
//		holdArray = new boolean[numberOfDice]; 
		determineUIState(STATE_1_RESET);
		determineUIState(STATE_2_ROLL_1);


	}

	
	// Seperating the game state and GUI design
	// Reference: Added gameLogics' methods and enabled different toggleButtons/buttons to refresh the GUI. 
	public void determineUIState(int nextState) throws SQLException {
		switch (nextState) {
		case STATE_1_RESET:

			game.clearAllUpperScoringCats();
			game.clearAllLowerScoringCats();
			game.clearUsedScoringCats();
			this.clearAllTextBoxes(); 
//			game.resetTurn();
//			resetScoreToggleButtons()
//			resetScoreButtons()
			dbConnect.InsertUser(textField.getText(), game.getGrandTotal());
			System.out.println(textField.getText()+" "+game.getGrandTotal());
			break;

		case STATE_2_ROLL_1:
			rollButton.setEnabled(true);

			clearAllDiceValue();

			clearDiceArray();
			clearAllScoreButtons();

			// enableDices();
//			disableAllScoreButtons() 
			// rollButton.setEnabled(true);
			
			break;

		case STATE_3_ROLL_2:
			enableAllDicesToStart();
			// setDicesEnaledState(true);
			enableAllUnusedScoreButtons();
			break;

		case STATE_4_ROLL_3:
			// rollButton.setEnabled(false);
//			resetTurn()
			break;

		case STATE_5_ROLL_DONE:
			// setDicesEnaledState(false);
			// rollButton.setEnabled(false);
			rollButton.setEnabled(false);

			break;
		case STATE_6_SCORING:
			clearAllScoreButtons();
//			resetScoreToggleButtons()
//			resetScoreButtons()
			game.nextTurn();
			break;
				
		case GAME_OVER:
			JOptionPane.showMessageDialog(null, "Your score was " + game.getGrandTotal());
			break; 
		default:
			JOptionPane.showMessageDialog(this, "Invalid UI state.");
			break;
		}
		currentUIState = nextState;
	}

	public void calculateGrandTotal() {
		game.updateTotals();
		txtBonus.setText("" + game.getBonus());
		txtUpperScore.setText("" + game.getSumUpperScores());
		txtLowerScore.setText("" + game.getSumLowerScores());
		txtGrandTotal.setText("" + game.getGrandTotal());
	}

	private void clearAllDiceValue() {
		int i;
		for (i = 0; i < numberOfDice; i++) {
			DiceArray[i].setText("");
			DiceArray[i].setEnabled(false);
			DiceArray[i].setSelected(false);
		}
	}

	private void clearDiceArray() {
		int i;
		for (i = 0; i < numberOfDice; i++) {
			holdArray[i] = false;
		}
	}


	public void showTotalsAndAdvanceTurn() throws SQLException {
		this.calculateGrandTotal();
		if (game.getCurrentTurnNum() <= GameLogic.MAX_NUM_TURNS) {
			determineUIState(STATE_2_ROLL_1);
		} else {
			determineUIState(GAME_OVER);
		}
	}

	
	private void enableAllDicesToStart() {
		int i;
		for (i = 0; i < numberOfDice; i++) {
			DiceArray[i].setEnabled(true);
		}
	}
	
	
	
	private void clearAllScoreButtons() {

		int i;

		for (i = 1; i <= GameLogic.NUM_UPPER_SCORE_CATEGORY; i++) {
			this.upperScoreBtnArray[i].setEnabled(false);
		}

		for (i = 0; i < GameLogic.NUM_LOWER_SCORE_CATEGORY; i++) {
			this.lowerScoreBtnArray[i].setEnabled(false);
		}
	}

	
	// Reference: Used the new usedUpperScoringCatState and usedLowerScoringCatState array to enable the buttons after each round, so that they will be used 
	// again in the next round. 
	// Also Change the buttons from JButton to JToggle button so they have the enabled effect. 
	private void enableAllUnusedScoreButtons() {
		int i;
		for (i = 1; i <= GameLogic.NUM_UPPER_SCORE_CATEGORY; i++) {
			if (!game.getUsedUpperScoringCatState(i)) {
				this.upperScoreBtnArray[i].setEnabled(true);
			}
		}

		for (i = 0; i < GameLogic.NUM_LOWER_SCORE_CATEGORY; i++) {
			if (!game.getUsedLowerScoringCatState(i)) {
				this.lowerScoreBtnArray[i].setEnabled(true);
			}
		}
	}

	
	
	private void clearAllTextBoxes() {
		int i;
		for (i = 1; i <= game.NUM_UPPER_SCORE_CATEGORY; i++) {
			upperScoreTxtArray[i].setText("");
//			System.out.println("555555555555555");
		}
		txtBonus.setText("");
		txtUpperScore.setText("");

		for (i = 0; i < game.NUM_LOWER_SCORE_CATEGORY; i++) {
			lowerScoreTxtArray[i].setText("");
		}
		txtLowerScore.setText("");
		txtGrandTotal.setText("");

	}

	private void scoreUpperCategory(int category) throws SQLException {
		int score = 0;
		int i;

		game.setUsedUpperScoreCategory(category, true);
		determineUIState(STATE_6_SCORING);
		for (i = 0; i < numberOfDice; i++) {
			if (myDice.getDieValue(i) == category) {
				score += category;
			}
		}
		game.setUpperScoreCategory(category, score);

		this.upperScoreTxtArray[category].setText("" + score);

		showTotalsAndAdvanceTurn(); 
		
		// Replaced by showTotalsAndAdvanceTurn
//		this.showTotals();
//
//		if (game.getCurrentTurnNum() < GameModel.MAX_NUM_TURNS) {
//			manageUIState(BEFORE_1st_ROLL);
//		} else {
////		System.out.println("1111111111111");
//			manageUIState(GAME_OVER);
//		}
		
		
		
		
	}
	
	
	
	
	// Old Utility functions
	
//	public void resetScoreToggleButtons() {
//		int i;
//		for (i = 1; i <= game.NUM_UPPER_SCORE_CATS; i++) {
//			upperScoreBtnArray[i].setEnabled(false);
//		}
//		for (i = 0; i <= game.NUM_LOWER_SCORE_CATS; i++) {
//			lowerScoreBtnArray[i].setEnabled(false);
//		}
//	}

	

//	public void resetTurn() {
//
//	}
	
	
//	private void resetScoreButtons() {
//		int i;
//		for (i = 0; i < GameModel.NUM_UPPER_SCORE_CATS; i++) {
//			
//		}
//	}
//	
//	private void setDicesEnaledState(boolean isEnabled) {
//		int i; 
//		for (i = 0; i < myDice.getNumDice(); i ++) {
//			DiceArray[i].setEnabled(isEnabled);
//		}
//	}

//	private void disableAllScoreButtons() {
//		
//	int i; 
//	
//	for (i = 1; i <= GameModel.NUM_UPPER_SCORE_CATS; i++) {
//		this.upperScoreButtonArray[i].setEnabled(false);
//	}
//	
//	for (i = 0; i < GameModel.NUM_LOWER_SCORE_CATS; i++) {
//		this.lowerScoreButtonArray[i].setEnabled(false);
//	}
//}
	
	
	// GUI State: 1-7, separating state and game design
	public final static int STATE_1_RESET = 1;
	public final static int STATE_2_ROLL_1 = 2;
	public final static int STATE_3_ROLL_2 = 3;
	public final static int STATE_4_ROLL_3 = 4;
	public final static int STATE_5_ROLL_DONE = 5;
	public final static int STATE_6_SCORING = 6;
	public final static int GAME_OVER = 7;

	
	// Lower score category components: 0-6
	public static final int THREE_OF_A_KIND = 0;
	public static final int FOUR_OF_A_KIND = 1;
	public static final int FULL_HOUSE = 2;
	public static final int SMALL_STRAIGHT = 3;
	public static final int LARGE_STRAIGHT = 4;
	public static final int YAHTZEE = 5;
	public static final int CHANCE = 6;


	private Dice myDice;
	private JToggleButton[] DiceArray;
//	private int numRolls; 	
	public static final int numberOfDice = 5;
	public static final int numberOfSides = 6;

	private JToggleButton[] upperScoreBtnArray;
	private JToggleButton[] lowerScoreBtnArray;
	private JTextField[] lowerScoreTxtArray;
	private JTextField[] upperScoreTxtArray;
	private boolean[] holdArray;
	private GameLogic game;


	private int currentUIState;
	private JTextField txtAces;
	private JTextField txtTwos;
	private JTextField txtThrees;
	private JTextField txtFours;
	private JTextField txtFives;
	private JTextField txtSixes;
	private JTextField txt3ofAKind;
	private JTextField txt4ofAKind;
	private JTextField txtFullHouse;
	private JTextField txtSmallStr;
	private JTextField txtLargeStr;
	private JTextField txtYahtzee;
	private JTextField txtChance;
	private JTextField txtBonus;
	private JTextField txtUpperScore;
	private JTextField txtLowerScore;
	private JTextField txtGrandTotal;
	private JTextField textField;
	private JLabel lblNewLabel;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
