package cartessian.genetic.programmming.operation;

import java.util.LinkedList;

public class Xor implements Operational<Boolean>
{

	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		Boolean a = list.get(0);
		Boolean b = list.get(1);
		return ((a || b) && !(a && b));//Good a XOR b
	}

	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "XOR";
	}

	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}
}
