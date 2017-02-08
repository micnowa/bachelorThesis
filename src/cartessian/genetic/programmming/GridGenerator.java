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
	public double getProbability()
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
		mainGrid = new Grid<T>();
		grid = new Grid[getGridNumber()];
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param initialGrid
	 *            Main grid, new grids are based on
	 * @param linkChangeProb
	 *            Probability of changing link in new grid
	 * @param gateChangeProb
	 *            Probability of changing gate's operation in new grid
	 */
	@SuppressWarnings("unchecked") public GridGenerator(Grid<T> initialGrid, double prob)
	{
		System.out.println("Main grid creating ...");
		mainGrid = new Grid<T>(initialGrid);
		grid = new Grid[GridGenerator.getGridNumber()];
		for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
		{
			System.out.println("Grid[" + ii + "] creating");
			grid[ii] = new Grid<T>(mainGrid);
		}
		probability = prob;
		
		generateNewGrids();
		System.out.println("Generations finished!");
	}

	/**
	 * Generates new 4 grid based on main grid
	 */
	public void generateNewGrids()
	{
		System.out.println("Realinking and reassigning");
		for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
		{
			grid[ii].reassignGatesOperation(probability);
			grid[ii].relinkAllGates(probability);
		}
	}

	/**
	 * @return Number of new grids
	 */
	public static int getGridNumber()
	{
		return gridNumber;
	}

}