package cartessian.genetic.programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import cartessian.genetic.programming.fitness.Functional;

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
	 * Two dimensional table of gates
	 */
	private Gate<T> gates[][];

	/**
	 * Number of inputs
	 */
	private int inputNumber;

	/**
	 * Table of inputs
	 */
	private Gate<T> input[];

	/**
	 * Number of outputs
	 */
	private int outputNumber;

	/**
	 * Table of outputs
	 */
	private Gate<T> output[];

	/**
	 * Randomness engine
	 */
	private Random randomGenerator;

	/**
	 * List of available functions
	 */
	private LinkedList<Functional<T>> functionList;

	/**
	 * Probability of occurrence of forward link
	 */
	private double recurrentProbability;

	/**
	 * Relinking probability
	 */
	private double probability;

	/**
	 * Array list of gates active in calculating outputs value
	 */
	private ArrayList<Gate<T>> activeGates;

	/**
	 * Gate's default value
	 */
	private T initialValue;

	/**
	 * Default constructor
	 */
	public Grid()
	{
	}

	/**
	 * @param functions
	 *            List of available functions
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
		this.initialValue = initialValue;

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
		initialValue = grid.getInitialValue();

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
	 * Returns number of rows
	 * 
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
	 * Returns number of columns
	 * 
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
	 * Returns recurrent probability
	 * 
	 * @return recurrentProbability
	 */
	public double getRecurrentProbability()
	{
		return recurrentProbability;
	}

	/**
	 * Sets probability of link shall be forward
	 * 
	 * @param recurrentProbability
	 *            recurrentProbability
	 */
	public void setRecurrentProbability(double recurrentProbability)
	{
		this.recurrentProbability = recurrentProbability;
	}

	/**
	 * Returns probability of switching link
	 * 
	 * @return probability probability
	 */
	public double getProbability()
	{
		return probability;
	}

	/**
	 * Sets probability of switching link
	 * 
	 * @param probability
	 *            probability
	 */
	public void setProbability(double probability)
	{
		this.probability = probability;
	}

	/**
	 * Returns two dimensional table of gates
	 * 
	 * @return two dimensional table of gates
	 */
	public Gate<T>[][] getGates()
	{
		return gates;
	}

	/**
	 * Sets value of every gate excluding inputs and outputs
	 * 
	 * @param value
	 *            Value every gate shall hold
	 */
	public void setGatesValue(T value)
	{
		for(int ii = 0; ii < m; ii++)
			for(int jj = 0; jj < n; jj++)
				gates[ii][jj].setValue(value);
	}

	/**
	 * Returns number of gates entering single gate
	 * 
	 * @return Number of gates entering single gate
	 */
	public int getEnteringGatesNumber()
	{
		return enteringGatesNumber;
	}

	/**
	 * Sets entering gates number
	 * 
	 * @param enteringGatesNumber
	 *            Number of gates entering single gate
	 */
	public void setEnteringGatesNumber(int enteringGatesNumber)
	{
		this.enteringGatesNumber = enteringGatesNumber;
	}

	/**
	 * Returns linked list of available functions gate can hold
	 * 
	 * @return list of available functions
	 */
	public LinkedList<Functional<T>> getFunctionList()
	{
		return functionList;
	}

	/**
	 * Sets linked list of available functions for single gate
	 * 
	 * @param functionList
	 *            list of available functions
	 */
	public void setFunctionList(LinkedList<Functional<T>> functionList)
	{
		this.functionList = functionList;
	}

	/**
	 * Returns number of inputs
	 * 
	 * @return number of inputs
	 */
	public int getInputNumber()
	{
		return inputNumber;
	}

	/**
	 * Sets number of inputs
	 * 
	 * @param inputNumber
	 *            number of inputs
	 */
	public void setInputNumber(int inputNumber)
	{
		this.inputNumber = inputNumber;
	}

	/**
	 * Returns number of outputs
	 * 
	 * @return number of outputs
	 */
	public int getOutputNumber()
	{
		return outputNumber;
	}

	/**
	 * Sets number of outputs
	 * 
	 * @param outputNumber
	 *            number of outputs
	 */
	public void setOutputNumber(int outputNumber)
	{
		this.outputNumber = outputNumber;
	}

	/**
	 * Returns table of input gates
	 * 
	 * @return Table of input gates
	 */
	public Gate<T>[] getInput()
	{
		return input;
	}

	/**
	 * Sets table of input gates
	 * 
	 * @param input
	 *            table of input gates
	 */
	public void setInput(Gate<T>[] input)
	{
		this.input = input;
	}

	/**
	 * Returns table of output gates
	 * 
	 * @return table of output gates
	 */
	public Gate<T>[] getOutput()
	{
		return output;
	}

	/**
	 * Sets table of output gates
	 * 
	 * @param output
	 *            table of output gates
	 */
	public void setOutput(Gate<T>[] output)
	{
		this.output = output;
	}

	/**
	 * Returns active gates list
	 * 
	 * @return active gates list
	 */
	public ArrayList<Gate<T>> getActiveGates()
	{
		return activeGates;
	}

	/**
	 * Returns gate initial value
	 * 
	 * @return gate initial value
	 */
	public T getInitialValue()
	{
		return initialValue;
	}

	/**
	 * Sets gate initial value
	 * 
	 * @param initialValue
	 *            gate initial value
	 */
	public void setInitialValue(T initialValue)
	{
		this.initialValue = initialValue;
	}

	/**
	 * Adds to active gates list every gate that is needed to count value on
	 * output
	 * 
	 * @param gate
	 *            gate function starts adding to active gates list from
	 */
	private void addToActiveGates(Gate<T> gate)
	{
		int ii = gate.getI(), jj = gate.getJ();
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
			if(!activeGates.contains(gate))
			{
				activeGates.add(gate);
				for(int kk = 0; kk < enteringGatesNumber; kk++)
				{
					addToActiveGates(gate.getEnteringGates().get(kk));
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
		activeGates.add(output[num]);
		addToActiveGates(output[num].getEnteringGates().getFirst());
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
		Collections.sort(activeGates, new Comparator<Gate<T>>()
		{
			@Override public int compare(Gate<T> g1, Gate<T> g2)
			{
				if(g1.getJ() == g2.getJ()) return 0;
				return g1.getJ() > g2.getJ() ? 1 : -1;
			}
		});
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
				for(int kk = 0; kk < enteringGatesNumber; kk++)
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
	 * Draws with given probability, whether gate's function shall be switched
	 * If so draws new one form uniform distribution between 0 and
	 * gate.operationNumber. Function repeats it for every gate in grid
	 */
	protected void reassignGatesFunction()
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
	 * Sets input values with value given in generic table with given order
	 * 
	 * @warning If table of value and input have different length function is
	 *          terminated
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
	 * @param gate
	 *            gate whose value is calculated
	 * 
	 * @return gate's value
	 */
	public T calculateGateValue(Gate<T> gate)
	{
		int ii = gate.getI(), jj = gate.getJ();
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
				for(Gate<T> x : gateList)
				{
					valueList.add(x.getValue());
				}
			}
			else
			{
				for(Gate<T> x : gateList)
				{
					valueList.add(calculateGateValue(x));
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
				calculateGateValue(gate);
			}
			return output[arg].getValue();
		}
		else
		{
			Gate<T> lastGate = output[arg].getEnteringGates().getFirst();
			return calculateGateValue(lastGate);
		}
	}

	/**
	 * Sets input, gates and output values with initial value
	 */
	public void clearGatesValues()
	{
		for(int ii = 0; ii < m; ii++)
			for(int jj = 1; jj < n; jj++)
				gates[ii][jj].setValue(initialValue);

		for(int ii = 0; ii < inputNumber; ii++)
			input[ii].setValue(initialValue);

		for(int ii = 0; ii < outputNumber; ii++)
			output[ii].setValue(initialValue);

	}

}
