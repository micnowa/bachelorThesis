package cartessian.genetic.programming;

import java.util.LinkedList;

import cartessian.genetic.programming.fitness.Functional;

/**
 * Class representing node in grid of functions. It is connected with other
 * gates. Such links are represented by two lists(enteringGates, exitingGates).
 * Position of grid is ii- number of row, j- number o column. Negative value of
 * j means gates is an input gate. Each gate has function and value(result of
 * function with values form gates entering this gate).
 * 
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            Operation
 */
public class Gate<T>
{
	/**
	 * Number of row
	 */
	protected int i;

	/**
	 * Number of column
	 */
	protected int j;

	/**
	 * Operation gate uses to calculate its value with given values of entering
	 * gates
	 */
	protected Functional<T> function;

	/**
	 * Gate's value, retrieved after performed operation on values entering gate
	 */
	protected T value;

	/**
	 * List of gates, gate is pointed to
	 */
	protected LinkedList<Gate<T>> enteringGates;
	/**
	 * List of gates, gate points to
	 */
	protected LinkedList<Gate<T>> exitingGates;

	/**
	 * Default Constructor, it does nothing
	 */
	public Gate()
	{
		enteringGates = new LinkedList<Gate<T>>();
		exitingGates = new LinkedList<Gate<T>>();
	}

	/**
	 * Constructor with arguments
	 * 
	 * @param operation
	 *            operation in the gate
	 * @param ii
	 *            row
	 * @param jj
	 *            column
	 */
	public Gate(Functional<T> function, int ii, int jj, T initialValue)
	{
		this.function = function;
		this.i = ii;
		this.j = jj;
		this.value = initialValue;
		enteringGates = new LinkedList<Gate<T>>();
		exitingGates = new LinkedList<Gate<T>>();
	}

	/**
	 * Sets the operation in gate
	 * 
	 * @param operation
	 *            operation gate uses
	 */
	public void setFunction(Functional<T> function)
	{
		this.function = function;
	}

	/**
	 * @return Function in the gate
	 */
	public Functional<T> getFunction()
	{
		return function;
	}

	/**
	 * Sets row
	 * 
	 * @param i
	 *            I-position
	 */
	public void setI(int i)
	{
		this.i = i;
	}

	/**
	 * Returns row
	 * 
	 * @return I-position
	 */
	public int getI()
	{
		return i;
	}

	/**
	 * Sets column
	 * 
	 * @param j
	 *            J-position
	 */
	public void setJ(int j)
	{
		this.j = j;
	}

	/**
	 * Returns column
	 * 
	 * @return J-position
	 */
	public int getJ()
	{
		return j;
	}

	/**
	 * @return size of LinkedList of gates, gate is pointed to
	 */
	public int getEnteringGatesNumber()
	{
		return enteringGates.size();
	}

	/**
	 * Sets list of gate, that gate is pointed to
	 * 
	 * @param gatesEntering
	 *            List of gates, gate is pointed to
	 */
	public void setEnteringGates(LinkedList<Gate<T>> gatesEntering)
	{
		this.enteringGates = gatesEntering;
	}

	/**
	 * Returns LinkedList of gates, gate is pointed to
	 * 
	 * @return LinkedList of gates, gate is pointed to
	 */
	public LinkedList<Gate<T>> getEnteringGates()
	{
		return enteringGates;
	}

	/**
	 * Returns size of LinkedList of gates, gate points to
	 * 
	 * @return Size of LinkedList of gates, gate points to
	 */
	public int getExitingGatesNumber()
	{
		return exitingGates.size();
	}

	/**
	 * Returns LinkedList of gates, gate points to
	 * 
	 * @return LinkedList<Gate> List of gates, gate points to
	 */
	public LinkedList<Gate<T>> getExitingGates()
	{
		return exitingGates;
	}

	/**
	 * Sets gates that gate points to
	 * 
	 * @param gatesExiting
	 *            LinkedList of gates exiting gate
	 */
	public void setExitingGates(LinkedList<Gate<T>> gatesExiting)
	{
		this.exitingGates = gatesExiting;
	}

	/**
	 * @return value hold in gate
	 */
	public T getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *            value to be hold in gate
	 */
	public void setValue(T value)
	{
		this.value = value;
	}

	/**
	 * Adds a gate to LinkedList of gates, gate is pointed to
	 * 
	 * @param gate
	 *            Gate that should be added to gates, gate is pointed to
	 */
	void addEnteringGate(Gate<T> gate)
	{
		enteringGates.add(gate);
	}

	/**
	 * @param gate
	 *            gate to be added gate to
	 * @param pos
	 *            position which gate will be add at
	 */
	void addEnteringGateAt(int pos, Gate<T> gate)
	{
		enteringGates.add(pos, gate);
	}

	/**
	 * Adds a gate to LinkedList of gates, gate points to
	 * 
	 * @param gate
	 *            Gate that should be added to gates, gate points to
	 */
	void addExitingGate(Gate<T> gate)
	{
		exitingGates.add(gate);
	}

}