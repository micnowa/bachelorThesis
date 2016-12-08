package cartessian.genetic.programmming;

public class GateGenerator
{
	private final static int gridNumber = 5;
	private Grid[] grid;
	private double linkProbability;
	private double logicGateProbability;

	public GateGenerator()
	{
		this.grid = new Grid[getGridnumber()];
	}

	public GateGenerator(Grid initialGrid, double linkProb, double logicGateProb)
	{
		this.grid = new Grid[GateGenerator.getGridnumber()];
		this.grid[0] = new Grid(initialGrid);
		linkProbability = linkProb;
		logicGateProbability = logicGateProb;
	}

	@SuppressWarnings("static-access") public void generateNewGrids()
	{
		for (int ii = 1; ii < this.getGridnumber(); ii++)
		{
			this.grid[ii] = new Grid(this.grid[0]);
			this.grid[ii].reassignLogicGateOperation(this.logicGateProbability);
			this.grid[ii].relinkAllGatesInGrid(this.linkProbability);
			this.grid[ii].printGrid();
		}
	}

	public static int getGridnumber()
	{
		return gridNumber;
	}

}
