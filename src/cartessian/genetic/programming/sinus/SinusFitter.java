package cartessian.genetic.programming.sinus;

import cartessian.genetic.programmming.Grid;
import cartessian.genetic.programmming.fitness.Fitness;

public class SinusFitter implements Fitness<Double>
{
	@Override public int getGridFitness(Grid<Double> grid)
	{
		// TODO Auto-generated method stub
		int num = 100;
		double sinus[] = sinus();
		double sinusForecast[] = new double[num];
		for(int ii = 1; ii < num; ii++)
			sinusForecast[ii] = 0;

		Double input[] = new Double[1];
		for(int ii = 1; ii < num; ii++)
		{
			input[0] = sinusForecast[ii - 1];
			grid.setInputValues(input);
			sinusForecast[ii] = grid.calculateOutputValue(0);
		}

		double score = 0;

		for(int kk = 0; kk < num; kk++)
		{
			score += Math.abs(sinus[kk] - sinusForecast[kk]);
		}
		grid.clearGatesValues();

		if(score < 20) return getMaxFitness(grid);
		return (int) score;
	}

	@Override public int getMaxFitness(Grid<Double> grid)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public static double[] sinus()
	{
		int num = 100;
		double sinus[] = new double[num];
		for(int ii = 0; ii < num; ii++)
		{
			sinus[ii] = Math.sin(2 * Math.PI * ii / 100.0);
		}
		return sinus;
	}

}
