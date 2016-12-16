package cartessian.genetic.programmming.operation;

public class And implements Operationable
{

	@Override public boolean calculateValue(boolean p, boolean q)
	{
		return p & q;
	}

	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "AND";
	}
}
