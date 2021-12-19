

public class GameLogic {
	public static final int BONUS = 35;
	public final static int NUM_UPPER_SCORE_CATEGORY = 6;
	public final static int NUM_LOWER_SCORE_CATEGORY = 7;
	public static final int MAX_NUM_TURNS = NUM_UPPER_SCORE_CATEGORY + NUM_LOWER_SCORE_CATEGORY;

	public GameLogic() {
		
		countCurrentTurn = 1;
		upperScoreTotal = 0;
		bonus = 0;
		lowerScoreTotal = 0;
		grandTotal = 0;
		
		
		// Add 1 to make it in sync with the Aces, Twos, Threes etc. 
		upperScoreIndexes = new int[NUM_UPPER_SCORE_CATEGORY + 1];
		lowerScoreIndexes = new int[NUM_LOWER_SCORE_CATEGORY];

		// Keep track of the scores if they are used. If they are used, you can't use
		// them again.
		// Reference: to use two pairs of separate arrays to store the upper and lower used scores from the Youtube video mentione in my description. 
		usedUpperScoreCategories = new boolean[NUM_UPPER_SCORE_CATEGORY + 1];
		usedLowerScoreCategories = new boolean[NUM_LOWER_SCORE_CATEGORY];
	}


	public void clearAllUpperScoringCats() {
		int i;
		for (i = 1; i < NUM_UPPER_SCORE_CATEGORY; i++) {
			upperScoreIndexes[i] = 0;
		}
	}

	public void clearAllLowerScoringCats() {
		int i;
		for (i = 0; i < NUM_LOWER_SCORE_CATEGORY; i++) {
			lowerScoreIndexes[i] = 0;
		}
	}

	public void clearUsedScoringCats() {
		int i;
		for (i = 1; i <= NUM_UPPER_SCORE_CATEGORY; i++) {
			usedUpperScoreCategories[i] = false;
		}
		for (i = 0; i < NUM_LOWER_SCORE_CATEGORY; i++) {
			usedLowerScoreCategories[i] = false;
		}
	}

	
	// Optimized to isOfAKind
//	public boolean is3ofaKind(Dice myDice) {
//		int[] hashTableFrequency;
//		hashTableFrequency = myDice.buildTable();
//		boolean foundOfAKind = false;
//		int i;
//
//		// loop through the frequency table
//		for (i = 1; i <= myDice.getNumSides(); i++) {
//			if (hashTableFrequency[i] >= 3) {
//				foundOfAKind = true;
//			}
//		}
//		return foundOfAKind;
//	}
//
//	public boolean is4ofaKind(Dice myDice) {
//		int[] hashTableFrequency;
//		hashTableFrequency = myDice.buildTable();
//		boolean foundOfAKind = false;
//		int i;
//
//		// loop through the frequency table
//		for (i = 1; i <= myDice.getNumSides(); i++) {
//			if (hashTableFrequency[i] >= 4) {
//				foundOfAKind = true;
//			}
//		}
//		return foundOfAKind;
//	}

//	
	public boolean isOfAKind(Dice myDice, int kind) {
		int[] hashTableFrequency;
		hashTableFrequency = myDice.buildTable();
		boolean foundOfAKind = false;
		int i;

		// loop through the frequency table
		for (i = 1; i <= myDice.getNumSides(); i++) {
			if (hashTableFrequency[i] >= kind) {
				foundOfAKind = true;
			}
		}
		return foundOfAKind;
	}

//	
	public boolean isFullHouse(Dice myDice) {
		boolean foundThree = false;
		boolean foundTwo = false;
		int[] hashTableFrequency;
		hashTableFrequency = myDice.buildTable();
		int i;

		for (i = 1; i <= myDice.getNumSides(); i++) {
			if (hashTableFrequency[i] == 3) {
				foundThree = true;
			}
			if (hashTableFrequency[i] == 2) {
				foundTwo = true;
			}
		}
		return foundThree && foundTwo;

	}

	public boolean isLargeStraight(Dice myDice) {
		int[] hashTableFrequency;
		hashTableFrequency = myDice.buildTable();

		return hashTableFrequency[2] == 1 && hashTableFrequency[3] == 1 && hashTableFrequency[4] == 1 && hashTableFrequency[5] == 1;
	}

	public boolean isSmallStraight(Dice myDice) {
		boolean returnValue;

		int[] frequencyTable;
		frequencyTable = myDice.buildTable();

		int i;
		for (i = 1; i <= myDice.getNumSides(); i++) {
			if (frequencyTable[i] > 1) {
				frequencyTable[i] = 1;
			}
		}
		
		
		// 3 and 4 must appear in a small straight, 2 and 5, 1 and 2, 5 and 6 are possible combinations. 
		if (frequencyTable[3] == 1 && frequencyTable[4] == 1) {
			if (frequencyTable[1] == 1 && frequencyTable[2] == 1) {
				returnValue = true;
			} else if (frequencyTable[2] == 1 && frequencyTable[5] == 1) {
				returnValue = true;
			} else if (frequencyTable[5] == 1 && frequencyTable[6] == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}
		} else {
			returnValue = false;
		}

		return returnValue;

	}

	// Setters and Getters
	
	public void setLowerScoreCategory(int index, int score) {
		lowerScoreIndexes[index] = score;
	}

	public void setUpperScoreCategory(int index, int score) {
		upperScoreIndexes[index] = score;
	}

	public void setUsedLowerScoreCategory(int index, boolean used) {
		usedLowerScoreCategories[index] = used;
	}

	public void setUsedUpperScoreCategory(int index, boolean used) {
		usedUpperScoreCategories[index] = used;
	}


	public boolean getUsedUpperScoringCatState(int i) {
		return usedUpperScoreCategories[i];
	}

	public boolean getUsedLowerScoringCatState(int i) {
		return usedLowerScoreCategories[i];
	}

//	
	public int getCurrentTurnNum() {
		return countCurrentTurn;
	}

	public void nextTurn() {
		countCurrentTurn++;
	}

	public void resetTurn() {
		countCurrentTurn = 1;
	}
	
	
	// Add both upperLevelScore, lowerLevelScore, and bonus if applicable.
	public void updateTotals() {
		int i;
		bonus = 0;
		upperScoreTotal = 0;
		lowerScoreTotal = 0;
		grandTotal = 0;

		for (i = 1; i <= NUM_UPPER_SCORE_CATEGORY; i++) {
			upperScoreTotal += upperScoreIndexes[i];
		}
		if (getSumUpperScores() >= 63) {
			bonus = 35;
		}

		upperScoreTotal += getBonus();

		for (i = 0; i < NUM_LOWER_SCORE_CATEGORY; i++) {
			lowerScoreTotal += lowerScoreIndexes[i];
		}
		grandTotal = getSumUpperScores() + getSumLowerScores();
	}
	
	public int addAllDiceNumber(Dice myDice) {
		int i;
		int sum = 0;
		for (i = 0; i < myDice.getNumDice(); i++) {
			sum += myDice.getDieValue(i);
		}
		return sum;
	}

	private int[] upperScoreIndexes;
	private int[] lowerScoreIndexes;
	private boolean[] usedUpperScoreCategories;
	private boolean[] usedLowerScoreCategories;

	private int countCurrentTurn;

	private int bonus;
	private int upperScoreTotal;
	private int lowerScoreTotal;
	private int grandTotal;

	public int getSumUpperScores() {
		return upperScoreTotal;
	}

	public int getBonus() {
		return bonus;
	}

	public int getSumLowerScores() {
		return lowerScoreTotal;
	}

	public int getGrandTotal() {
		return grandTotal;
	}

}
