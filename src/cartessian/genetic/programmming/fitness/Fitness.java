package cartessian.genetic.programmming.fitness;

import cartessian.genetic.programmming.Grid;

public interface Fitness<T>
{
	/**
	 * Returns fitness of grid passed as argument, if grid fulfills all conditions, function returns maxFitness
	 * 
	 * @param grid	Grid whose fitness is counted
	 * @return	Grid's fitness
	 */
	int getGridFitness(Grid<T> grid);

	/**
	 * Returns max fitness of grid realizing programm's conditions
	 * 
	 * @param grid
	 * @return
	 */
	int getMaxFitness(Grid<T> grid);
}
