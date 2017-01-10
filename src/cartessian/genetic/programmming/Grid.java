package cartessian.genetic.programmming;

import java.util.LinkedList;
import java.util.Random;

import cartessian.genetic.programmming.operation.Operational;

/**
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            Operation
 * @param <U>
 *            Operations's argument
 */
public class Grid<T, U>
{
	private int m;
	private int n;

	private int enteringGatesNumber;
	private Gate<T, U> gates[][];

	private int inputNumber;
	private Gate<T, U> input[];

	private int outputNumber;
	private Gate<T, U> output[];

	private Random randomGenerator;
	private LinkedList<T> operationList;

	/**
	 * Default constructor. Set all fields with their default values
	 */
	public Grid()
	{
		this.m = 0;
		this.n = 0;
		this.randomGenerator = new Random();
	}

	/**
	 * @param operations
	 * @param inputNum
	 * @param outputNum
	 * @param mm
	 * @param nn
	 * @param enteringGatesNum
	 */
	@SuppressWarnings("unchecked") public Grid(LinkedList<T> operations, int inputNum, int outputNum, int mm, int nn, int enteringGatesNum)
	{
		m = mm;// Parameters setting
		n = nn;
		enteringGatesNumber = enteringGatesNum;
		operationList = operations;
		gates = new Gate[m][n];
		randomGenerator = new Random();

		inputNumber = inputNum;// Input setting
		input = new Gate[inputNumber];
		for(int ii = 0; ii < inputNumber; ii++)
		{
			input[ii] = new Gate<T, U>();
			input[ii].setI(ii);
			input[ii].setJ(-1);
		}

		outputNumber = outputNum;// Output setting
		output = new Gate[outputNumber];
		for(int ii = 0; ii < outputNumber; ii++)
		{
			output[ii] = new Gate<T, U>();
			output[ii].setI(ii);
			output[ii].setJ(n);
		}

		for(int ii = 0; ii < m; ii++)
			// Gates setting
			for(int jj = 0; jj < n; jj++)
				gates[ii][jj] = new Gate<T, U>(operationList.get(randomGenerator.nextInt(operationList.size())), ii, jj);

		linkAllGates();// Links creation
	}

