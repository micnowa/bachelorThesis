package cartessian.genetic.programmming.operation;

import java.util.ArrayList;

public class Or implements Operational<Boolean>
{

	@Override public Boolean calculateValue(ArrayList<Boolean> list)
	{
		return (list.get(0) || list.get(1));
	}

	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "OR";
	}

}
