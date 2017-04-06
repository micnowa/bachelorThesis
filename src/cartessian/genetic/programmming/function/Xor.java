package cartessian.genetic.programmming.function;

import java.util.LinkedList;

import cartessian.genetic.programmming.fitness.Functional;

/**
* Class implementing interface Functional<Boolean> with logic XOR
* 
* @author Michał Nowaliński
*
*/
public class Xor implements Functional<Boolean>
{

	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.function.Functional#calculateValue(java.util.LinkedList)
	 */
	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		Boolean a = list.get(0);
		Boolean b = list.get(1);
		return ((a || b) && !(a && b));//Good a XOR b
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "XOR";
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
