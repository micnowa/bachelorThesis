package cartessian.genetic.programmming;

import cartessian.genetic.programmming.operation.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import cartessian.genetic.programmming.operation.Operationable;

public class MainClass extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public MainClass()
	{
		setSize(1600, 900);
		setTitle("Recurrent Cartessian Genetic Programming");
		setLayout(null);
	}

	public static void main(String[] args)
	{
		MainClass window = new MainClass();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setVisible(true);
		ArrayList<Operationable> operations = new ArrayList<Operationable>();
		operations.add(new And());
		operations.add(new Nor());
		operations.add(new Xor());
		operations.add(new Or());
		operations.add(new Nand());
		
		
		Grid grid = new Grid(operations, 5, 5);
		grid.printGrid();

		GridGenerator g = new GridGenerator(grid, 0.75);
		System.out.println("Main Grid:\n");
		g.getMainGrid().printGrid();
	}

	@Override public void actionPerformed(ActionEvent e)
	{
	}
}