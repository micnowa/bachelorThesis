package cartessian.genetic.programming.sinus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cartessian.genetic.programming.Grid;
import cartessian.genetic.programming.fitness.Fitness;

/**
 * Class implementing generic interface Fitness with type Double. Fitness of
 * grid is calculated basing of sum of errors' absolute value of predicted and
 * original. User sets permissible deviation. If it fits his demanding max
 * fitness is returned. Max fitness is equal to zero. Public int
 * getGridFitness(Grid<Double> grid) returns negative value due to situation of
 * comparing grids fitnesse. The bigger the better.
 * 
 * @author Michał Nowaliński
 * 
 */
public class EcgFitter implements Fitness<Double>
{
	int num = 100;
	double ecg[] = ecgData(100, 1);
	
	@Override public int getGridFitness(Grid<Double> grid)
	{
		// TODO Auto-generated method stub
		double ecgForecast[] = new double[num];
		for(int ii = 1; ii < num; ii++)
			ecgForecast[ii] = 0;

		Double input[] = new Double[1];
		for(int ii = 1; ii < num; ii++)
		{
			input[0] = ecgForecast[ii - 1];
			grid.setInputValues(input);
			ecgForecast[ii] = grid.calculateOutputValue(0);
		}

		double score = 0;

		for(int kk = 0; kk < num; kk++)
		{
			score -= Math.abs(ecg[kk] - ecgForecast[kk]);
		}
		grid.clearGatesValues();

		if(score >= -25) return getMaxFitness(grid);
		return (int) score;
	}

	@Override public int getMaxFitness(Grid<Double> grid)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public static double[] ecgData(int num, int choise)
	{
		double tab[] = new double[num];

		File file = new File("/home/ubuntu/Dokumenty/inż/ekg/ekg.txt");
		Scanner scanner = null;
		try
		{
			scanner = new Scanner(file);
		}catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			System.out.println("Ojoj");
			e.printStackTrace();
		}
		String line;
		if(choise == 1)
		{
			for(int ii = 0; ii < num; ii++)
			{
				line = scanner.nextLine();
				tab[ii] = Double.parseDouble(line.substring(line.indexOf("	") + 1, line.indexOf("	", 7)));
			}
		}
		else if(choise == 2)
		{
			for(int ii = 0; ii < num; ii++)
			{
				line = scanner.nextLine();
				tab[ii] = Double.parseDouble(line.substring(line.indexOf("	", 7)));
			}
		}
		scanner.close();
		return tab;
	}
}
/*
 * @Override public int getGridFitness(Grid<Double> grid) { int inputNumber =
 * grid.getInputNumber(); // TODO Auto-generated method stub int num = 100;
 * double sinus[] = sinus(); double sinusForecast[] = new double[num]; for(int
 * ii = 0; ii < num; ii++) sinusForecast[ii] = 0;
 * 
 * Double input[] = new Double[inputNumber]; for(int ii = 0; ii < input.length;
 * ii++) { input[ii] = sinus[ii]; sinusForecast[ii] = sinus[ii]; }
 * grid.setInputValues(input);
 * 
 * for(int ii = input.length; ii < num; ii++) { sinusForecast[ii] =
 * grid.calculateOutputValue(0); for(int jj = 0; jj < input.length; jj++)
 * input[jj] = input[jj z]; input[inputNumber - 1] = sinusForecast[ii];
 * grid.setInputValues(input); }
 * 
 * double score = 0;
 * 
 * for(int kk = 0; kk < num; kk++) { score -= Math.abs(sinus[kk] -
 * sinusForecast[kk]); } grid.clearGatesValues();
 * 
 * if(score > -28) return getMaxFitness(grid); return (int) score; }
 */