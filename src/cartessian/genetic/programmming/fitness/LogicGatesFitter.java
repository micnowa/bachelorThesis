package cartessian.genetic.programmming.fitness;

import java.util.LinkedList;

import cartessian.genetic.programmming.Grid;
import cartessian.genetic.programmming.function.And;
import cartessian.genetic.programmming.function.Functional;

public class LogicGatesFitter implements Fitness<Boolean>
{

	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.fitness.Fitness#getGridFitness(cartessian.genetic.programmming.Grid)
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

			Boolean value;
			LinkedList<Boolean> valueList = new LinkedList<Boolean>();
			valueList.add(tab[0]);
			valueList.add(tab[1]);
			Functional<Boolean> operator = new And();
			value = operator.calculateValue(valueList);
			for(int jj = 2; jj < inputNumber; jj++)
			{
				valueList.clear();
				valueList.add(value);
				valueList.add(tab[jj]);
				value = operator.calculateValue(valueList);
			}

			if(grid.calculateOutputValue(0) == value)
			{
				score++;
			}
		}

		return score;
	}

	/**
	 * @param N
	 * @param ii
	 * @return
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

	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.fitness.Fitness#getMaxFitness(cartessian.genetic.programmming.Grid)
	 */
	@Override public int getMaxFitness(Grid<Boolean> grid)
	{
		// TODO Auto-generated method stub
		return (int) Math.pow(2, grid.getInputNumber());
	}
}
