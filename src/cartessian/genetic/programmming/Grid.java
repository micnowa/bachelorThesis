package cartessian.genetic.programmming;

import java.util.Random;

//import cartessian.genetic.programmming.Graph.Graph;

public class Grid
{
	private int m;
	private int n;
	private LogicGate logicGates[][];

	public Grid()
	{
		super();
	}

	public Grid(int mm,int nn)
	{
		this.m = mm;
		this.n = nn;
		this.logicGates = new LogicGate[this.m][this.n];
		Random randomGenerator = new Random();
		int randomInt, logicGateCount = 0;
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				randomInt = randomGenerator.nextInt(5) + 1;
				logicGates[ii][jj] = new LogicGate(randomInt, logicGateCount);
				logicGateCount++;
			}
		}
	}

	public LogicGate[][] getLogicGates()
	{
		return logicGates;
	}

	public void setLogicGates(LogicGate[][] logicGates)
	{
		this.logicGates = logicGates;
	}

	public LogicGate getLogicGateAt(int ii, int jj)
	{
		return logicGates[ii][jj];
	}
	
	public LogicGate getLogicGateAt(int n)
	{
		int[][] tab = new int[1][1];
		tab = this.getLogicGatePositionAtNumber(n);
		
		return logicGates[tab[0][0]][tab[0][1]];
	}
	
	public void setLogicGateAt(LogicGate logicGate, int n)
	{
		int[][] tab = new int[1][1];
		tab = this.getLogicGatePositionAtNumber(n);
		
		logicGates[tab[0][0]][tab[0][1]] = logicGate;
	}

	public void setLogicGateAt(LogicGate logicGate, int ii, int jj)
	{
		this.logicGates[ii][jj] = logicGate;
	}

	public int getWidht()
	{
		return n;
	}

	public void setWidht(int widht)
	{
		this.n = widht;
	}

	public int getHight()
	{
		return m;
	}

	public void setHight(int hight)
	{
		this.m = hight;
	}

	void printGrid()
	{
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				System.out.print(logicGates[ii][jj].getNumber() + "("
						+ logicGates[ii][jj].getLogicOperation() + ")	");
			}
			System.out.println();
		}
	}

	int[][] getLogicGatePositionAtNumber(int pos)
	{
		int ii, jj;
		
		ii = (int)(pos / this.n);
		jj = pos%this.n;

		int[][] tab = new int[1][2];
		tab[0][0] = ii;
		tab[0][1] = jj;

		return tab;
	}

}