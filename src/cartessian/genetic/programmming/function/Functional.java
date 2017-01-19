package cartessian.genetic.programmming.function;

import java.util.LinkedList;

public interface Functional<T>
{
	public T calculateValue(LinkedList<T> list);
	public String toString();
	public int argsNumber();
}
