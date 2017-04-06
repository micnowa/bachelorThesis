package cartessian.genetic.programmming.fitness;

import java.util.LinkedList;

/**
 * Interface class that represent gate functions must implement to calculate
 * value based on values of gates entering
 * 
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            type of value functions base on
 */
public interface Functional<T>
{
	/**
	 * @param list
	 *            list of arguments
	 * @return value
	 */
	public T calculateValue(LinkedList<T> list);

	/**
	 * Use shall implement this to function for stylish description of function
	 * in drawing it
	 * 
	 * @return name of function set by user
	 */
	public String toString();

	/**
	 * Used in determining what number of entering gates shall be.Number is
	 * equal to maximum of argsNumber() of function grid has
	 * 
	 * @return number of argument function has
	 */
	public int argsNumber();
}