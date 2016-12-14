package cartessian.genetic.programmming;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		Grid grid = new Grid(10, 5, 5);
		grid.printGrid();

		GridGenerator g = new GridGenerator(grid, 0.1, 0.1);
		System.out.println("Main Grid:\n");
		g.getMainGrid().printGrid();
	}

	@Override public void actionPerformed(ActionEvent e)
	{
	}
}