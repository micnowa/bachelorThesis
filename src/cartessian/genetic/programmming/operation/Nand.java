package cartessian.genetic.programmming.operation;

public class Nand implements Operationable
{

	@Override public boolean calculateValue(boolean p, boolean q)
	{
		return p ? !q : true;
	}
	
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "NAND";
	}
}
