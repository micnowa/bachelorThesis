package cartessian.genetic.programmming.operation;

import java.util.LinkedList;

public interface Operational<T>
{
	public T calculateValue(LinkedList<T> list);
}