	/**
	 * Copying constructor
	 * 
	 * @param grid
	 */
	@SuppressWarnings("unchecked") public Grid(Grid<T, U> grid)
	{
		m = grid.m;// Parameters setting
		n = grid.n;
		randomGenerator = new Random();
		enteringGatesNumber = grid.getEnteringGatesNumber();
		operationList = grid.operationList;
		inputNumber = grid.inputNumber;
		outputNumber = grid.outputNumber;
		gates = new Gate[m][n];

		input = new Gate[inputNumber];// Input setting
		for(int ii = 0; ii < inputNumber; ii++)
		{
			input[ii] = new Gate<T, U>();
			input[ii].setJ(-1);
			input[ii].setI(ii);
			input[ii].value = grid.input[ii].value;
			input[ii].exitingGates = new LinkedList<Gate<T, U>>(grid.getInput()[ii].exitingGates);
		}

		output = new Gate[outputNumber];// Output setting
		for(int ii = 0; ii < outputNumber; ii++)
		{
			output[ii] = new Gate<T, U>();
			output[ii].setJ(n);
			output[ii].setI(ii);
			output[ii].enteringGates = new LinkedList<Gate<T, U>>(grid.getOutput()[ii].enteringGates);
		}

		for(int ii = 0; ii < m; ii++)// Gates setting
		{
			for(int jj = 0; jj < n; jj++)
			{
				gates[ii][jj] = new Gate<T, U>(grid.gates[ii][jj].getOperation(), ii, jj);
				gates[ii][jj].enteringGates = new LinkedList<Gate<T, U>>(grid.getGates()[ii][jj].enteringGates);
				gates[ii][jj].exitingGates = new LinkedList<Gate<T, U>>(grid.getGates()[ii][jj].exitingGates);
				gates[ii][jj].value = grid.gates[ii][jj].value;
			}
		}
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
	public Gate<T, U>[][] getGates()
	{
		return gates;
	}

	/**
	 * @return
	 */
	public int getEnteringGatesNumber()
	{
		return enteringGatesNumber;
	}

	/**
	 * @param enteringGatesNumber
	 */
	public void setEnteringGatesNumber(int enteringGatesNumber)
	{
		this.enteringGatesNumber = enteringGatesNumber;
	}

	/**
	 * Sets two dimensional table of logic gates
	 * 
	 * @param logicGates
	 */
	public void setGates(Gate<T, U>[][] gates)
	{
		this.gates = gates;
	}

	/**
	 * @return
	 */
	public LinkedList<T> getOperationList()
	{
		return operationList;
	}

	/**
	 * @param operationList
	 */
	public void setOperationList(LinkedList<T> operationList)
	{
		this.operationList = operationList;
	}

	/**
	 * @return
	 */
	public int getInputNumber()
	{
		return inputNumber;
	}

	/**
	 * @param inputNumber
	 */
	public void setInputNumber(int inputNumber)
	{
		this.inputNumber = inputNumber;
	}

	/**
	 * @return
	 */
	public int getOutputNumber()
	{
		return outputNumber;
	}

	/**
	 * @param outputNumber
	 */
	public void setOutputNumber(int outputNumber)
	{
		this.outputNumber = outputNumber;
	}

	public Gate<T, U>[] getInput()
	{
		return input;
	}

	public void setInput(Gate<T, U>[] input)
	{
		this.input = input;
	}

	public Gate<T, U>[] getOutput()
	{
		return output;
	}

	public void setOutput(Gate<T, U>[] output)
	{
		this.output = output;
	}

	/**
	 * Prints gird on the stand output
	 */
	void printGrid()
	{
		for(int ii = 0; ii < m; ii++)
		{
			for(int jj = 0; jj < n; jj++)
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
	void linkGates(Gate<T, U> g1, Gate<T, U> g2)
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
	void removeLink(Gate<T, U> g1, Gate<T, U> g2)
	{
		g1.getExitingGates().remove(g2);
		g2.getEnteringGates().remove(g1);
	}

	/**
	 * Creates links between gates, input and output
	 */
	void linkAllGates()
	{
		int randomIi, randomJj;
		double gateInputProbability;

		for(int ii = 0; ii < m; ii++)// First column linking
		{
			for(int kk = 0; kk < enteringGatesNumber; kk++)
			{
				randomIi = randomGenerator.nextInt(inputNumber);
				this.linkGates(input[randomIi], gates[ii][0]);
			}
		}

		for(int jj = 1; jj < n; jj++)// Columns 1->n-1 linking
		{
			for(int ii = 0; ii < m; ii++)
			{
				gateInputProbability = (double) inputNumber / (inputNumber + jj * m);
				for(int kk = 0; kk < enteringGatesNumber; kk++)
				{
					if(randomGenerator.nextDouble() < gateInputProbability)
					{
						randomIi = randomGenerator.nextInt(inputNumber);
						linkGates(input[randomIi], gates[ii][jj]);
					}
					else
					{
						randomIi = randomGenerator.nextInt(m);
						randomJj = randomGenerator.nextInt(jj);
						linkGates(gates[randomIi][randomJj], gates[ii][jj]);
					}
				}
			}
		}

		gateInputProbability = (double) inputNumber / (inputNumber + n * m);// Output
																			// linking
		for(int ii = 0; ii < outputNumber; ii++)
		{
			if(randomGenerator.nextDouble() < gateInputProbability)
			{
				randomIi = randomGenerator.nextInt(inputNumber);
				linkGates(input[randomIi], output[ii]);
				System.out.println("input[" + randomIi + "]	with output[" + ii + "]");
			}
			else
			{
				randomIi = randomGenerator.nextInt(m);
				randomJj = randomGenerator.nextInt(n);
				linkGates(gates[randomIi][randomJj], output[ii]);
				System.out.println("Linkin i=" + randomIi + ",j=" + randomJj + "	with output[" + ii + "]");
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
	private void relinkGate(Gate<T, U> gate, double linkProbability)
	{
		int column, row, jj, removed = 0;
		double gateInputProbability;

		for(int kk = 0; kk < enteringGatesNumber; kk++)
		{
			if(randomGenerator.nextDouble() < linkProbability)
			{
				removeLink(gate.getEnteringGates().get(kk - removed), gate);
				jj = gate.getJ();
				gateInputProbability = (double) inputNumber / (inputNumber + jj * m);
				if(randomGenerator.nextDouble() < gateInputProbability || (jj == 0))
				{
					row = randomGenerator.nextInt(inputNumber);
					linkGates(input[row], gate);
				}
				else
				{
					column = randomGenerator.nextInt(jj);
					row = randomGenerator.nextInt(m);
					linkGates(gates[row][column], gate);
				}
				removed++;
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
		for(int jj = 0; jj < n; jj++)
		{
			for(int ii = 0; ii < m; ii++)
			{
				if(randomGenerator.nextDouble() < logicGateProbability)
				{
					gates[ii][jj].setOperation(operationList.get(randomGenerator.nextInt(operationList.size())));
				}
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
		for(int jj = 0; jj < n; jj++)// Switching gates
		{
			for(int ii = 0; ii < m; ii++)
			{
				relinkGate(gates[ii][jj], linkProbability);
			}
		}

		for(int ii = 0; ii < outputNumber; ii++)// Switching output
		{
			int column, row;
			double inputProbability;
			if(randomGenerator.nextDouble() < linkProbability)
			{
				removeLink(output[ii].getEnteringGates().getFirst(), output[ii]);
				inputProbability = inputNumber / (inputNumber + m * n);
				if(randomGenerator.nextDouble() < inputProbability)
				{
					linkGates(input[randomGenerator.nextInt(inputNumber)], output[ii]);
				}
				else
				{
					column = randomGenerator.nextInt(n);
					row = randomGenerator.nextInt(m);
					linkGates(gates[row][column], output[ii]);
				}
			}
		}
	}

	public void setInputValues(U val[])
	{
		if(val.length != inputNumber) return;
		int ii = 0;
		for(U x : val)
		{
			input[ii].setValue(x);
			ii++;
		}
	}

	@SuppressWarnings("unchecked") public void calculateValueForEveryGate()
	{
		Operational<U> op;
		for(int jj = 0; jj < n; jj++)
		{
			for(int ii = 0; ii < m; ii++)
			{
				op = (Operational<U>) gates[ii][jj].getOperation();// ,,Czeba" omówić
				LinkedList<U> values = new LinkedList<U>();
				for(int kk = 0; kk < enteringGatesNumber; kk++)
					values.add(gates[ii][jj].getEnteringGates().get(kk).getValue());
				gates[ii][jj].setValue(op.calculateValue(values));
			}
		}

		for(int jj = 0; jj < outputNumber; jj++)
			output[jj].setValue(output[jj].getEnteringGates().getFirst().getValue());
	}

}