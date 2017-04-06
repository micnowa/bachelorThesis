package cartessian.genetic.programmming.function;

import cartessian.genetic.programmming.Grid;
import cartessian.genetic.programmming.fitness.Fitness;

/**
 * Class implementing interface Fitness with type Boolean. It checks, whether
 * grid on output 0 return logic sum of all input gates. In method public int
 * getGridFitness(Grid<Boolean> grid) it sets output with all possible values.
 * If there is at least one false output 0 shall return false, if all are true
 * then true. Checks in how many cases grid return proper values. This is its
 * fitness. Method public int getMaxFitness(Grid<Boolean> grid) returns
 * maxFitness. It is equal to all possibilities, that is 2 to power of
 * inputNumber.
 * 
 * @author Michał Nowaliński
 * 
 */
public class LogicGatesFitter implements Fitness<Boolean>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cartessian.genetic.programmming.fitness.Fitness#getGridFitness(cartessian
	 * .genetic.programmming.Grid)
	 */
	@Override public int getGridFitness(Grid<Boolean> grid)
	{
		int inputNumber = grid.getInputNumber();
		int possibilities = (int) Math.pow(2, inputNumber);
		int score = 0;
		Boolean tab[] = new Boolean[inputNumber];

		for(int jj = 0; jj < inputNumber; jj++)
		{
			if(grid.getInput()[jj].getExitingGates().size() == 0) return 0;
		}
		for(int ii = 0; ii < possibilities; ii++)
		{
			tab = bytesTable(inputNumber, ii);
			grid.setInputValues(tab);
			if(grid.getRecurrentProbability() != 0)
			{
				grid.setGatesValue(false);
			}
			grid.calculateValueForEveryGate();
			if(ii != (possibilities - 1) && grid.calculateOutputValue(0) == false)
			{
				score++;
			}
			else if(ii == (possibilities - 1) && grid.calculateOutputValue(0) == true)
			{
				score++;
			}
		}

		return score;
	}

	/**
	 * Returns table of Booleans. It convert integer to binary number, where
	 * false represents 0 and true 1.
	 * 
	 * @param N
	 *            Table's length
	 * @param ii
	 *            Number
	 * @return Table of Booleans
	 * 
	 * @warning Number's length must be specified. It must size of number of
	 *          bytes enough to represent number by binary sequence.
	 */
	Boolean[] bytesTable(int N, int ii)
	{
		Boolean tab[] = new Boolean[N];
		String s = Integer.toString(ii, 2);
		if(s.length() != N)
		{
			String sPlus = new String();
			int len = N - s.length();
			for(int jj = 0; jj < len; jj++)
			{
				sPlus += "0";
			}
			s = sPlus + s;
		}

		for(int jj = 0; jj < N; jj++)
		{
			tab[jj] = false;
			if(s.charAt(jj) == '1')
			{
				tab[jj] = true;
			}
		}
		return tab;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cartessian.genetic.programmming.fitness.Fitness#getMaxFitness(cartessian
	 * .genetic.programmming.Grid)
	 */
	@Override public int getMaxFitness(Grid<Boolean> grid)
	{
		// TODO Auto-generated method stub
		return (int) Math.pow(2, grid.getInputNumber());
	}
}