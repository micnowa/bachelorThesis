package cartessian.genetic.programmming.operation;

import java.util.ArrayList;

public class Nand implements Operational<Boolean>
{

	@Override public Boolean calculateValue(ArrayList<Boolean> list)
	{
		return (list.get(0)) ? !(list.get(1)) : true;//Good a NAND b
	}

	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "NAND";
	}
}
