package cartessian.genetic.programmming.operation;

import java.util.LinkedList;

public class And implements Operational<Boolean>
{
	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		// TODO Auto-generated method stub
		return (list.get(0) && list.get(1));//Good a AND b
	}
	
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "AND";
	}

	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}
}
