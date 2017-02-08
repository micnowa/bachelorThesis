package cartessian.genetic.programmming;

import cartessian.genetic.programmming.fitness.Fitness;

public class FourPlusOneAlogrithm<T>
{
	/**
	 * 
	 */
	private GridGenerator<T> gridGenerator;
	/**
	 * 
	 */
	private Grid<T> grid;
	/**
	 * 
	 */
	private Fitness<T> fitness;
	/**
	 * 
	 */
	private Grid<T> programm;

	/**
	 * @param gridGenerator
	 * @param grid
	 * @param fitness
	 */
	public FourPlusOneAlogrithm(GridGenerator<T> gridGenerator, Grid<T> grid, Fitness<T> fitness)
	{
		this.gridGenerator = gridGenerator;
		this.grid = grid;
		this.fitness = fitness;
	}

	/**
	 * @return
	 */
	public GridGenerator<T> getGridGenerator()
	{
		return gridGenerator;
	}

	/**
	 * @param gridGenerator
	 */
	public void setGridGenerator(GridGenerator<T> gridGenerator)
	{
		this.gridGenerator = gridGenerator;
	}

	/**
	 * @return
	 */
	public Grid<T> getGrid()
	{
		return grid;
	}

	/**
	 * @param grid
	 */
	public void setGrid(Grid<T> grid)
	{
		this.grid = grid;
	}

	/**
	 * @return
	 */
	public Fitness<T> getFitness()
	{
		return fitness;
	}

	/**
	 * @param fitness
	 */
	public void setFitness(Fitness<T> fitness)
	{
		this.fitness = fitness;
	}

	/**
	 * @return
	 */
	public Grid<T> getProgramm()
	{
		return programm;
	}

	/**
	 * @param programm
	 */
	public void setProgramm(Grid<T> programm)
	{
		this.programm = programm;
	}

	/**
	 * 
	 */
	public void run()
	{
		
		Boolean solutionFound = false;
		int score[] = new int[GridGenerator.getGridNumber() + 1];
		int bestGridNumber;
		int timesRunned = 1;
		score[0] = fitness.getGridFitness(gridGenerator.getMainGrid());
		
		while(!solutionFound)
		{
			System.out.println("==================================");
			System.out.println("Times runned:	" + timesRunned);
			
			score[0] = fitness.getGridFitness(gridGenerator.getMainGrid());
			System.out.print(score[0] + "	");

			bestGridNumber = 0;
			for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
			{
				score[ii + 1] = fitness.getGridFitness(gridGenerator.getGrid()[ii]);
				System.out.print(score[ii + 1] + "	");
				if(score[ii + 1] > score[bestGridNumber]) bestGridNumber = ii + 1;
			}
			System.out.println(",best score:	" + score[bestGridNumber]);
			
			if(score[bestGridNumber] == fitness.getMaxFitness(gridGenerator.getMainGrid()))
			{
				solutionFound = true;
				if(bestGridNumber == 0) programm = gridGenerator.getMainGrid();
				else programm = gridGenerator.getGrid()[bestGridNumber - 1];
			}
			else
			{
				if(bestGridNumber == 0)
				{
					System.out.println("GG stays, as it is");
					grid = gridGenerator.getMainGrid();
					System.out.println("New GG with same MainGrid");
					gridGenerator.generateNewGrids();
				}
				else
				{
					grid = gridGenerator.getGrid()[bestGridNumber - 1];
					gridGenerator = new GridGenerator<T>(grid, gridGenerator.getProbability());
					System.out.println("New GG with grid number: " + bestGridNumber);
				}
			}
			System.out.println("New GG created!");
			timesRunned++;
			
			System.out.println();
		}
	}
	
	/**
	 * @return
	 */
	public Grid<T> generateProgramm()
	{
		run();
		return programm;
	}
}
