package cartessian.genetic.programmming;

/**
 * @author Michał Nowaliński
 * @version 1.01
 *
 */
public class GridGenerator<T, U>
{
	/**
	 * Number of copied grids
	 */
	private final static int gridNumber = 4;
	/**
	 * Grid new grid are created from
	 */
	private Grid<T, U> mainGrid;
	/**
	 * Grids copied from main grid
	 */
	private Grid<T, U>[] grid;
	/**
	 * Probability of changing link between two grids
	 */
	private double probability;
	
	
	/**
	 * @return mainGrid
	 */
	public Grid<T, U> getMainGrid()
	{
		return mainGrid;
	}

	
	/**
	 * @param mainGrid
	 */
	public void setMainGrid(Grid<T, U> mainGrid)
	{
		this.mainGrid = mainGrid;
	}

	
	/**
	 * @return
	 */
	public Grid<T, U>[] getGrid()
	{
		return grid;
	}

	
	/**
	 * @param grid
	 */
	public void setGrid(Grid<T, U>[] grid)
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
	@SuppressWarnings("unchecked") public GridGenerator()
	{
		mainGrid = new Grid<T, U>();
		grid = new Grid[getGridnumber()];
	}

	
	/**
	 * Constructor with parameters
	 * 
	 * @param initialGrid	Main grid, new grids are based on
	 * @param linkChangeProb	Probability of changing link in new grid
	 * @param gateChangeProb	Probability of changing gate's operation in new grid
	 */
	@SuppressWarnings("unchecked") public GridGenerator(Grid<T, U> initialGrid, double prob)
	{
		mainGrid = new Grid<T,U>(initialGrid);
		grid = new Grid[GridGenerator.getGridnumber()];
		for(int ii=0; ii<GridGenerator.getGridnumber(); ii++)
		{
			grid[ii] = new Grid<T,U>(mainGrid);
		}
		probability = prob;
		generateNewGrids();
	}

	/**
	 * Generates new 4 grid based on main grid
	 */
	@SuppressWarnings("static-access") public void generateNewGrids()
	{
		for (int ii = 0; ii < this.getGridnumber(); ii++)
		{
			grid[ii] = new Grid<T, U>(mainGrid);
			grid[ii].reassignGatesOperation(probability);
			grid[ii].relinkAllGates(probability);
			grid[ii].printGrid();
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