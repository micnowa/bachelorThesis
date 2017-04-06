package cartessian.genetic.programmming.function;

import java.util.LinkedList;

import cartessian.genetic.programmming.fitness.Functional;

/**
* Class implementing interface Functional<Boolean> with logic NOR
* 
* @author Michał Nowaliński
*
*/
public class Nor implements Functional<Boolean>
{

	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.function.Functional#calculateValue(java.util.LinkedList)
	 */
	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		return !((list.get(0)) || list.get(1));//Good a NOR b
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "NOR";
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
