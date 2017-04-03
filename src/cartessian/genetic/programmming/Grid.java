package cartessian.genetic.programmming;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import cartessian.genetic.programmming.function.Functional;

/**
 * Grid is a class responsible for execution user requirements. It has 3 main
 * parts: input, gates and output. Input and output are gates held in
 * 1-dimensional table. Gates are held in 2-dimensional table. Input and output
 * have not got functions. Input has empty enteringGates, Output empty
 * exitingGates.
 * 
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            Value type gates base on
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
	 * Randomness engine
	 */
	private Random randomGenerator;

	/**
	 * List of functions, that single gates can hold
	 */
	private LinkedList<Functional<T>> functionList;

	/**
	 * Probability link is forward, while linking gates
	 */
	private double recurrentProbability;

	/**
	 * Probability link will be switched
	 */
	private double probability;

	/**
	 * List holding references of gates active in calculating value on all
	 * outputs
	 */
	private ArrayList<Gate<T>> activeGates;

	/**
	 * Default constructor
	 */
	public Grid()
	{
	}

	/**
	 * @param functions
	 *            List of functions that can be held in gate
	 * @param inputNum
	 *            Number of inputs
	 * @param outputNum
	 *            Number of inputs
	 * @param m
	 *            Number of rows
	 * @param n
	 *            Number of columns
	 * @param initialValue
	 *            Value all gates shall be initiated with
	 * @param probability
	 *            Probability link shall be switched
	 * @param recurrentProbability
	 *            Probability if switched link shall be forward
	 */
	@SuppressWarnings("unchecked") public Grid(LinkedList<Functional<T>> functions, int inputNum, int outputNum, int m, int n, T initialValue, double probability, double recurrentProbability)
	{
		this.m = m;
		this.n = n;
		this.gates = new Gate[m][n];
		this.randomGenerator = new Random();
		this.recurrentProbability = recurrentProbability;
		this.probability = probability;

		this.inputNumber = inputNum;
		this.input = new Gate[inputNumber];
		for(int ii = 0; ii < inputNumber; ii++)
		{
			this.input[ii] = new Gate<T>();
			this.input[ii].setI(ii);
			this.input[ii].setJ(-1);
			this.input[ii].exitingGates = new LinkedList<Gate<T>>();
		}

		this.outputNumber = outputNum;
		this.output = new Gate[outputNumber];
		for(int ii = 0; ii < outputNumber; ii++)
		{
			this.output[ii] = new Gate<T>();
			this.output[ii].setI(ii);
			this.output[ii].setJ(n);
			this.output[ii].enteringGates = new LinkedList<Gate<T>>();
		}

		this.functionList = new LinkedList<Functional<T>>();
		this.enteringGatesNumber = functions.get(0).argsNumber();
		for(Functional<T> func : functions)
		{
			this.functionList.add(func);
			if(func.argsNumber() > this.enteringGatesNumber) this.enteringGatesNumber = func.argsNumber();
		}
		for(int jj = 0; jj < n; jj++)
		{
			for(int ii = 0; ii < m; ii++)
			{
				this.gates[ii][jj] = new Gate<T>(this.functionList.get(this.randomGenerator.nextInt(this.functionList.size())), ii, jj, initialValue);
			}
		}

		this.activeGates = new ArrayList<Gate<T>>();
		linkAllGates();
	}

	/**
	 * Copying constructor
	 * 
	 * @param grid
	 *            Grid that is model for new grid
	 */
	@SuppressWarnings("unchecked") public Grid(Grid<T> grid)
	{
		m = grid.m;
		n = grid.n;
		randomGenerator = new Random();
		enteringGatesNumber = grid.getEnteringGatesNumber();
		functionList = new LinkedList<Functional<T>>(grid.functionList);
		for(Functional<T> func : grid.functionList)
		{
			functionList.add(func);
		}
		inputNumber = grid.inputNumber;
		outputNumber = grid.outputNumber;
		probability = grid.getProbability();
		recurrentProbability = grid.getRecurrentProbability();

		T initialValue = grid.getGates()[0][0].getValue();
		input = new Gate[inputNumber];
		for(int ii = 0; ii < inputNumber; ii++)
		{
			input[ii] = new Gate<T>(null, ii, -1, initialValue);
			input[ii].value = grid.input[ii].value;
			input[ii].exitingGates = new LinkedList<Gate<T>>();
		}

		output = new Gate[outputNumber];
		for(int ii = 0; ii < outputNumber; ii++)
		{
			output[ii] = new Gate<T>(null, ii, n, initialValue);
			output[ii].enteringGates = new LinkedList<Gate<T>>();
		}

		gates = new Gate[m][n];
		for(int ii = 0; ii < m; ii++)
		{
			for(int jj = 0; jj < n; jj++)
			{
				gates[ii][jj] = new Gate<T>(grid.gates[ii][jj].getFunction(), ii, jj, initialValue);
				gates[ii][jj].enteringGates = new LinkedList<Gate<T>>();
				gates[ii][jj].exitingGates = new LinkedList<Gate<T>>();
				gates[ii][jj].setValue(grid.getGates()[0][0].getValue());
			}
		}

		for(int ii = 0; ii < m; ii++)
		{
			for(int jj = 0; jj < n; jj++)
			{
				for(int kk = 0; kk < enteringGatesNumber; kk++)
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

		activeGates = new ArrayList<Gate<T>>();
		int ii, jj;
		for(Gate<T> x : grid.getActiveGates())
		{
			ii = x.getI();
			jj = x.getJ();
			if(jj == -1) activeGates.add(input[ii]);
			else if(jj == n) activeGates.add(output[ii]);
			else
				activeGates.add(gates[ii][jj]);
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
	 * @param n
	 *            number of columns
	 */
	public void setN(int n)
	{
		this.n = n;
	}

	/**
	 * @return recurrentProbability recurrentProbability
	 */
	public double getRecurrentProbability()
	{
		return recurrentProbability;
	}

	/**
	 * @param recurrentProbability
	 *            recurrentProbability
	 */
	public void setRecurrentProbability(double recurrentProbability)
	{
		this.recurrentProbability = recurrentProbability;
	}

	/**
	 * @return probability probability
	 */
	public double getProbability()
	{
		return probability;
	}

	/**
	 * @param probability
	 *            probability
	 */
	public void setProbability(double probability)
	{
		this.probability = probability;
	}

	/**
	 * @return two dimensional table of gates
	 */
	public Gate<T>[][] getGates()
	{
		return gates;
	}

	/**
	 * @param value
	 *            Value every gate shall hold
	 */
	public void setGatesValue(T value)
	{
		for(int ii = 0; ii < m; ii++)
		{
			for(int jj = 0; jj < n; jj++)
			{
				gates[ii][jj].setValue(value);
			}
		}
	}

	/**
	 * @return Number of gates entering single gate
	 */
	public int getEnteringGatesNumber()
	{
		return enteringGatesNumber;
	}

	/**
	 * @param enteringGatesNumber
	 *            Number of gates entering single gate
	 */
	public void setEnteringGatesNumber(int enteringGatesNumber)
	{
		this.enteringGatesNumber = enteringGatesNumber;
	}

	/**
	 * @return functionList functionList
	 */
	public LinkedList<Functional<T>> getFunctionList()
	{
		return functionList;
	}

	/**
	 * @param functionList
	 *            functionList
	 */
	public void setFunctionList(LinkedList<Functional<T>> functionList)
	{
		this.functionList = functionList;
	}

	/**
	 * @return inputNumber inputNumber
	 */
	public int getInputNumber()
	{
		return inputNumber;
	}

	/**
	 * @param inputNumber
	 *            inputNumber
	 */
	public void setInputNumber(int inputNumber)
	{
		this.inputNumber = inputNumber;
	}

	/**
	 * @return outputNumber outputNumber
	 */
	public int getOutputNumber()
	{
		return outputNumber;
	}

	/**
	 * @param outputNumber
	 *            outputNumber
	 */
	public void setOutputNumber(int outputNumber)
	{
		this.outputNumber = outputNumber;
	}

	/**
	 * @return Table of gate that are input
	 */
	public Gate<T>[] getInput()
	{
		return input;
	}

	/**
	 * @param input
	 *            input
	 */
	public void setInput(Gate<T>[] input)
	{
		this.input = input;
	}

	/**
	 * @return output
	 */
	public Gate<T>[] getOutput()
	{
		return output;
	}

	/**
	 * @param output
	 *            output
	 */
	public void setOutput(Gate<T>[] output)
	{
		this.output = output;
	}

	/**
	 * Returns active gates list
	 * 
	 * @return active gate list
	 */
	public ArrayList<Gate<T>> getActiveGates()
	{
		return activeGates;
	}

	/**
	 * Adds to active gates list every gate that is needed to count value on
	 * output
	 * 
	 * @param ii
	 *            gates rows
	 * @param jj
	 *            gates column
	 */
	private void addToActiveGates(int ii, int jj)
	{
		Gate<T> gate;
		int iPrim, jPrim;
		if(jj == -1)
		{
			gate = input[ii];
			if(!activeGates.contains(gate))
			{
				activeGates.add(gate);
			}
			return;
		}
		else
		{
			gate = gates[ii][jj];
			if(!activeGates.contains(gate))
			{
				activeGates.add(gate);
				for(int kk = 0; kk < enteringGatesNumber; kk++)
				{
					iPrim = gate.getEnteringGates().get(kk).getI();
					jPrim = gate.getEnteringGates().get(kk).getJ();
					addToActiveGates(iPrim, jPrim);
				}
			}
			else
				return;
		}
	}

	/**
	 * Add every gate leading to output signed by given number including input
	 * and output. List is order form input to output. Function is used only if
	 * recurrent probability is not equal to zero
	 * 
	 * @param num
	 *            Number of input
	 */
	private void setOutputActiveGates(int num)
	{
		int ii, jj;
		activeGates.add(output[num]);
		ii = output[num].getEnteringGates().getFirst().getI();
		jj = output[num].getEnteringGates().getFirst().getJ();
		addToActiveGates(ii, jj);

		Gate<T> gate0, gate1;
		for(int kk = 0; kk < activeGates.size() / 2; kk++)
		{
			gate0 = activeGates.get(kk);
			gate1 = activeGates.get(activeGates.size() - 1 - kk);
			activeGates.remove(kk);
			activeGates.remove(activeGates.size() - 1 - kk);
			activeGates.add(activeGates.size() - kk, gate0);
			activeGates.add(kk, gate1);
		}
	}

	/**
	 * Clears list of active values later adds all gates that are needed to
	 * count value on output
	 */
	private void setActiveGates()
	{
		activeGates.clear();
		for(int ii = 0; ii < output.length; ii++)
			setOutputActiveGates(ii);
	}

	/**
	 * Prints gird on the stand output
	 */
	public void printGrid()
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
	 *            Gate entering g2
	 * @param g2
	 *            Gate exiting g1
	 */
	private void linkGates(Gate<T> g1, Gate<T> g2)
	{
		g2.addEnteringGate(g1);
		g1.addExitingGate(g2);

	}

	/**
	 * Links two gates adding g2 to list of g1's exiting gates at given position
	 * 
	 * @param g1
	 *            Gate entering g2
	 * @param g2
	 *            Gate exiting g1
	 */
	private void linkGates(Gate<T> g1, Gate<T> g2, int position)
	{
		g2.getEnteringGates().add(position, g1);
		g1.getExitingGates().add(g2);
	}

	/**
	 * Removes link between g1 and g2. Functions removes g1 from
	 * g2.enteringGates and g2 form g1.exitingGates
	 * 
	 * @param g1
	 *            Gate entering g2
	 * @param g2
	 *            Gate exiting g1
	 */
	private void removeLink(Gate<T> g1, Gate<T> g2)
	{
		g1.getExitingGates().remove(g2);
		g2.getEnteringGates().remove(g1);
	}

	/**
	 * Creates links between gates, input and output, gates can be linked to
	 * following gates
	 */
	private void linkAllGates()
	{
		int randomIi, randomJj;
		double gateInputProbability;

		for(int jj = 0; jj < n; jj++)
		{
			gateInputProbability = (double) inputNumber / (inputNumber + jj * m);
			for(int ii = 0; ii < m; ii++)
			{
				for(int kk = 0; kk < enteringGatesNumber; kk++)
				{
					if(recurrentProbability != 0 && randomGenerator.nextDouble() < recurrentProbability)
					{
						randomIi = randomGenerator.nextInt(m);
						randomJj = jj + randomGenerator.nextInt(n - jj);
						linkGates(gates[randomIi][randomJj], gates[ii][jj]);
					}
					else
					{
						if(randomGenerator.nextDouble() < gateInputProbability || jj == 0)
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
		}

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
	 *            gate, whose links shall be switched
	 * @param linkProbability
	 *            Probability of switching link
	 */
	private void relinkGate(Gate<T> gate)
	{
		int column, row, jj = gate.getJ();
		double gateInputProbability = (double) inputNumber / (inputNumber + jj * m);
		for(int kk = 0; kk < enteringGatesNumber; kk++)
		{
			if(randomGenerator.nextDouble() < probability)
			{
				removeLink(gate.getEnteringGates().get(kk), gate);

				if(recurrentProbability != 0 && randomGenerator.nextDouble() < recurrentProbability)
				{
					row = randomGenerator.nextInt(m);
					column = jj + randomGenerator.nextInt(n - jj);
					linkGates(gates[row][column], gate, kk);
				}
				else
				{
					if(randomGenerator.nextDouble() < gateInputProbability || (jj == 0))
					{
						row = randomGenerator.nextInt(inputNumber);
						linkGates(input[row], gate, kk);
					}
					else
					{
						column = randomGenerator.nextInt(jj);
						row = randomGenerator.nextInt(m);
						linkGates(gates[row][column], gate, kk);
					}
				}
			}
		}
	}

	/**
	 * Draws with given probability, whether gate's operation shall be switched
	 * If so draws new one form uniform distribution between 0 and
	 * gate.operationNumber. Function repeats it for every gate in grid
	 * 
	 * @param logicGateProbability	logicGateProbability
	 */
	protected void reassignGatesOperation()
	{
		for(int jj = 0; jj < n; jj++)
		{
			for(int ii = 0; ii < m; ii++)
			{
				if(randomGenerator.nextDouble() < probability)
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
	protected void relinkAllGates()
	{
		for(int jj = 0; jj < n; jj++)
		{
			for(int ii = 0; ii < m; ii++)
			{
				relinkGate(gates[ii][jj]);
			}
		}

		int column, row;
		double inputProbability = (double) inputNumber / (inputNumber + m * n);
		for(int ii = 0; ii < outputNumber; ii++)
		{
			if(randomGenerator.nextDouble() < probability)
			{
				removeLink(output[ii].getEnteringGates().getFirst(), output[ii]);
				if(randomGenerator.nextDouble() < inputProbability)
				{
					row = randomGenerator.nextInt(inputNumber);
					linkGates(input[row], output[ii]);
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
	 * Sets values on input
	 * 
	 * @param val
	 *            Table of values, input shall be set with
	 */
	public void setInputValues(T val[])
	{
		if(val.length != inputNumber) return;
		int kk = 0;
		for(T x : val)
		{
			input[kk].setValue(x);
			kk++;
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
					T value = gates[ii][jj].getEnteringGates().get(kk).getValue();
					values.add(value);
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
	 * 
	 * @return Gate's value
	 */
	public T calculateGateValue(int ii, int jj)
	{
		LinkedList<T> valueList = new LinkedList<T>();
		LinkedList<Gate<T>> gateList;
		if(jj == -1)
		{
			return input[ii].getValue();
		}
		else if(jj == n)
		{
			output[ii].setValue(output[ii].getEnteringGates().getFirst().getValue());
			return output[ii].getValue();
		}
		else
		{
			gateList = gates[ii][jj].getEnteringGates();
			if(recurrentProbability != 0)
			{
				for(Gate<T> gate : gateList)
				{
					valueList.add(gate.getValue());
				}
			}
			else
			{
				for(Gate<T> gate : gateList)
				{
					int iPrim = gate.getI();
					int jPrim = gate.getJ();
					valueList.add(calculateGateValue(iPrim, jPrim));
				}
			}
		}
		gates[ii][jj].setValue(gates[ii][jj].getFunction().calculateValue(valueList));

		return gates[ii][jj].getValue();
	}

	/**
	 * Returns value on chosen output and sets input value. Input must be
	 * initialized!
	 * 
	 * @param arg
	 *            Number of output value is returned from
	 * 
	 * @return value on chosen output
	 */
	public T calculateOutputValue(int arg)
	{
		setActiveGates();
		if(recurrentProbability != 0)
		{
			for(Gate<T> gate : activeGates)
			{
				calculateGateValue(gate.getI(), gate.getJ());
			}
			return output[arg].getValue();
		}
		else
		{
			Gate<T> lastGate = output[arg].getEnteringGates().getFirst();
			int ii = lastGate.getI();
			int jj = lastGate.getJ();
			return calculateGateValue(ii, jj);
		}
	}

}
