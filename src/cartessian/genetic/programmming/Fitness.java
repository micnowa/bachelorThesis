package cartessian.genetic.programmming;

import java.util.Scanner;

public class Fitness {

	private int entriesNumber;
	private boolean[][] trueFalseTable;
	private Scanner keyboardValue;

	public Fitness() {
		System.out.println("Proszę wybrać, na ile wejść jest bramka!");
		keyboardValue = new Scanner(System.in);
		this.entriesNumber = keyboardValue.nextInt();
		trueFalseTable = new boolean[entriesNumber][2];
		for (int ii = 0; ii < entriesNumber; ii++) {
			for (int jj = 1; jj < 3; jj++) {
				if (jj % 2 == 0) {
					trueFalseTable[ii][jj - 1] = false;
				} else {
					trueFalseTable[ii][jj - 1] = true;
				}
			}
		}
	}

	public Fitness(int inNumber, boolean[][] innerValues) {
		super();
		this.entriesNumber = inNumber;
		this.trueFalseTable = innerValues;
	}

	public int getInNumber() {
		return entriesNumber;
	}

	public void setInNumber(int inNumber) {
		this.entriesNumber = inNumber;
	}

	// ********************************************Tablica Wejść
	public boolean[][] getInnerValues() {
		return trueFalseTable;
	}

	public void setInnerValues(boolean[][] innerValues) {
		this.trueFalseTable = innerValues;
	}

	void showFitness() {
		System.out.println("Oto funkcja fitnessu:");
		System.out.println("Ilość bramek: " + this.entriesNumber);
		System.out.println("Tablica bramek:");
		for (int ii = 0; ii < entriesNumber; ii++) {
			System.out.print(trueFalseTable[ii][0] + "  ");
			System.out.println(trueFalseTable[ii][1]);
		}
	}

	// ********************************************

	double fit(Grid grid) {
		return 0;
	}

}
