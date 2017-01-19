package cartessian.genetic.programmming.fitness;

import java.util.LinkedList;

import cartessian.genetic.programmming.Grid;
import cartessian.genetic.programmming.function.And;
import cartessian.genetic.programmming.function.Functional;

public class LogicGatesFitter implements Fitness<Boolean>
{

	@Override public double getGridFitness(Grid<Boolean> grid)
	{
		int inputNumber = grid.getInputNumber();
		int possibilities = (int) Math.pow(2, inputNumber);
		int score = 0;
		double fitness = 0;
		Boolean tab[] = new Boolean[inputNumber];
		for(int ii = 0; ii < possibilities; ii++)
		{
			String s = Integer.toString(ii, 2);
			if(s.length() != inputNumber)
			{
				String sPlus = new String();
				int len = inputNumber - s.length();
				for(int jj = 0; jj < len; jj++)
				{
					sPlus += "0";
				}
				s = sPlus + s;
			}

			for(int jj = 0; jj < inputNumber; jj++)
			{
				tab[jj] = false;
				if(s.charAt(jj) == '1')
				{
					tab[jj] = true;
				}
			}

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

			if(grid.calculateValueOnOutput(0) == value)
			{
				score++;
			}
		}

		fitness = (double) score / possibilities;
		return fitness;
	}
}
