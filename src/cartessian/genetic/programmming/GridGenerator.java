package cartessian.genetic.programmming;

/**
 * @author Michał Nowaliński
 * @version 1.01
 *
 */
public class GridGenerator<T>
{
	/**
	 * Number of copied grids
	 */
	private final static int gridNumber = 4;
	/**
	 * Grid new grid are created from
	 */
	private Grid<T> mainGrid;
	/**
	 * Grids copied from main grid
	 */
	private Grid<T>[] grid;
	/**
	 * Probability of changing link between two grids
	 */
	private double probability;
	
	
	/**
	 * @return mainGrid
	 */
	public Grid<T> getMainGrid()
	{
		return mainGrid;
	}

	
	/**
	 * @param mainGrid
	 */
	public void setMainGrid(Grid<T> mainGrid)
	{
		this.mainGrid = mainGrid;
	}

	
	/**
	 * @return
	 */
	public Grid<T>[] getGrid()
	{
		return grid;
	}

	
	/**
	 * @param grid
	 */
	public void setGrid(Grid<T>[] grid)
	{
		this.grid = grid;
	}

	
	/**
	 * @return
	 */
	public double getOperationChangeProbability()
	{
		return probability;
	}

	
	/**
	 * @param operationChangeProbability
	 */
	public void setProbability(double probability)
	{
		this.probability = probability;
	}


	/**
	 * Default constructor
	 */
	public GridGenerator()
	{
		this.mainGrid = new Grid<T>();
		this.grid = new Grid[getGridnumber()];
	}

	
	/**
	 * Constructor with parameters
	 * 
	 * @param initialGrid	Main grid, new grids are based on
	 * @param linkChangeProb	Probability of changing link in new grid
	 * @param gateChangeProb	Probability of changing gate's operation in new grid
	 */
	public GridGenerator(Grid<T> initialGrid, double prob)
	{
		this.mainGrid = initialGrid;
		this.grid = new Grid[GridGenerator.getGridnumber()];
		probability = prob;
		this.generateNewGrids();
	}

	/**
	 * Generates new 4 grid based on main grid
	 */
	@SuppressWarnings("static-access") public void generateNewGrids()
	{
		for (int ii = 0; ii < this.getGridnumber(); ii++)
		{
			this.grid[ii] = new Grid<T>(this.mainGrid);
			this.grid[ii].reassignGatesOperation(this.probability);
			this.grid[ii].relinkAllGates(this.probability);
			this.grid[ii].printGrid();
		}
	}

	
	/**
	 * @return	Number of new grids
	 */
	public static int getGridnumber()
	{
		return gridNumber;
	}

}