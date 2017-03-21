package cartessian.genetic.programmming.function;

import java.util.LinkedList;

public interface Functional<T>
{
	/**
	 * @param list
	 * @return
	 */
	public T calculateValue(LinkedList<T> list);
	
	/**
	 * @return
	 */
	public String toString();
	
	/**
	 * @return
	 */
	public int argsNumber();
}