package cartessian.genetic.programmming;

import java.util.LinkedList;

public class Gate<T>
{
	protected int i;
	protected int j;
	protected T operation;
	protected LinkedList<Gate<T>> enteringGates = new LinkedList<Gate<T>>();
	protected LinkedList<Gate<T>> exitingGates = new LinkedList<Gate<T>>();
	
	/**
	 *	Default Constructor
	 */
	public Gate()
	{
	}

	/**
	 *	Constructor with arguments
	 *
	 * @param  operation	operation in the gate
	 * @param  ii	i-position
	 * @param  jj	j-position
	 */
	public Gate(T operation,int ii,int jj)
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
	 *	Sets gate's I-position
	 *
	 * @param  i	I-position
	 */
	public void setI(int i)
	{
		this.i = i;
	}

	/**
	 *	Returns I-position of the gate
	 *
	 * @return      I-position
	 */
	public int getI()
	{
		return i;
	}

	/**
	 *	Sets gate's J-position
	 *
	 * @param  j	J-position
	 */
	public void setJ(int j)
	{
		this.j = j;
	}

	/**
	 *	Returns J-position of the gate
	 *
	 * @return	J-position
	 */
	public int getJ()
	{
		return j;
	}


	/**
	 *	Returns size of LinkedList of gates, gate is pointed to
	 */
	public int getEnteringGatesNumber()
	{
		return enteringGates.size();
	}
	
	
	/**
	 * Sets list of gate, that gate is pointed to
	 *
	 * @param  gatesEntering	List of gates, gate is pointed to
	 */
	public void setEnteringGates(LinkedList<Gate<T>> gatesEntering)
	{
		this.enteringGates = gatesEntering;
	}

	/**
	 *	Returns LinkedList of gates, gate is pointed to
	 *
	 * @return LinkedList of gates, gate is pointed to
	 */
	public LinkedList<Gate<T>> getEnteringGates()
	{
		return enteringGates;
	}

	/**
	 *	Returns size of LinkedList of gates, gate points to
	 *
	 * @return	Size of LinkedList of gates, gate points to
	 */
	public int getExitingGatesNumber()
	{
		return exitingGates.size();
	}
	
	
	/**
	 *	Adds a gate to LinkedList of gates, gate is pointed to
	 *
	 * @param  gate	Gate that should be added to gates, gate is pointed to
	 */
	void addEnteringGate(Gate<T> gate)
	{
		enteringGates.add(gate);
	}
	
	
	/**
	 *	Adds a gate to LinkedList of gates, gate points to
	 *
	 * @param  gate	Gate that should be added to gates, gate points to
	 */
	void addExitingGate(Gate<T> gate)
	{
		exitingGates.add(gate);
	}
	
	
	/**
	 *	Returns LinkedList of gates, gate points to
	 *
	 * @return	LinkedList<Gate> List of gates, gate points to 
	 */
	public LinkedList<Gate<T>> getExitingGates()
	{
		return exitingGates;
	}
	
	
	/**
	 *	Sets gates that gate points to
	 * 
	 * @param	gatesExiting LinkedList of gates exiting gate	
	 */
	public void setExitingGates(LinkedList<Gate<T>> gatesExiting)
	{
		this.exitingGates = gatesExiting;
	}
	
}