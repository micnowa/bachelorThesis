package cartessian.genetic.programmming;

/**
 * Class creating 4 new grids based on main Grid. It takes main grid and draws
 * new links between gates and reassigns functions with given probability.
 * 
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
	 * @return mainGrid
	 */
	public Grid<T> getMainGrid()
	{
		return mainGrid;
	}

	/**
	 * @param mainGrid	mainGrid
	 */
	public void setMainGrid(Grid<T> mainGrid)
	{
		this.mainGrid = mainGrid;
	}

	/**
	 * @return	grids table
	 */
	public Grid<T>[] getGrid()
	{
		return grid;
	}

	/**
	 * @param grid	grid
	 */
	public void setGrid(Grid<T>[] grid)
	{
		this.grid = grid;
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
	@SuppressWarnings("unchecked") public GridGenerator(Grid<T> initialGrid)
	{
		System.out.println("Main grid creating ...");
		mainGrid = new Grid<T>(initialGrid);
		grid = new Grid[GridGenerator.getGridNumber()];
		for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
		{
			System.out.println("Grid[" + ii + "] creating");
			grid[ii] = new Grid<T>(mainGrid);
		}

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
			grid[ii].reassignGatesOperation();
			grid[ii].relinkAllGates();
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