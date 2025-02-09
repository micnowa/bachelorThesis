package cartessian.genetic.programming;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cartessian.genetic.programming.fitness.Fitness;

/**
 * Class responsible for running four plus one algorithm. Use provides Gird<T>,
 * Fitness<T> and GridGenerator<T>. It can be done by constructor
 * FourPlusOneAlogrithm(GridGenerator<T> gridGenerator, Grid<T> grid, Fitness<T>
 * fitness) or be implemented by setGird(Grid<T> grid) and setFitness(Fitness<T>
 * fitness). Main goal of this class is to generate grid realizing user's needs.
 * In this case user has to call method Grid<T> generateProgramm(). It runs four
 * plus one algorithm basing on fitness and grid user implemented. Return value
 * is grid<T>, that can be later used by user for his own needs.
 * 
 * Four plus One algorithm is an algorithm generating final grid. In first step
 * four new grids are created basing on main grid. Fitness of each one is
 * counted. Grid with the biggest score becomes main grid. Another 4 are
 * destroyed. If main grid is one with best fitness it stays as the main one.
 * Algorithm is executed until it is found grid with fitness equal to
 * maxFitness.
 * 
 * @Warning Fitness must be higher or equal to 0 and less or equal to
 *          maxFitness.
 * 
 * @author Michał Nowaliński
 * 
 * @param <T>
 *            Type of variables user want to use
 */
public class FourPlusOneAlogrithm<T>
{
	/**
	 * GridGenerator used in algorithm to generate 4 new grids based on main
	 * Grid
	 */
	private GridGenerator<T> gridGenerator;

	/**
	 * Grid, new Grids are based on
	 */
	private Grid<T> grid;

	/**
	 * Interface implemented by user. It is used in every step for each grid. It
	 * returns its fitness
	 */
	private Fitness<T> fitness;

	/**
	 * Grid generated as a result of run algorithm. Its fitness is equal
	 * maxFitness, that mean such grid realists all user's needs
	 */
	private Grid<T> programm;

	/**
	 * Constructor with 3 parameters class requires to properly run 4plus1
	 * algorithm
	 * 
	 * @param gridGenerator
	 *            GridGenerator used in every step to generate new grids
	 * @param grid
	 *            Grid all grids in 4plus1 are based on
	 * @param fitness
	 *            Interface counting grid's fitness
	 */

	private String fileName;

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	private int timesRunned;

	public int getTimesRunned()
	{
		return timesRunned;
	}

	public void setTimesRunned(int timesRunned)
	{
		this.timesRunned = timesRunned;
	}

	public FourPlusOneAlogrithm(GridGenerator<T> gridGenerator, Grid<T> grid, Fitness<T> fitness)
	{
		this.gridGenerator = gridGenerator;
		this.grid = grid;
		this.fitness = fitness;
	}

	public int fitnessNumber;

	/**
	 * Returns grid generator
	 * 
	 * @return GridGenerator GridGenerator
	 */
	public GridGenerator<T> getGridGenerator()
	{
		return gridGenerator;
	}

	/**
	 * Sets grid generator
	 * 
	 * @param gridGenerator
	 *            gridGenerator
	 */
	public void setGridGenerator(GridGenerator<T> gridGenerator)
	{
		this.gridGenerator = gridGenerator;
	}

	/**
	 * Returns grid
	 * 
	 * @return Grid Grid
	 */
	public Grid<T> getGrid()
	{
		return grid;
	}

	/**
	 * Grid setter
	 * 
	 * @param grid
	 *            Grid
	 */
	public void setGrid(Grid<T> grid)
	{
		this.grid = grid;
	}

	/**
	 * Fitness getter
	 * 
	 * @return Fitness fitness function
	 */
	public Fitness<T> getFitness()
	{
		return fitness;
	}

	/**
	 * Sets fitness
	 * 
	 * @param fitness
	 *            fitness function
	 */
	public void setFitness(Fitness<T> fitness)
	{
		this.fitness = fitness;
	}

	/**
	 * Program getter
	 * 
	 * @return Grid evolved by method run()
	 */
	public Grid<T> getProgramm()
	{
		return programm;
	}

