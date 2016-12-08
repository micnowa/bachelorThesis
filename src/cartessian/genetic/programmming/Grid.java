package cartessian.genetic.programmming;

import java.util.Random;

public class Grid
{
	protected int m;
	protected int n;
	protected Gate logicGates[][];
	protected Random randomGenerator;

	public Grid()
	{
		super();
	}

	public Grid(int mm,int nn)
	{
		this.m = mm;
		this.n = nn;
		this.logicGates = new Gate[this.m][this.n];
		randomGenerator = new Random();
		int randomInt;
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				randomInt = randomGenerator.nextInt(5) + 1;
				logicGates[ii][jj] = new Gate(randomInt, ii, jj);
			}
		}

		this.linkAllGatesInGrid();
	}

	public Grid(Grid grid)
	{
		this.m = grid.m;
		this.n = grid.n;
		randomGenerator = new Random();
		this.logicGates = new Gate[this.m][this.n];
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				logicGates[ii][jj] = new Gate(grid.logicGates[ii][jj].getLogicOperation(), ii, jj);
				this.logicGates[ii][jj].setEnteringGates(grid.logicGates[ii][jj].getEnteringGates());
				this.logicGates[ii][jj].setExitingGates(grid.logicGates[ii][jj].getExitingGates());
			}
		}
	}

	void printGrid()
	{
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				System.out.print("(" + logicGates[ii][jj].getI() + "," + logicGates[ii][jj].getJ() + ")=" + logicGates[ii][jj].getLogicOperation() + "	");
			}
			System.out.println();
		}
		System.out.println();
	}

	void linkGates(Gate g1, Gate g2)
	{
		g2.addEnteringGate(g1);
		g1.addExitingGate(g2);
	}

	void removeLink(Gate g1, Gate g2)
	{
		g1.getExitingGates().remove(g2);
		g1.getEnteringGates().remove(g1);
	}

	void linkAllGatesInGrid()
	{
		int randomIi1, randomIi2, randomJj1, randomJj2;
		System.out.println("Sorting by entering");
		for (int jj = 1; jj < this.n; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				randomIi1 = randomGenerator.nextInt(5);
				randomIi2 = randomGenerator.nextInt(5);
				while (randomIi1 == randomIi2)
				{
					randomIi2 = randomGenerator.nextInt(5);
				}
				randomJj1 = randomGenerator.nextInt(jj);
				randomJj2 = randomGenerator.nextInt(jj);

				this.linkGates(logicGates[randomIi1][randomJj1], logicGates[ii][jj]);
				this.linkGates(logicGates[randomIi2][randomJj2], logicGates[ii][jj]);

				System.out.println(logicGates[ii][jj].getEnteringGates().get(0).getI() + "," + logicGates[ii][jj].getEnteringGates().get(0).getJ() + "--->" + ii + "," + jj);
				System.out.println(logicGates[ii][jj].getEnteringGates().get(1).getI() + "," + logicGates[ii][jj].getEnteringGates().get(1).getJ() + "--->" + ii + "," + jj);
			}
		}

		System.out.println("Sorting by exiting");
		int lim;
		for (int jj = 0; jj < this.n - 1; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				lim = logicGates[ii][jj].getExitingGates().size();
				if (lim == 0) System.out.println(ii + "," + jj + "---> NULL");
				else
				{
					for (int kk = 0; kk < lim; kk++)
					{
						System.out.println(ii + "," + jj + "--->" + logicGates[ii][jj].getExitingGates().get(kk).getI() + "," + logicGates[ii][jj].getExitingGates().get(kk).getJ());
					}
				}
				System.out.println();
			}
		}
	}

	void relinkGates(Gate gate, double linkProbability)
	{
		for (int kk = 0; kk < 2; kk++)
		{
			if (randomGenerator.nextDouble() < linkProbability)
			{
				this.removeLink(gate.getEnteringGates().getFirst(), gate);
				int column = randomGenerator.nextInt(gate.getJ());
				int row = randomGenerator.nextInt(this.m);
				this.linkGates(this.logicGates[row][column], gate);
			}
		}
	}

	void reassignLogicGateOperation(double logicGateProbability)
	{
		for (int jj = 0; jj < this.n; jj++)
			for (int ii = 0; ii < this.m; ii++)
				if (randomGenerator.nextDouble() < logicGateProbability) this.logicGates[0][jj].setLogicOperation(randomGenerator.nextInt(5));
	}

	void relinkAllGatesInGrid(double linkProbability)
	{
		for (int jj = 1; jj < this.n; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				this.relinkGates(this.logicGates[ii][jj], linkProbability);
			}
		}
	}
}