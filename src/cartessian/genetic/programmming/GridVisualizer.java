package cartessian.genetic.programmming;

import java.awt.*;
import java.util.LinkedList;

public class GridVisualizer<T> extends Canvas
{
	private static final long serialVersionUID = 1L;
	private Grid<T> grid;
	private int activeOutput;
	private int a;
	private int dist;

	public GridVisualizer(Grid<T> grid)
	{
		super();
		this.grid = grid;
	}

	public int getActiveOutput()
	{
		return activeOutput;
	}

	public void setActiveOutput(int activeOutput)
	{
		this.activeOutput = activeOutput;
	}

	public int getA()
	{
		return a;
	}

	public void setA(int a)
	{
		this.a = a;
	}

	public int getDist()
	{
		return dist;
	}

	public void setDist(int d)
	{
		this.dist = d;
	}

	public void paint(Graphics g)
	{
		g.drawString("Grid", 40, 40);
		setBackground(Color.WHITE);
		dist = 150;
		a = 30;
		LinkedList<Gate<T>> gateList;
		for(int ii = 0; ii < grid.getM(); ii++)// Draw Gates and links between
		{
			for(int jj = 0; jj < grid.getN(); jj++)
			{
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((jj + 1) * dist, (ii + 1) * dist, a, a);
				g.setColor(Color.RED);
				g.drawString(ii + "	" + jj, (jj + 1) * dist, (ii + 1) * dist);
				g.setColor(Color.BLUE);
				g.drawString(grid.getGates()[ii][jj].getFunction().toString(), (jj + 1) * dist, (ii + 1) * dist + a / 2);
				g.setColor(Color.LIGHT_GRAY);
				gateList = grid.getGates()[ii][jj].getEnteringGates();
				for(int kk = 0; kk < gateList.size(); kk++)
				{
					if(gateList.get(kk).getJ() == -1)
					{
						g.drawLine((jj + 1) * dist, (ii + 1) * dist + a / 2, (gateList.get(kk).getJ() + 1) * dist + a, gateList.get(kk).getI() * dist + a + 80 - a / 2);
					}
					else
					{
						g.drawLine((jj + 1) * dist, (ii + 1) * dist + a / 2, (gateList.get(kk).getJ() + 1) * dist + a, (gateList.get(kk).getI() + 1) * dist + a / 2);
					}
				}
			}
		}
		g.setColor(Color.RED);
		for(int ii = 0; ii < grid.getInputNumber(); ii++)// Draw Input
		{
			g.fillRect(0, ii * dist + 80, a, a);
			g.setColor(Color.GRAY);
			g.drawString(grid.getInput()[ii].getValue().toString(), 0, ii * dist + 80);
			g.setColor(Color.RED);
		}

		for(int ii = 0; ii < grid.getOutputNumber(); ii++)// Draw Output
		{
			g.setColor(Color.ORANGE);
			g.fillRect(1500, ii * dist + 80, a, a);
			g.setColor(Color.RED);
			g.drawString(grid.getOutput()[ii].getValue().toString(), 1500, ii * dist + 80);
			g.setColor(Color.LIGHT_GRAY);
			int gateI = grid.getOutput()[ii].getEnteringGates().getFirst().getI();
			int gateJ = grid.getOutput()[ii].getEnteringGates().getFirst().getJ();
			if(grid.getOutput()[ii].getEnteringGates().getFirst().getJ() == -1)
			{
				g.drawLine(a, gateI * dist + 80 + a / 2, 1500, ii * dist + 50 + a + a / 2);
			}
			else
			{
				g.drawLine((gateJ + 1) * dist + a, (gateI + 1) * dist + a / 2, 1500, ii * dist + 50 + a + a / 2);
			}
		}

		// Highlighting active output
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.RED);
		int gateI = grid.getOutput()[activeOutput].getEnteringGates().getFirst().getI();
		int gateJ = grid.getOutput()[activeOutput].getEnteringGates().getFirst().getJ();
		if(grid.getOutput()[activeOutput].getEnteringGates().getFirst().getJ() == -1)
		{
			g.drawLine(a, gateI * dist + 80 + a / 2, 1500, activeOutput * dist + 50 + a + a / 2);
		}
		else
		{
			g.drawLine((gateJ + 1) * dist + a, (gateI + 1) * dist + a / 2, 1500, activeOutput * dist + 50 + a + a / 2);
		}
		int ii = grid.getOutput()[activeOutput].getEnteringGates().getFirst().getI();
		int jj = grid.getOutput()[activeOutput].getEnteringGates().getFirst().getJ();
		highlightGate(g2, ii, jj);
	}

	public void highlightGate(Graphics2D g, int ii, int jj)
	{
		if(jj == -1) return;
		g.setColor(Color.CYAN);
		g.fillRect((jj + 1) * dist, (ii + 1) * dist, a, a);
		g.setColor(Color.BLUE);
		g.drawString(grid.getGates()[ii][jj].getFunction().toString(), (jj + 1) * dist, (ii + 1) * dist + a / 2);
		Gate<T> gate = grid.getGates()[ii][jj];
		LinkedList<Gate<T>> gateList = gate.getEnteringGates();
		for(int kk = 0; kk < gateList.size(); kk++)
		{
			if(gateList.get(kk).getJ() == -1)
			{
				g.setColor(Color.GREEN);
				g.drawLine((jj + 1) * dist, (ii + 1) * dist + a / 2, (gateList.get(kk).getJ() + 1) * dist + a, gateList.get(kk).getI() * dist + a + 80 - a / 2);
			}
			else
			{
				g.setColor(Color.BLACK);
				g.drawLine((jj + 1) * dist, (ii + 1) * dist + a / 2, (gateList.get(kk).getJ() + 1) * dist + a, (gateList.get(kk).getI() + 1) * dist + a / 2);
			}
			highlightGate(g, gateList.get(kk).getI(), gateList.get(kk).getJ());
		}
	}
}
