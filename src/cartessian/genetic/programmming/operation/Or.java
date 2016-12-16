package cartessian.genetic.programmming.operation;

public class Or implements Operationable
{

	@Override public boolean calculateValue(boolean p, boolean q)
	{
		return p || q;
	}
	
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "OR";
	}

}
