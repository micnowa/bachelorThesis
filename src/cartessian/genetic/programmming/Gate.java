package cartessian.genetic.programmming;

import java.util.LinkedList;

public class Gate
{
	int i;
	int j;
	private int logicOperation;
	private LinkedList<Gate> enteringGates = new LinkedList<Gate>();
	private LinkedList<Gate> exitingGates = new LinkedList<Gate>();

	public Gate()
	{
		this.logicOperation = 4;
	}

	public Gate(int logicOperation,int ii,int jj)
	{
		this.logicOperation = logicOperation;
		this.i = ii;
		this.j = jj;
	}

	public int getLogicOperation()
	{
		return this.logicOperation;
	}

	public void setLogicOperation(int logicOperation)
	{
		this.logicOperation = logicOperation;
	}

	public int getI()
	{
		return i;
	}

	public void setI(int i)
	{
		this.i = i;
	}

	public int getJ()
	{
		return j;
	}

	public void setJ(int j)
	{
		this.j = j;
	}
	
	
	//Entering********************************
	public int getEnteringGatesNumber()
	{
		return enteringGates.size();
	}
	
	void addEnteringGate(Gate gate)
	{
		enteringGates.add(gate);
	}
	
	public LinkedList<Gate> getEnteringGates()
	{
		return enteringGates;
	}
	//******************************************
	
	
	//Exiting********************************
	public int getExitingGatesNumber()
	{
		return exitingGates.size();
	}
	
	void addExitingGate(Gate gate)
	{
		exitingGates.add(gate);
	}
	
	public LinkedList<Gate> getExitingGates()
	{
		return exitingGates;
	}
	//******************************************
	
	
	public boolean result(boolean p, boolean q)
	{
		switch (this.logicOperation)
		{
			case 1: // AND
				return p & q;
			case 2: // OR
				return p || q;
			case 3: // XOR
				return ((p || q) && !(p && q));
			case 4: // NOR
				return !(p || q);
			case 5: // NAND
				return p ? !q : true;
			default:
				return false;
		}
	}

	public void printResult(boolean p, boolean q)
	{
		boolean r = this.result(p, q);
		System.out.println("Resultat: " + r);
	}

	public void printLogicGate()
	{
		System.out.print("(" + this.getLogicOperation() + ")");
	}

}
