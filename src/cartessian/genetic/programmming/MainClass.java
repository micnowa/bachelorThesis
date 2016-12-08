package cartessian.genetic.programmming;

public class MainClass
{

	public static void main(String[] args)
	{
		Grid grid = new Grid(5, 5);
		grid.printGrid();
		
		GateGenerator g = new GateGenerator(grid, 1, 1);
		g.generateNewGrids();
	}
}