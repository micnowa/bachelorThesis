package cartessian.genetic.programmming;

import cartessian.genetic.programmming.operation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cartessian.genetic.programmming.operation.Operational;

public class MainClass extends Canvas
{
	private static final long serialVersionUID = 1L;

	public MainClass()
	{
		super();
	}

	public static void main(String[] args) throws InterruptedException
	{
		int input = 4;
		int output = 5;
		int rows = 5;
		int columns = 7;
		int enteringGates = 1;
		double probability = 1;
		
		// Available Operations
		ArrayList<Operational<Boolean>> operations = new ArrayList<Operational<Boolean>>();
		operations.add(new And());
		operations.add(new Or());
		operations.add(new Nor());
		operations.add(new Xor());
		operations.add(new Nand());

		// Grid of operations
		Grid<Operational<Boolean>, Boolean> grid = new Grid<Operational<Boolean>, Boolean>(operations, input, output, rows, columns, enteringGates);
		grid.printGrid();

		// Set of grids
		GridGenerator<Operational<Boolean>, Boolean> g = new GridGenerator<Operational<Boolean>, Boolean>(grid, probability);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual = new GridVisualizer<>(g.getMainGrid());
		JFrame frame = new JFrame();
		frame.add(gridVisual);
		frame.setSize(1600, 900);
		frame.setVisible(true);
		TimeUnit.SECONDS.sleep(1);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual0 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[0]);
		JFrame frame0 = new JFrame();
		frame0.add(gridVisual0);
		frame0.setSize(1600, 900);
		frame0.setVisible(true);
		TimeUnit.SECONDS.sleep(1);
		
		GridVisualizer<Operational<Boolean>, Boolean> gridVisual1 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[1]);
		JFrame frame1 = new JFrame();
		frame1.add(gridVisual1);
		frame1.setSize(1600, 900);
		frame1.setVisible(true);
		TimeUnit.SECONDS.sleep(1);
		
		GridVisualizer<Operational<Boolean>, Boolean> gridVisual2 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[2]);
		JFrame frame2 = new JFrame();
		frame2.add(gridVisual2);
		frame2.setSize(1600, 900);
		frame2.setVisible(true);
		TimeUnit.SECONDS.sleep(1);
		
		GridVisualizer<Operational<Boolean>, Boolean> gridVisual3 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[3]);
		JFrame frame3 = new JFrame();
		frame3.add(gridVisual3);
		frame3.setSize(1600, 900);
		frame3.setVisible(true);
		TimeUnit.SECONDS.sleep(1);
	}
}


//GridVisualizer<Operational<Boolean>, Boolean> gridVisualizer[] = new GridVisualizer[4];
//JFrame f[] = new JFrame[4];
//for (int ii = 0; ii < 4; ii++)
//{
//	gridVisualizer[ii] = new GridVisualizer<>(g.getGrid()[ii]);
//	f[ii] = new JFrame();
//	f[ii].add(gridVisualizer[ii]);
//	f[ii].setSize(1600, 900);
//	f[ii].setVisible(true);
//}