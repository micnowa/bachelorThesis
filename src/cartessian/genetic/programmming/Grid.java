package cartessian.genetic.programmming;

import java.util.LinkedList;
import java.util.Random;

import cartessian.genetic.programmming.function.Functional;

/**
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            Operation
 */
public class Grid<T>
{
	/**
	 * Number of rows
	 */
	private int m;
	/**
	 * Number of columns
	 */
	private int n;
	/**
	 * Number of gates entering single gate
	 */
	private int enteringGatesNumber;
	/**
	 * Two dimensional table of gate
	 */
	private Gate<T> gates[][];
	/**
	 * Number of gates in the input
	 */
	private int inputNumber;
	/**
	 * Table of input gates
	 */
	private Gate<T> input[];
	/**
	 * Number of gates in the output
	 */
	private int outputNumber;
	/**
	 * Table of output gates
	 */
	private Gate<T> output[];
	/**
	 * Randomness engine producing random numbers
	 */
	private Random randomGenerator;
	/**
	 * List of functions, that single gates can hold
	 */
	private LinkedList<Functional<T>> functionList;

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
	 * @param functions
	 * @param inputNum
	 * @param outputNum
	 * @param mm
	 * @param nn
	 * @param enteringGatesNum
	 */
	@SuppressWarnings("unchecked") public Grid(LinkedList<Functional<T>> functions, int inputNum, int outputNum, int mm, int nn, int enteringGatesNum)
	{
		m = mm;// Parameters setting
		n = nn;
		functionList = new LinkedList<Functional<T>>();
		for(Functional<T> func : functions)
		{
			functionList.add(func);
		}
		enteringGatesNumber = enteringGatesNum;
		gates = new Gate[m][n];
		randomGenerator = new Random();

		inputNumber = inputNum;// Input setting
		input = new Gate[inputNumber];
		for(int ii = 0; ii < inputNumber; ii++)
		{
			input[ii] = new Gate<T>();
			input[ii].setI(ii);
			input[ii].setJ(-1);
			input[ii].exitingGates = new LinkedList<Gate<T>>();
		}

		outputNumber = outputNum;// Output setting
		output = new Gate[outputNumber];
		for(int ii = 0; ii < outputNumber; ii++)
		{
			output[ii] = new Gate<T>();
			output[ii].setI(ii);
			output[ii].setJ(n);
			output[ii].enteringGates = new LinkedList<Gate<T>>();
		}

		for(int ii = 0; ii < m; ii++)
		{
			for(int jj = 0; jj < n; jj++)
			{
				gates[ii][jj] = new Gate<T>(functionList.get(randomGenerator.nextInt(functionList.size())), ii, jj);
			}
		}
		linkAllGates();// Links creation
	}

