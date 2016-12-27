package cartessian.genetic.programmming;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Michał Nowaliński
 * 
 */
public class Grid<T>
{
	private int m;
	private int n;
	private int enteringGatesNumber;
	private Gate<T> gates[][];
	private Random randomGenerator;
	private int operationNumber;
	private ArrayList<T> operationList;
	private int inputNumber;

	/**
	 * Default constructor. Set all fields with their default values
	 */
	public Grid()
	{
		super();
		this.m = 0;
		this.n = 0;
		this.operationNumber = 0;
		this.randomGenerator = new Random();
	}

	/**
	 * @param operations
	 *            List of possible operations
	 * @param mm
	 *            Number of rows
	 * @param nn
	 *            Number of columns
	 * @param enteringGatesNumber
	 *            Number of argument one operation has
	 */
	public Grid(ArrayList<T> operations, int mm, int nn, int enteringGatesNumber)
	{
		this.m = mm;
		this.n = nn;
		this.enteringGatesNumber = enteringGatesNumber;
		this.operationNumber = operations.size();
		this.operationList = operations;
		this.gates = new Gate[this.m][this.n];
		this.randomGenerator = new Random();
		
		int randomInt;
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				randomInt = randomGenerator.nextInt(this.operationNumber);
				T op = this.operationList.get(randomInt);
				gates[ii][jj] = new Gate<T>(op, ii, jj);
			}
		}
		this.linkAllGates();
	}

	/**
	 * @return Number number of rows of the two dimensional table of gates
	 */
	public int getM()
	{
		return m;
	}

	/**
	 * Sets number of rows
	 * 
	 * @param m
	 *            number of rows
	 */
	public void setM(int m)
	{
		this.m = m;
	}

	/**
	 * @return Number number of columns of the two dimensional table of gates
	 */
	public int getN()
	{
		return n;
	}

	/**
	 * Sets number of columns
	 * 
	 * @param n
	 *            number of columns
	 */
	public void setN(int n)
	{
		this.n = n;
	}

	/**
	 * @return two dimensional table of gates
	 */
	public Gate<T>[][] getGates()
	{
		return gates;
	}

	public int getEnteringGatesNumber()
	{
		return enteringGatesNumber;
	}

	public void setEnteringGatesNumber(int enteringGatesNumber)
	{
		this.enteringGatesNumber = enteringGatesNumber;
	}

	/**
	 * Sets two dimensional table of logic gates
	 * 
	 * @param logicGates
	 */
	public void setGates(Gate<T>[][] logicGates)
	{
		this.gates = logicGates;
	}

	/**
	 * @return object creating random numbers
	 */
	public Random getRandomGenerator()
	{
		return randomGenerator;
	}

	/**
	 * Sets object creating random numbers
	 * 
	 * @param randomGenerator
	 *            object creating random numbers
	 */
	public void setRandomGenerator(Random randomGenerator)
	{
		this.randomGenerator = randomGenerator;
	}

	/**
	 * @return Number of different operations, single gate can have
	 */
	public int getOperationNumber()
	{
		return operationNumber;
	}

	/**
	 * @param operationNumber
	 *            Sets number of different operations, single gate can have
	 */
	public void setOperationNumber(int operationNumber)
	{
		this.operationNumber = operationNumber;
	}

	public ArrayList<T> getOperationList()
	{
		return operationList;
	}

	public void setOperationList(ArrayList<T> operationList)
	{
		this.operationList = operationList;
	}

	public int getInputNumber()
	{
		return inputNumber;
	}

	public void setInputNumber(int inputNumber)
	{
		this.inputNumber = inputNumber;
	}

	/**
	 * Copying constructor
	 * 
	 * @param grid
	 */
	// TO TYSZ!!!
	@SuppressWarnings("unchecked") public Grid(Grid<T> grid)
	{
		this.m = grid.m;
		this.n = grid.n;
		this.operationNumber = grid.getOperationNumber();
		this.randomGenerator = new Random();
		this.enteringGatesNumber = grid.getEnteringGatesNumber();
		this.operationList = grid.operationList;
		this.gates = new Gate[this.m][this.n];
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				gates[ii][jj] = new Gate<T>(grid.gates[ii][jj].getOperation(), ii, jj);
				this.gates[ii][jj].setEnteringGates(grid.gates[ii][jj].getEnteringGates());
				this.gates[ii][jj].setExitingGates(grid.gates[ii][jj].getExitingGates());
			}
		}
	}

	/**
	 * Prints gird on the stand output
	 */
	void printGrid()
	{
		for (int ii = 0; ii < this.m; ii++)
		{
			for (int jj = 0; jj < this.n; jj++)
			{
				System.out.print("(" + gates[ii][jj].getI() + "," + gates[ii][jj].getJ() + ")=" + gates[ii][jj].getOperation() + "	");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Links gates. g1 points to g2, g2 is pointed to by g1. Function adds g1 to
	 * g2.enteringGates, and g2 to g1.exitingGates
	 * 
	 * @param g1
	 *            First gate
	 * @param g2
	 *            Second gate
	 */
	void linkGates(Gate<T> g1, Gate<T> g2)
	{
		g2.addEnteringGate(g1);
		g1.addExitingGate(g2);
	}

	/**
	 * Removes link between g1 and g2. Functions removes g1 from
	 * g2.enteringGates and g2 form g1.exitingGates
	 * 
	 * @param g1
	 * @param g2
	 */
	void removeLink(Gate<T> g1, Gate<T> g2)
	{
		g1.getExitingGates().remove(g2);
		g2.getEnteringGates().remove(g1);
	}

	/**
	 * Draws links between gates
	 */
	void linkAllGates()
	{
		int randomIi, randomJj;
		System.out.println("Sorting by entering");
		for (int jj = 1; jj < this.n; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				for(int kk= 0 ; kk < this.enteringGatesNumber; kk++ )
				{
					randomIi = randomGenerator.nextInt(this.getM());
					randomJj = randomGenerator.nextInt(jj);
					this.linkGates(gates[randomIi][randomJj], gates[ii][jj]);
					System.out.println(gates[ii][jj].getEnteringGates().get(kk).getI() + "," + gates[ii][jj].getEnteringGates().get(kk).getJ() + "--->" + ii + "," + jj);
				}
			}
		}

		System.out.println("Sorting by exiting");
		int lim;
		for (int jj = 0; jj < this.n - 1; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				lim = gates[ii][jj].getExitingGates().size();
				if (lim == 0) System.out.println(ii + "," + jj + "---> NULL");
				else
				{
					for (int kk = 0; kk < lim; kk++)
					{
						System.out.println(ii + "," + jj + "--->" + gates[ii][jj].getExitingGates().get(kk).getI() + "," + gates[ii][jj].getExitingGates().get(kk).getJ());
					}
				}
				System.out.println();
			}
		}
	}

	/**
	 * Switches links, that enter the gate, between gates with given probability
	 * Always switches 2 link, because 2 links enter one gate
	 * 
	 * @param gate
	 *            gate, whose link shall be switched
	 * @param linkProbability
	 *            Probability of switching link
	 */
	private void relinkGate(Gate<T> gate, double linkProbability)
	{
		for (int kk = 0; kk < this.enteringGatesNumber; kk++)
		{
			if (randomGenerator.nextDouble() < linkProbability)
			{
				this.removeLink(gate.getEnteringGates().get(kk), gate);
				int column = randomGenerator.nextInt(gate.getJ());
				int row = randomGenerator.nextInt(this.m);
				this.linkGates(this.gates[row][column], gate);
			}
		}
	}

	/**
	 * Draws with given probability, whether gate's operation shall be switched
	 * If so draws new one form uniform distribution between 0 and
	 * gate.operationNumber. Function repeats it for every gate in grid
	 * 
	 * @param logicGateProbability
	 */
	void reassignGatesOperation(double logicGateProbability)
	{
		for (int jj = 0; jj < this.n; jj++)
			for (int ii = 0; ii < this.m; ii++)
			{
				if (randomGenerator.nextDouble() < logicGateProbability)
				{
					this.gates[ii][jj].setOperation(this.operationList.get(randomGenerator.nextInt(this.getOperationNumber())));
				}
			}

	}

	/**
	 * Switches with given probability every link
	 * 
	 * @param linkProbability
	 *            probability of switching link
	 */
	void relinkAllGates(double linkProbability)
	{
		for (int jj = 1; jj < this.n; jj++)
		{
			for (int ii = 0; ii < this.m; ii++)
			{
				this.relinkGate(this.gates[ii][jj], linkProbability);
			}
		}
	}

}