package cartessian.genetic.programmming.function;

import java.util.LinkedList;

public class Nand implements Functional<Boolean>
{

	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		return (list.get(0)) ? !(list.get(1)) : true;//Good a NAND b
	}

	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "NAND";
	}
	
	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}
}