	/**
	 * Copying constructor
	 * 
	 * @param grid
	 */
	@SuppressWarnings("unchecked") public Grid(Grid<T> grid)
	{
		m = grid.m;// Parameters setting
		n = grid.n;
		randomGenerator = new Random();
		enteringGatesNumber = grid.getEnteringGatesNumber();
		functionList = new LinkedList<Functional<T>>(grid.functionList);
		for(int ii = 0; ii < grid.functionList.size(); ii++)
		{
			functionList.add(grid.functionList.get(ii));
		}
		inputNumber = grid.inputNumber;
		outputNumber = grid.outputNumber;

		input = new Gate[inputNumber];// Input setting
		for(int ii = 0; ii < inputNumber; ii++)
		{
			input[ii] = new Gate<T>(null, ii, -1);
			input[ii].value = grid.input[ii].value;
			input[ii].exitingGates = new LinkedList<Gate<T>>();
		}

		output = new Gate[outputNumber];// Output setting
		for(int ii = 0; ii < outputNumber; ii++)
		{
			output[ii] = new Gate<T>(null, ii, n);
			output[ii].enteringGates = new LinkedList<Gate<T>>();
		}

		gates = new Gate[m][n];
		for(int ii = 0; ii < m; ii++)// Gates setting
		{
			for(int jj = 0; jj < n; jj++)
			{
				gates[ii][jj] = new Gate<T>(grid.gates[ii][jj].getFunction(), ii, jj);
				gates[ii][jj].enteringGates = new LinkedList<Gate<T>>();
				gates[ii][jj].exitingGates = new LinkedList<Gate<T>>();
			}
		}

		for(int ii = 0; ii < m; ii++)// Gates setting
		{
			for(int jj = 0; jj < n; jj++)
			{
				for(int kk = 0; kk < grid.getGates()[ii][jj].enteringGates.size(); kk++)
				{
					int xx = grid.getGates()[ii][jj].enteringGates.get(kk).getI();
					int yy = grid.getGates()[ii][jj].enteringGates.get(kk).getJ();
					if(yy == -1)
					{
						linkGates(input[xx], gates[ii][jj]);
					}
					else if(yy == n)
					{
						linkGates(gates[xx][yy], output[xx]);
					}
					else
					{
						linkGates(gates[xx][yy], gates[ii][jj]);
					}
				}
				gates[ii][jj].value = grid.gates[ii][jj].value;
			}
		}

		for(int kk = 0; kk < outputNumber; kk++)
		{
			int ii = grid.output[kk].enteringGates.getFirst().getI();
			int jj = grid.output[kk].enteringGates.getFirst().getJ();
			if(jj == -1)
			{
				linkGates(input[ii], output[kk]);
			}
			else
			{
				linkGates(gates[ii][jj], output[kk]);
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
	public Gate<T>[][] getGates()
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
	public void setGates(Gate<T>[][] gates)
	{
		this.gates = gates;
	}

	/**
	 * @return
	 */
	public LinkedList<Functional<T>> getFunctionList()
	{
		return functionList;
	}

	/**
	 * @param functionList
	 */
	public void setFunctionList(LinkedList<Functional<T>> functionList)
	{
		this.functionList = functionList;
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

	/**
	 * @return
	 */
	public Gate<T>[] getInput()
	{
		return input;
	}

	/**
	 * @param input
	 */
	public void setInput(Gate<T>[] input)
	{
		this.input = input;
	}

	/**
	 * @return
	 */
	public Gate<T>[] getOutput()
	{
		return output;
	}

	/**
	 * @param output
	 */
	public void setOutput(Gate<T>[] output)
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
				System.out.print("(" + gates[ii][jj].getI() + "," + gates[ii][jj].getJ() + ")=" + gates[ii][jj].getFunction() + "	");
			}
			System.out.println();
		}
		System.out.println();

		for(int ii = 0; ii < m; ii++)
		{
			for(int jj = 0; jj < n; jj++)
			{
				for(int kk = 0; kk < gates[ii][jj].enteringGates.size(); kk++)
				{
					int i = gates[ii][jj].enteringGates.get(kk).getI();
					int j = gates[ii][jj].enteringGates.get(kk).getJ();
					System.out.println(i + "," + j + "--->" + "(" + gates[ii][jj].getI() + "," + gates[ii][jj].getJ() + ")");
				}
				System.out.println();
			}
		}
		System.out.println();

		for(int ii = 0; ii < outputNumber; ii++)
		{
			int i = output[ii].enteringGates.getFirst().getI();
			int j = output[ii].enteringGates.getFirst().getJ();
			System.out.println(i + "," + j + "--->" + "(" + output[ii].getI() + "," + output[ii].getJ() + ")");
		}
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
	 * Creates links between gates, input and output
	 */
	void linkAllGates()
	{
		int randomIi, randomJj;
		double gateInputProbability;

		// First column linking
		for(int ii = 0; ii < m; ii++)
		{
			for(int kk = 0; kk < enteringGatesNumber; kk++)
			{
				randomIi = randomGenerator.nextInt(inputNumber);
				this.linkGates(input[randomIi], gates[ii][0]);
			}
		}

		// Columns 1->n-1 linking
		for(int jj = 1; jj < n; jj++)
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

		// Output linking
		gateInputProbability = (double) inputNumber / (inputNumber + n * m);
		for(int ii = 0; ii < outputNumber; ii++)
		{
			if(randomGenerator.nextDouble() < gateInputProbability)
			{
				randomIi = randomGenerator.nextInt(inputNumber);
				linkGates(input[randomIi], output[ii]);
			}
			else
			{
				randomIi = randomGenerator.nextInt(m);
				randomJj = randomGenerator.nextInt(n);
				linkGates(gates[randomIi][randomJj], output[ii]);
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
					gates[ii][jj].setFunction(functionList.get(randomGenerator.nextInt(functionList.size())));
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

	/**
	 * @param val
	 *            Table of values, input shall be set with
	 */
	public void setInputValues(T val[])
	{
		if(val.length != inputNumber) return;
		int ii = 0;
		for(T x : val)
		{
			input[ii].setValue(x);
			ii++;
		}
	}

	/**
	 * Counts value for every gate
	 */
	public void calculateValueForEveryGate()
	{
		for(int jj = 0; jj < n; jj++)
		{
			for(int ii = 0; ii < m; ii++)
			{
				LinkedList<T> values = new LinkedList<T>();
				for(int kk = 0; kk < enteringGatesNumber; kk++)
				{
					values.add(gates[ii][jj].getEnteringGates().get(kk).getValue());
				}
				gates[ii][jj].setValue(gates[ii][jj].getFunction().calculateValue(values));
			}
		}

		for(int jj = 0; jj < outputNumber; jj++)
		{
			output[jj].setValue(output[jj].getEnteringGates().getFirst().getValue());
		}
	}

	/**
	 * Recursive function counting value for gate
	 * 
	 * @param ii
	 *            Gate's rows
	 * @param jj
	 *            Gate's column
	 * @return Gate's value
	 */
	public T calculateGateValue(int ii, int jj)
	{
		T value = null;
		LinkedList<T> list = new LinkedList<T>();
		if(jj == 0)
		{
			int n = gates[ii][jj].getFunction().argsNumber();
			for(int kk = 0; kk < n; kk++)
			{
				list.add(gates[ii][jj].getEnteringGates().get(kk).getValue());
			}
			return gates[ii][jj].getFunction().calculateValue(list);
		}
		else if(jj == -1)
		{
			return input[ii].value;
		}
		else
		{
			int n = gates[ii][jj].getFunction().argsNumber();
			for(int kk = 0; kk < n; kk++)
			{
				list.add(calculateGateValue(gates[ii][jj].getEnteringGates().get(kk).getI(), gates[ii][jj].getEnteringGates().get(kk).getJ()));
			}
		}
		value = gates[ii][jj].getFunction().calculateValue(list);
		return value;
	}

	/**
	 * Function returns value on chosen output. Input must be initialized!
	 * 
	 * @param arg
	 *            Number of output value is returned from
	 * @return value on chosen output
	 */
	public T calculateValueOnOutput(int arg)
	{
		Gate<T> lastGate = output[arg].getEnteringGates().getFirst();
		int ii = lastGate.getI();
		int jj = lastGate.getJ();
		return calculateGateValue(ii, jj);
	}
}
