package cartessian.genetic.programmming.function;

import java.util.LinkedList;

/**
 * Class implementing interface Functional<Boolean> with logic AND
 * 
 * @author Michał Nowaliński
 *
 */
public class And implements Functional<Boolean>
{
	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.function.Functional#calculateValue(java.util.LinkedList)
	 */
	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		// TODO Auto-generated method stub
		return (list.get(0) && list.get(1));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "AND";
	}

	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.function.Functional#argsNumber()
	 */
	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}
}
