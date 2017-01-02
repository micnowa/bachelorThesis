package cartessian.genetic.programmming;

import java.awt.*;
import java.util.LinkedList;

public class GridVisualizer<T, U> extends Canvas
{
	private static final long serialVersionUID = 1L;
	private Grid<T, U> grid;

	public GridVisualizer(Grid<T, U> grid)
	{
		super();
		this.grid = grid;
	}

	public void paint(Graphics g)
	{
		g.drawString("Grid", 40, 40);
		setBackground(Color.WHITE);
		int a = 150;
		LinkedList<Gate<T, U>> gateList;
		for (int ii = 0; ii < grid.getM(); ii++)// Draw Gates and links between
		{
			for (int jj = 0; jj < grid.getN(); jj++)
			{
				System.out.println("Enter: " + grid.getGates()[ii][jj].getEnteringGates().size());

				g.setColor(Color.CYAN);
				g.fillRect((jj + 1) * a, (ii + 1) * a, 30, 30);
				g.setColor(Color.RED);
				g.drawString(ii + "	" + jj, (jj + 1) * a, (ii + 1) * a);
				g.setColor(Color.BLUE);
				g.drawString(grid.getGates()[ii][jj].getOperation().toString(), (jj + 1) * a, (ii + 1) * a + 15);
				g.setColor(Color.BLACK);
				gateList = grid.getGates()[ii][jj].getEnteringGates();
				for (int kk = 0; kk < gateList.size(); kk++)
				{
					if (gateList.get(kk).getJ() == -1)
					{
						g.setColor(Color.GREEN);
						g.drawLine((jj + 1) * a, (ii + 1) * a + 15, (gateList.get(kk).getJ() + 1) * a + 30, gateList.get(kk).getI() * a + 30 + 80 - 15);
						g.setColor(Color.BLACK);
					}
					else
					{
						g.drawLine((jj + 1) * a, (ii + 1) * a + 15, (gateList.get(kk).getJ() + 1) * a + 30, (gateList.get(kk).getI() + 1) * a + 30 / 2);
					}
				}
			}
		}
		g.setColor(Color.RED);
		for (int ii = 0; ii < grid.getInputNumber(); ii++)// Draw Input
		{
			g.fillRect(0, ii * a + 80, 30, 30);
			System.out.println(grid.getInput()[ii].getExitingGates().size() + "***" + ii);
		}

		for (int ii = 0; ii < grid.getOutputNumber(); ii++)// Draw Output
		{
			g.setColor(Color.ORANGE);
			g.fillRect(1500, ii * a + 80, 30, 30);
			g.setColor(Color.RED);
			int gateI = grid.getOutput()[ii].getEnteringGates().getFirst().getI();
			int gateJ = grid.getOutput()[ii].getEnteringGates().getFirst().getJ();
			g.drawLine((gateJ + 1) * a + 30, (gateI + 1) * a + 15 , 1500, ii * a + 50 + 15 + 30);
		}

	}
}