	/**
	 * Method executing four plus one algorithm
	 */
	private void run()
	{

		Boolean solutionFound = false;
		int score[] = new int[GridGenerator.getGridNumber() + 1];
		int bestGridNumber = 0;
		timesRunned = 1;
		score[0] = fitness.getGridFitness(gridGenerator.getMainGrid());

		PrintWriter fileWrite = null;
		try
		{
			fileWrite = new PrintWriter(fileName);
		}catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(!solutionFound)
		{
			score[0] = score[bestGridNumber];
			bestGridNumber = 0;
			System.out.println("==================================");
			System.out.println("Times runned:	" + timesRunned);
			System.out.print(score[0] + "	");
			for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
			{
				score[ii + 1] = fitness.getGridFitness(gridGenerator.getGrid()[ii]);
				System.out.print(score[ii + 1] + "	");
			}

			for(int ii = 1; ii < GridGenerator.getGridNumber(); ii++)
			{
				if(score[ii] > score[bestGridNumber])
				{
					bestGridNumber = ii;
				}
			}
			System.out.println(",best score:	" + score[bestGridNumber]);

			if(score[bestGridNumber] == fitness.getMaxFitness(gridGenerator.getMainGrid()))
			{
				solutionFound = true;
				if(bestGridNumber == 0) programm = gridGenerator.getMainGrid();
				else
					programm = gridGenerator.getGrid()[bestGridNumber - 1];
			}
			else
			{
				if(bestGridNumber == 0)
				{
					System.out.println("GG stays, as it is");
					grid = gridGenerator.getMainGrid();
					System.out.println("New GG with same MainGrid");
					gridGenerator.generateNewGrids();
				}
				else
				{
					grid = gridGenerator.getGrid()[bestGridNumber - 1];
					gridGenerator = new GridGenerator<T>(grid);
					System.out.println("New GG with grid number: " + bestGridNumber);
				}
			}
			System.out.println("New GG created!");
			timesRunned++;

			if(timesRunned > 5000)
			{
				solutionFound = true;
				fitnessNumber = score[bestGridNumber];
				if(bestGridNumber == 0) programm = gridGenerator.getMainGrid();
				else
					programm = gridGenerator.getGrid()[bestGridNumber - 1];
			}
			fileWrite.println(timesRunned + "	" + score[bestGridNumber]);

			System.out.println();
		}
		fileWrite.close();
	}

	/**
	 * Function used by user to generate grid realizing all needs
	 * 
	 * @return Final Grid that has maximum fitness
	 */
	public Grid<T> generateProgramm()
	{
		run();
		return programm;
	}
}

