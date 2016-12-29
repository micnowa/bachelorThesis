package cartessian.genetic.programmming;

import cartessian.genetic.programmming.operation.*;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import cartessian.genetic.programmming.operation.Operational;

public class MainClass extends Canvas
{
	private static final long serialVersionUID = 1L;

	public MainClass()
	{
		super();
	}

	public static void main(String[] args)
	{
		// Available Operations
		ArrayList<Operational<Boolean>> operations = new ArrayList<Operational<Boolean>>();
		operations.add(new And());
		operations.add(new Or());
		operations.add(new Nor());
		operations.add(new Xor());
		operations.add(new Nand());

		// Grid of operations
		Grid<Operational<Boolean>> grid = new Grid<Operational<Boolean>>(operations,5, 8, 1);
		grid.printGrid();

		// Set of grids
		GridGenerator<Operational<Boolean>> g = new GridGenerator<Operational<Boolean>>(grid, 0.75);
		g.getMainGrid().printGrid();

		// List of values
		ArrayList<Boolean> listBool = new ArrayList<Boolean>();
		listBool.add(true);
		listBool.add(true);
		System.out.println(((Operational<Boolean>) ((grid.getGates())[0][0]).getOperation()).calculateValue(listBool));
		
		
		GridVisualizer<Operational<Boolean>> m = new GridVisualizer<>(g.getGrid()[3]);
		JFrame f = new JFrame();
		f.add(m);
		f.setSize(1600, 900);
		f.setVisible(true);

	}

}