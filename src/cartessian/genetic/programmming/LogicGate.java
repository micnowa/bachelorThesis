package cartessian.genetic.programmming;
import cartessian.genetic.programmming.Graph.*;


public class LogicGate extends Vertex
{
	private int logicOperation;

	public LogicGate()
	{
		super();
		this.number = this.logicOperation;
		this.logicOperation = 4;
	}

	public LogicGate(int logicOperation, int number)
	{
		super();
		this.number = number;
		this.logicOperation = logicOperation;
	}

	public int getLogicOperation()
	{
		return this.logicOperation;
	}

	public void setLogicOperation(int logicOperation)
	{
		this.logicOperation = logicOperation;
	}

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
		System.out.print(this.getNumber() + "(" + this.getLogicOperation() + ")");
	}

}