//
// package cartessian.genetic.programming;
//
// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
//
// import cartessian.genetic.programming.fitness.Fitness;
//
// /**
// * Class responsible for running four plus one algorithm. Use provides
// Gird<T>,
// * Fitness<T> and GridGenerator<T>. It can be done by constructor
// * FourPlusOneAlogrithm(GridGenerator<T> gridGenerator, Grid<T> grid,
// Fitness<T>
// * fitness) or be implemented by setGird(Grid<T> grid) and
// setFitness(Fitness<T>
// * fitness). Main goal of this class is to generate grid realizing user's
// needs.
// * In this case user has to call method Grid<T> generateProgramm(). It runs
// four
// * plus one algorithm basing on fitness and grid user implemented. Return
// value
// * is grid<T>, that can be later used by user for his own needs.
// *
// * Four plus One algorithm is an algorithm generating final grid. In first
// step
// * four new grids are created basing on main grid. Fitness of each one is
// * counted. Grid with the biggest score becomes main grid. Another 4 are
// * destroyed. If main grid is one with best fitness it stays as the main one.
// * Algorithm is executed until it is found grid with fitness equal to
// * maxFitness.
// *
// * @Warning Fitness must be higher or equal to 0 and less or equal to
// * maxFitness.
// *
// * @author Michał Nowaliński
// *
// * @param <T>
// * Type of variables user want to use
// */
// public class FourPlusOneAlogrithm<T>
// {
// /**
// * GridGenerator used in algorithm to generate 4 new grids based on main
// * Grid
// */
// private GridGenerator<T> gridGenerator;
//
// /**
// * Grid, new Grids are based on
// */
// private Grid<T> grid;
//
// /**
// * Interface implemented by user. It is used in every step for each grid. It
// * returns its fitness
// */
// private Fitness<T> fitness;
//
// /**
// * Grid generated as a result of run algorithm. Its fitness is equal
// * maxFitness, that mean such grid realists all user's needs
// */
// private Grid<T> programm;
//
// /**
// *
// */
// int score[];
//
// /**
// * Number of grid with biggest fitness
// */
// int bestGridNumber = 0;
//
// /**
// * How many steps it took to generate program
// */
// int timesRunned = 1;
//
// /**
// * Default constructor
// */
// public FourPlusOneAlogrithm()
// {
// }
//
// /**
// * Constructor with 3 parameters class requires to properly run 4plus1
// * algorithm
// *
// * @param gridGenerator
// * GridGenerator used in every step to generate new grids
// * @param grid
// * Grid all grids in 4plus1 are based on
// * @param fitness
// * Interface counting grid's fitness
// */
// public FourPlusOneAlogrithm(GridGenerator<T> gridGenerator, Grid<T> grid,
// Fitness<T> fitness)
// {
// this.gridGenerator = gridGenerator;
// this.grid = grid;
// this.fitness = fitness;
// this.score = new int[GridGenerator.getGridNumber() + 1];
// this.bestGridNumber = 0;
// this.timesRunned = 1;
// }
//
// /**
// * Returns grid generator
// *
// * @return GridGenerator GridGenerator
// */
// public GridGenerator<T> getGridGenerator()
// {
// return gridGenerator;
// }
//
// /**
// * Sets grid generator
// *
// * @param gridGenerator
// * gridGenerator
// */
// public void setGridGenerator(GridGenerator<T> gridGenerator)
// {
// this.gridGenerator = gridGenerator;
// }
//
// /**
// * Returns grid
// *
// * @return Grid Grid
// */
// public Grid<T> getGrid()
// {
// return grid;
// }
//
// /**
// * Grid setter
// *
// * @param grid
// * Grid
// */
// public void setGrid(Grid<T> grid)
// {
// this.grid = grid;
// }
//
// /**
// * Fitness getter
// *
// * @return Fitness fitness function
// */
// public Fitness<T> getFitness()
// {
// return fitness;
// }
//
// /**
// * Sets fitness
// *
// * @param fitness
// * fitness function
// */
// public void setFitness(Fitness<T> fitness)
// {
// this.fitness = fitness;
// }
//
// /**
// * Program getter
// *
// * @return Grid evolved by method run()
// */
// public Grid<T> getProgramm()
// {
// return programm;
// }
//
// /**
// * Method executing four plus one algorithm
// */
// @SuppressWarnings("unchecked") public Grid<T> generateProgramm()
// {
//
// Boolean solutionFound = false;
// score[0] = fitness.getGridFitness(gridGenerator.getMainGrid());
// bestGridNumber = 0;
//
// PrintWriter fileWrite = null;
// try
// {
// fileWrite = new
// PrintWriter("/home/ubuntu/Dokumenty/inż/sinus/20procbledu/algorytm.txt");
// }catch (FileNotFoundException e)
// {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// while(!solutionFound)
// {
// score[0] = score[bestGridNumber];
// bestGridNumber = 0;
// System.out.println("==================================");
// System.out.println("Times runned:	" + timesRunned);
// System.out.print("0) " + score[0] + "	");
//
// MultiThreadFitness<T> multiThread[] = new
// MultiThreadFitness[GridGenerator.getGridNumber()];
// Thread thread[] = new Thread[GridGenerator.getGridNumber()];
//
// for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
// {
// multiThread[ii] = new MultiThreadFitness<T>(gridGenerator, fitness);
// thread[ii] = new Thread(multiThread[ii]);
// }
//
// for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
// {
// multiThread[ii].setGridGenerator(gridGenerator);
// }
//
// for(int ii = 0; ii < thread.length; ii++)
// try
// {
// thread[ii].start();
// thread[ii].join();
// }catch (InterruptedException e)
// {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// int tmp = score[0];
// score = MultiThreadFitness.getScore();
// score[0] = tmp;
//
// for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
// {
// System.out.print(ii + ") " + score[ii + 1] + "	");
// }
//
// for(int ii = 1; ii < GridGenerator.getGridNumber(); ii++)
// {
// if(score[ii] > score[bestGridNumber])
// {
// bestGridNumber = ii;
// }
// }
// System.out.println(",best score:	" + score[bestGridNumber]);
//
// if(score[bestGridNumber] ==
// fitness.getMaxFitness(gridGenerator.getMainGrid()))
// {
// solutionFound = true;
// if(bestGridNumber == 0) programm = gridGenerator.getMainGrid();
// else
// programm = gridGenerator.getGrid()[bestGridNumber - 1];
// }
// else
// {
// if(bestGridNumber == 0)
// {
// System.out.println("GG stays, as it is");
// grid = gridGenerator.getMainGrid();
// System.out.println("New GG with same MainGrid");
// gridGenerator.generateNewGrids();
// }
// else
// {
// grid = gridGenerator.getGrid()[bestGridNumber - 1];
// gridGenerator = new GridGenerator<T>(grid);
// System.out.println("New GG with grid number: " + bestGridNumber);
// }
// }
// System.out.println("New GG created!");
// timesRunned++;
//
// // fileWrite.println(timesRunned + "	" + score[bestGridNumber]);
//
// System.out.println();
// }
// // fileWrite.close();
//
// return programm;
// }
//
// }
