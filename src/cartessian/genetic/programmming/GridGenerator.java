package cartessian.genetic.programmming;

/**
 * @author Michał Nowaliński
 *
 */
public class GridGenerator
{
	/**
	 * Number of copied grids
	 */
	private final static int gridNumber = 4;
	/**
	 * Grid new grid are created from
	 */
	private Grid mainGrid;
	/**
	 * Grids copied from main grid
	 */
	private Grid[] grid;
	/**
	 * Probability of changing link between two grids
	 */
	private double linkChangeProbability;
	/**
	 * Probability of changing grid's operation
	 */
	private double operationChangeProbability;
	
	
	/**
	 * @return mainGrid
	 */
	public Grid getMainGrid()
	{
		return mainGrid;
	}

	
	/**
	 * @param mainGrid
	 */
	public void setMainGrid(Grid mainGrid)
	{
		this.mainGrid = mainGrid;
	}

	
	/**
	 * @return
	 */
	public Grid[] getGrid()
	{
		return grid;
	}

	
	/**
	 * @param grid
	 */
	public void setGrid(Grid[] grid)
	{
		this.grid = grid;
	}

	
	/**
	 * @return
	 */
	public double getLinkChangeProbability()
	{
		return linkChangeProbability;
	}

	
	/**
	 * @param linkChangeProbability
	 */
	public void setLinkChangeProbability(double linkChangeProbability)
	{
		this.linkChangeProbability = linkChangeProbability;
	}

	
	/**
	 * @return
	 */
	public double getOperationChangeProbability()
	{
		return operationChangeProbability;
	}

	
	/**
	 * @param operationChangeProbability
	 */
	public void setOperationChangeProbability(double operationChangeProbability)
	{
		this.operationChangeProbability = operationChangeProbability;
	}


	/**
	 * Default constructor
	 */
	public GridGenerator()
	{
		this.mainGrid = new Grid();
		this.grid = new Grid[getGridnumber()];
	}

	
	/**
	 * Constructor with parameters
	 * 
	 * @param initialGrid	Main grid, new grids are based on
	 * @param linkChangeProb	Probability of changing link in new grid
	 * @param gateChangeProb	Probability of changing gate's operation in new grid
	 */
	public GridGenerator(Grid initialGrid, double linkChangeProb, double gateChangeProb)
	{
		this.mainGrid = new Grid(initialGrid);
		this.grid = new Grid[GridGenerator.getGridnumber()];
		linkChangeProbability = linkChangeProb;
		operationChangeProbability = gateChangeProb;
		this.generateNewGrids();
	}

	/**
	 * Generates new 4 grid based on main grid
	 */
	@SuppressWarnings("static-access") public void generateNewGrids()
	{
		for (int ii = 0; ii < this.getGridnumber(); ii++)
		{
			this.grid[ii] = new Grid(this.mainGrid);
			this.grid[ii].reassignGatesOperation(this.operationChangeProbability);
			this.grid[ii].relinkAllGatesInGrid(this.linkChangeProbability);
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