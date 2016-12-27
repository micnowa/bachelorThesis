package cartessian.genetic.programmming.operation;

import java.util.ArrayList;

public class Xor implements Operational<Boolean>
{

	@Override public Boolean calculateValue(ArrayList<Boolean> list)
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

}
