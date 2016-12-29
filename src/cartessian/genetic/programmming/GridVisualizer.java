package cartessian.genetic.programmming;

import java.awt.*;
import java.util.LinkedList;

public class GridVisualizer<T> extends Canvas
{
	private Grid<T> grid;

	public GridVisualizer(Grid<T> grid)
	{
		super();
		this.grid = grid;
	}

	public void paint(Graphics g)
	{
		g.drawString("Grid", 40, 40);
		setBackground(Color.WHITE);

		int m = grid.getM();
		int n = grid.getN();

		int a = 150;

		for (int ii = 0; ii < m; ii++)
		{
			for (int jj = 0; jj < n; jj++)
			{
				g.drawRect((jj + 1) * a, (ii + 1) * a, 30, 30);
				g.setColor(Color.RED);
				g.drawString(ii + "	" + jj, (jj + 1) * a, (ii + 1) * a);
				g.setColor(Color.BLUE);
				g.drawString(grid.getGates()[ii][jj].getOperation().toString(), (jj + 1) * a, (ii + 1) * a + 15);
				g.setColor(Color.BLACK);
			}
		}

		LinkedList<Gate<T>> gateList;

		for (int ii = 0; ii < m; ii++)
		{
			for (int jj = 1; jj < n; jj++)
			{
				gateList = grid.getGates()[ii][jj].getEnteringGates();
				int size = gateList.size();
				for (int kk = 0; kk < size; kk++)
				{
					int mm = gateList.get(kk).getI();
					int nn = gateList.get(kk).getJ();
					g.drawLine((jj + 1) * a + 5, (ii + 1) * a, (nn + 1) * a + 30, (mm + 1) * a + 30);

				}
			}
		}

	}
}
