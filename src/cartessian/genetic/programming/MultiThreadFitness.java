package cartessian.genetic.programming;

import cartessian.genetic.programming.fitness.Fitness;

public class MultiThreadFitness<T> implements Runnable
{
	private Fitness<T> fitness;

	private GridGenerator<T> gridGenerator;

	private static int ii = -1;

	private static int score[] = new int[GridGenerator.getGridNumber() + 1];

	public MultiThreadFitness(GridGenerator<T> gridGenerator, Fitness<T> fitness)
	{
		this.fitness = fitness;
		this.gridGenerator = gridGenerator;
	}

	public Fitness<T> getFitness()
	{
		return fitness;
	}

	public void setFitness(Fitness<T> fitness)
	{
		this.fitness = fitness;
	}

	public GridGenerator<T> getGridGenerator()
	{
		return gridGenerator;
	}

	public void setGridGenerator(GridGenerator<T> gridGenerator)
	{
		this.gridGenerator = gridGenerator;
	}

	public static int[] getScore()
	{
		return score;
	}

	public static void setScore(int[] score)
	{
		MultiThreadFitness.score = score;
	}

	@Override public void run()
	{
		synchronized (this)
		{
			// System.out.println("Before ii=	" + ii);
			ii++;
			if(ii == GridGenerator.getGridNumber()) ii = 0;
			// System.out.println("After ii=	" + ii);
		}
		score[ii + 1] = fitness.getGridFitness(gridGenerator.getGrid()[ii]);
	}
}