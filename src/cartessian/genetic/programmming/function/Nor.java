package cartessian.genetic.programmming.function;

import java.util.LinkedList;

public class Nor implements Functional<Boolean>
{

	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		return !((list.get(0)) || list.get(1));//Good a NOR b
	}

	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "NOR";
	}
	
	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}

}
