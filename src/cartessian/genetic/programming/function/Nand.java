package cartessian.genetic.programming.function;

import java.util.LinkedList;

import cartessian.genetic.programming.fitness.Functional;

/**
 * Class implementing interface Functional<Boolean> with logic NAND
 * 
 * @author Michał Nowaliński
 *
 */
public class Nand implements Functional<Boolean>
{

	/* (non-Javadoc)
	 * @see cartessian.genetic.programmming.function.Functional#calculateValue(java.util.LinkedList)
	 */
	@Override public Boolean calculateValue(LinkedList<Boolean> list)
	{
		return (list.get(0)) ? !(list.get(1)) : true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString()
	{
		// TODO Auto-generated method stub
		return "NAND";
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
