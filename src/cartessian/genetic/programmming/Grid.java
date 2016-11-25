package cartessian.genetic.programmming;

import java.util.Random;

public class Grid
{
	private int m;
	private int n;
	private Gate logicGates[][];

	public Grid()
	{
		super();
	}

	public Grid(int mm,int nn)
	{
		this.m = mm;
		this.n = nn;
		this.logicGates = new Gate[this.m][this.n];
		Random randomGenerator = new Random();
		int randomInt;
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				randomInt = randomGenerator.nextInt(5) + 1;
				logicGates[ii][jj] = new Gate(randomInt, ii, jj);
			}
		}

		this.linkGates();
	}

	void printGrid()
	{
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				System.out.print("(" + logicGates[ii][jj].getI() + ","
						+ logicGates[ii][jj].getJ() + ")="
						+ logicGates[ii][jj].getLogicOperation() + "	");
			}
			System.out.println();
		}
	}

	void linkGates(Gate g1, Gate g2)
	{
		g2.addEnteringGate(g1);
		g1.addExitingGate(g2);
	}

	void linkGates()
	{
		Random randomGenerator = new Random();
		int rand1, rand2;
		System.out.println("Sorting by entering");

		for (int jj = 1; jj < this.n; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				rand1 = randomGenerator.nextInt(5);
				rand2 = randomGenerator.nextInt(5);
				while(rand1 == rand2) rand2 = randomGenerator.nextInt(5);
				
				this.linkGates(logicGates[rand1][jj - 1], logicGates[ii][jj]);
				this.linkGates(logicGates[rand2][jj - 1], logicGates[ii][jj]);

				System.out.println(logicGates[ii][jj].getEnteringGates().get(0)
						.getI()
						+ ","
						+ logicGates[ii][jj].getEnteringGates().get(0).getJ()
						+ "--->" + ii + "," + jj);

				System.out.println(logicGates[ii][jj].getEnteringGates().get(1)
						.getI()
						+ ","
						+ logicGates[ii][jj].getEnteringGates().get(1).getJ()
						+ "--->" + ii + "," + jj);
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
						System.out.println(ii
								+ ","
								+ jj
								+ "--->"
								+ logicGates[ii][jj].getExitingGates().get(kk)
										.getI()
								+ ","
								+ logicGates[ii][jj].getExitingGates().get(kk)
										.getJ());
					}
				}
				System.out.println();
			}
		}
	}
}