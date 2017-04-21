package cartessian.genetic.programming.fitness;

import cartessian.genetic.programming.Grid;

/**
 * Interface used in FourPlusOne algorithm. It is used in counting grid's
 * fitness.
 * 
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            Type of grid's gates values are count
 */
public interface Fitness<T>
{
	/**
	 * Returns fitness of grid passed as argument, if grid fulfills all
	 * conditions, function returns maxFitness
	 * 
	 * @param grid
	 *            Grid whose fitness is counted
	 * @return Grid's fitness
	 */
	int getGridFitness(Grid<T> grid);

	/**
	 * Returns max fitness of grid realizing programm's conditions
	 * 
	 * @param grid	gird whose fitness will be determined
	 * @return	max fitness gird can have
	 */
	int getMaxFitness(Grid<T> grid);
}