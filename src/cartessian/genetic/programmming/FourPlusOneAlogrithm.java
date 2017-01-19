package cartessian.genetic.programmming;

import cartessian.genetic.programmming.fitness.Fitness;

public class FourPlusOneAlogrithm<T>
{
	private GridGenerator<T> gridGenerator;
	private Grid<T> grid;
	private Fitness<T> fitness;
	private double probability;

	public FourPlusOneAlogrithm(GridGenerator<T> gridGenerator, Grid<T> grid, Fitness<T> fitness)
	{
		this.gridGenerator = gridGenerator;
		this.grid = grid;
		this.fitness = fitness;
		this.probability = gridGenerator.getProbability();
	}

	public GridGenerator<T> getGridGenerator()
	{
		return gridGenerator;
	}

	public void setGridGenerator(GridGenerator<T> gridGenerator)
	{
		this.gridGenerator = gridGenerator;
	}

	public Grid<T> getGrid()
	{
		return grid;
	}

	public void setGrid(Grid<T> grid)
	{
		this.grid = grid;
	}

	public Fitness<T> getFitness()
	{
		return fitness;
	}

	public void setFitness(Fitness<T> fitness)
	{
		this.fitness = fitness;
	}

	public void run()
	{
		Boolean solutionFound = false;
		double score[] = new double[GridGenerator.getGridNumber() + 1];
		int bestGridNumber;
		int timesRunned = 1;
		score[0] = fitness.getGridFitness(gridGenerator.getMainGrid());
		
		while(!solutionFound)
		{
			System.out.println("Times runned:	" + timesRunned);
			System.out.print(score[0] + "	");

			bestGridNumber = 0;
			for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
			{
				score[ii + 1] = fitness.getGridFitness(gridGenerator.getGrid()[ii]);
				System.out.print(score[ii + 1] + "	");
				if(score[ii + 1] > score[bestGridNumber]) bestGridNumber = ii + 1;
			}
			System.out.println("Best score:	" + score[bestGridNumber]);

			if(Double.compare(score[bestGridNumber], 1.0) == 0)
			{
				solutionFound = true;
			}
			else
			{
				if(bestGridNumber == 0)
				{
					grid = new Grid<T>(gridGenerator.getMainGrid());
				}
				else
				{
					grid = new Grid<T>(gridGenerator.getGrid()[bestGridNumber - 1]);
				}
			}
			gridGenerator = new GridGenerator<T>(grid, probability);
			timesRunned++;
		}
	}
}
