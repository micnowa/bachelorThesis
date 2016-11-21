package cartessian.genetic.programmming;

import cartessian.genetic.programmming.Graph.*;

public class MainClass
{

	public static void main(String[] args)
	{
		Grid grid = new Grid(6, 5);
		grid.printGrid();

		Graph graph = new Graph();

		for (int ii = 0; ii < 5; ii++)
			graph.addVertex(grid.getLogicGateAt(0, ii));
		for (int ii = 1; ii < 4; ii++)
			graph.addVertex(grid.getLogicGateAt(3, ii));

		Pair pair[] = new Pair[8];
		pair[0] = new Pair(grid.getLogicGatePositionAtNumber(0));
		pair[1] = new Pair(grid.getLogicGatePositionAtNumber(1));
		pair[2] = new Pair(grid.getLogicGatePositionAtNumber(2));
		pair[3] = new Pair(grid.getLogicGatePositionAtNumber(3));
		pair[4] = new Pair(grid.getLogicGatePositionAtNumber(4));
		pair[5] = new Pair(grid.getLogicGatePositionAtNumber(16));
		pair[6] = new Pair(grid.getLogicGatePositionAtNumber(17));
		pair[7] = new Pair(grid.getLogicGatePositionAtNumber(18));

		Edge edge[] = new Edge[6];
		edge[0] = new Edge(grid.getLogicGateAt(0), grid.getLogicGateAt(16), 0.1);
		edge[1] = new Edge(grid.getLogicGateAt(1), grid.getLogicGateAt(18), 0.2);
		edge[2] = new Edge(grid.getLogicGateAt(2), grid.getLogicGateAt(16), 0.3);
		edge[3] = new Edge(grid.getLogicGateAt(2), grid.getLogicGateAt(17), 0.4);
		edge[4] = new Edge(grid.getLogicGateAt(3), grid.getLogicGateAt(17), 0.5);
		edge[5] = new Edge(grid.getLogicGateAt(4), grid.getLogicGateAt(18), 0.6);

		for (int ii = 0; ii < 6; ii++) graph.addEdge(edge[ii]);
		
		graph.printGraph();

	}
}