

import java.util.Random;

import javax.swing.ImageIcon;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Dice {

	public Dice(int numDice, int numSides) {
		this.numDice = numDice;
		this.numSides = numSides;

		// Create an array to hold all the dice.
		myDice = new Die[numDice];

		int i;

		// Loop through the array creating each individual die.
		for (i = 0; i < numDice; i++) {
			myDice[i] = new Die(numSides);
		}
		rand = new Random();
	}
	
//  public Dice(int numDice, int numSides){
//  this.numDice = numDice;
//  this.numSides = numSides;
//  // Create an array to hold all the dice
//  this.myDice = new Die[numDice];
//  int i ;
//
//  // Loop through the array creating each individual die
//  for (i = 0; i < numDice; i++){
//      myDice[i] = new Die(numSides);
//  }
//
//
//}

	public int getDieValue(int i) {
//		faceValue = myDice[i].getValue(); 
//		dieFace = new ImageIcon(getClass().getResource("die" + face + ".png")); 
		return myDice[i].getValue();
	}

	public int rollDice() {
		int sum = 0;
		for (int i = 0; i < getNumDice(); i++) {
			sum += myDice[i].roll(rand);
		}
		return sum;
	}
	
	//
//  public int rollDie(){
//
//      int i, sum = 0;
//
//      // Loop through the array creating each individual die
//      for (i = 0; i < numDice; i++){
//          sum += myDice[i].roll(rand); 
//      }
//      return sum;
//  }

	public int rollDie(int i) {
		return myDice[i].roll(rand);
	}

	public int[] buildTable() {
		int i;
		int[] hashTableFrequency = new int[getNumSides() + 1];

		for (i = 0; i < getNumDice(); i++) {
			hashTableFrequency[myDice[i].getValue()]++;
		}

		return hashTableFrequency;

	}

	private int numDice;
	private int numSides;
	private Die[] myDice;
	private Random rand;
	private int faceValue; 

	public int getNumDice() {
		return numDice;
	}

	public void setNumDice(int numDice) {
		this.numDice = numDice;
	}

	public int getNumSides() {
		return numSides;
	}

	public void setNumSides(int numSides) {
		this.numSides = numSides;
	}

}
