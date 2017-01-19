package cartessian.genetic.programmming.fitness;

import cartessian.genetic.programmming.Grid;

public interface Fitness<T>
{
	double getGridFitness(Grid<T> grid);
}
