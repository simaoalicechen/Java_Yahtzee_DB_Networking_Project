

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Die {
	private int numSides;
	private int value;

	public Die(int numSides) {
		this.numSides = numSides;
	}

	public Die() {
		numSides = 0;
	}

	public int roll(Random rand) {
		setValue(rand.nextInt(getNumSides()) + 1);
		return getValue();
	}

	public int getNumSides() {
		return numSides;
	}

	public void setNumSides(int numSides) {
		this.numSides = numSides;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
