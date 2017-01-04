package cartessian.genetic.programmming;

import java.util.LinkedList;

public class Gate<T, U>
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
	 * Gate's operation
	 */
	protected T operation;
	/**
	 * Gate's value, retrieved after performed operation on values entering gate
	 */
	protected U value;
	/**
	 * List of gates, gate is pointed to
	 */
	protected LinkedList<Gate<T, U>> enteringGates = new LinkedList<Gate<T, U>>();
	/**
	 * List of gates, gate points to
	 */
	protected LinkedList<Gate<T, U>> exitingGates = new LinkedList<Gate<T, U>>();

	/**
	 * Default Constructor
	 */
	public Gate()
	{
	}

	/**
	 * Constructor with arguments
	 * 
	 * @param operation
	 *            operation in the gate
	 * @param ii
	 *            i-position
	 * @param jj
	 *            j-position
	 */
	public Gate(T operation, int ii, int jj)
	{
		this.operation = operation;
		this.i = ii;
		this.j = jj;
	}

	/**
	 * Sets the operation in gate
	 * 
	 * @param operation
	 */
	public void setOperation(T operation)
	{
		this.operation = operation;
	}

	/**
	 * @return Operation in the gate
	 */
	public T getOperation()
	{
		return operation;
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
	 * Returns size of LinkedList of gates, gate is pointed to
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
	public void setEnteringGates(LinkedList<Gate<T, U>> gatesEntering)
	{
		this.enteringGates = gatesEntering;
	}

	/**
	 * Returns LinkedList of gates, gate is pointed to
	 * 
	 * @return LinkedList of gates, gate is pointed to
	 */
	public LinkedList<Gate<T, U>> getEnteringGates()
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
	 * Adds a gate to LinkedList of gates, gate is pointed to
	 * 
	 * @param gate
	 *            Gate that should be added to gates, gate is pointed to
	 */
	void addEnteringGate(Gate<T, U> gate)
	{
		enteringGates.add(gate);
	}

	/**
	 * @param gate
	 *            gate to be added gate to
	 * @param pos
	 *            position which gate will be add at
	 */
	void addEnteringGateAt(int pos, Gate<T, U> gate)
	{
		enteringGates.add(pos, gate);
	}

	/**
	 * Adds a gate to LinkedList of gates, gate points to
	 * 
	 * @param gate
	 *            Gate that should be added to gates, gate points to
	 */
	void addExitingGate(Gate<T, U> gate)
	{
		exitingGates.add(gate);
	}

	/**
	 * Returns LinkedList of gates, gate points to
	 * 
	 * @return LinkedList<Gate> List of gates, gate points to
	 */
	public LinkedList<Gate<T, U>> getExitingGates()
	{
		return exitingGates;
	}

	/**
	 * Sets gates that gate points to
	 * 
	 * @param gatesExiting
	 *            LinkedList of gates exiting gate
	 */
	public void setExitingGates(LinkedList<Gate<T, U>> gatesExiting)
	{
		this.exitingGates = gatesExiting;
	}

	/**
	 * @return value hold in gate
	 */
	public U getValue()
	{
		return value;
	}

	/**
	 * @param value value to be hold in gate
	 */
	public void setValue(U value)
	{
		this.value = value;
	}
	
	
	

}