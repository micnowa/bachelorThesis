package cartessian.genetic.programmming;

import java.util.LinkedList;

public class Gate
{
	protected int i;
	protected int j;
	protected int operation;
	protected LinkedList<Gate> enteringGates = new LinkedList<Gate>();
	protected LinkedList<Gate> exitingGates = new LinkedList<Gate>();
	
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
	public Gate(int operation,int ii,int jj)
	{
		this.operation = operation;
		this.i = ii;
		this.j = jj;
	}

	/**
	 * Returns gate's operation
	 * 
	 * @return      operation in gate
	 */
	public int getOperation()
	{
		return this.operation;
	}

	/**
	 * Sets gate's operation
	 *
	 * @param  operation	Operation, that should be in the gate
	 */
	public void setOperation(int operation)
	{
		this.operation = operation;
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
	 *	Sets gate's I-position
	 *
	 * @param  i	I-position
	 */
	public void setI(int i)
	{
		this.i = i;
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
	 *	Sets gate's J-position
	 *
	 * @param  j	J-position
	 */
	public void setJ(int j)
	{
		this.j = j;
	}
	
	/**
	 *	Returns size of LinkedList of gates, gate is pointed to
	 */
	public int getEnteringGatesNumber()
	{
		return enteringGates.size();
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
	void addEnteringGate(Gate gate)
	{
		enteringGates.add(gate);
	}
	
	
	/**
	 *	Adds a gate to LinkedList of gates, gate points to
	 *
	 * @param  gate	Gate that should be added to gates, gate points to
	 */
	void addExitingGate(Gate gate)
	{
		exitingGates.add(gate);
	}
	
	
	/**
	 *	Returns LinkedList of gates, gate is pointed to
	 *
	 * @return LinkedList of gates, gate is pointed to
	 */
	public LinkedList<Gate> getEnteringGates()
	{
		return enteringGates;
	}
	
	/**
	 * Sets list of gate, that gate is pointed to
	 *
	 * @param  gatesEntering	List of gates, gate is pointed to
	 */
	public void setEnteringGates(LinkedList<Gate> gatesEntering)
	{
		this.enteringGates = gatesEntering;
	}
	
	
	/**
	 *	Returns LinkedList of gates, gate points to
	 *
	 * @return	LinkedList<Gate> List of gates, gate points to 
	 */
	public LinkedList<Gate> getExitingGates()
	{
		return exitingGates;
	}
	
	/**
	 *	Sets gates that gate points to
	 * 
	 * @param	gatesExiting LinkedList of gates exiting gate	
	 */
	public void setExitingGates(LinkedList<Gate> gatesExiting)
	{
		this.exitingGates = gatesExiting;
	}
	
	
	/**
	 *	Returns the result of the operation with given arguments
	 *
	 * @param  p	first argument, with type set by user
	 * @param  q	second argument, with type set by user
	 * @return      <User type>	Result of operation
	 */
	public boolean result(boolean p, boolean q)
	{
		switch (this.operation)
		{
			case 0: // AND
				return p & q;
			case 1: // OR
				return p || q;
			case 2: // XOR
				return ((p || q) && !(p && q));
			case 3: // NOR
				return !(p || q);
			case 4: // NAND
				return p ? !q : true;
			default:
				return false;
		}
	}
	
	
	/**
	 *	Prints on the standard output value returned by
	 *	operation called with given arguments
	 *
	 * @param  p	first argument, with type set by user
	 * @param  q	second argument, with type set by user
	 */
	public void printResult(boolean p, boolean q)
	{
		boolean r = this.result(p, q);
		System.out.println("Result: " + r);
	}
	

	/**
	 *	Prints gate's operation on the standard output
	 */
	public void printLogicGate()
	{
		System.out.print("(" + this.getOperation() + ")");
	}

}