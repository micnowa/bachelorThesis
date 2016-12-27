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
		Integer M = m;
		int n = grid.getN();
		Integer N = n;

		System.out.println(m + "	" + n);
		int X = 150;

		// g.drawRect((0 + 1)*50, (0 + 1)*50,(0 + 1)*50 + 10, (0 + 1)*50 + 10);
		for (int ii = 0; ii < m; ii++)
		{
			for (int jj = 0; jj < n; jj++)
			{
				M = ii;
				N = jj;
				g.drawRect((ii + 1) * X, (jj + 1) * X, 30, 30);
				g.drawString(M.toString(), (ii + 1) * X, (jj + 1) * X);
				g.drawString(N.toString(), (ii + 1) * X + 10, (jj + 1) * X);
			}
		}

		LinkedList<Gate<T>> gateList;// = new ArrayList<Gate<T>>();

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
					g.drawLine((ii + 1) * X, (jj + 1) * X + 5, (mm + 1) * X + 30, (nn + 1) * X + 30);

				}
			}
		}

	}
}
