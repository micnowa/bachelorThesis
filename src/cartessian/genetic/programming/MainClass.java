package cartessian.genetic.programming;

import cartessian.genetic.programming.fitness.*;
import cartessian.genetic.programming.function.*;
import cartessian.genetic.programming.sinus.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Michał Nowaliński
 * 
 *         Class showing program's work. It creates 5 grids with input values of
 *         user's type, and operations user implemented.
 * 
 */
public class MainClass extends Canvas
{
	private static final long serialVersionUID = 1L;

	public MainClass()
	{
		super();
	}

	public static BufferedImage getScreenShot(Component component)
	{
		BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
		component.paint(image.getGraphics());
		return image;

	}

	public static void saveScreenShot(Component component, String fileName) throws Exception
	{
		BufferedImage img = getScreenShot(component);
		ImageIO.write(img, "png", new File("/home/ubuntu/Obrazy/" + fileName));
	}

	public static void main(String[] args) throws InterruptedException, IOException
	{
		System.out.println("Choose logic gate fitter or sinu fitting");
		System.out.println("1-	logic gates");
		System.out.println("2-	sinus");

		int choose;
		choose = 4;
		System.out.println("Number is:	" + choose);

		int inputNumber = 1, outputNumber = 1, rows = 1, columns = 100;
		double probability = 0.1, recurrentProbability = 0.5;
		String fileName = "/home/ubuntu/Dokumenty/inż/logicGates/5x9/";

		if(choose == 1)
		{
			FileWriter plotFile = null;
			plotFile = new FileWriter("/home/ubuntu/Dokumenty/inż/logicGates/5x9/timesProbability2.txt", true);
			BufferedWriter outPlot = new BufferedWriter(plotFile);
			outPlot.write("Probability	Steps\n");

			for(int mm = 0; mm < 60; mm++)
			{
				System.out.println("Times runned:	" + mm);
				for(int kk = 5; kk < 100; kk += 5)
				{
					
					probability = (double) kk / 100;
					System.out.println("Probability:	" + probability);
					Random rand = new Random();

					// Available Operations
					LinkedList<Functional<Boolean>> operations = logicFunctions();

					// Grid of operations
					Grid<Boolean> grid = new Grid<Boolean>(operations, inputNumber, outputNumber, rows, columns, false, probability, recurrentProbability);
					Boolean tab[] = new Boolean[inputNumber];
					for(int ii = 0; ii < inputNumber; ii++)
					{
						tab[ii] = rand.nextBoolean();
					}
					grid.setInputValues(tab);
					grid.calculateValueForEveryGate();

					// Set of grids
					GridGenerator<Boolean> gridGenerator = new GridGenerator<Boolean>(grid);
					gridGenerator.getMainGrid().calculateValueForEveryGate();
					for(int ii = 0; ii < GridGenerator.getGridNumber(); ii++)
					{
						gridGenerator.getGrid()[ii].calculateValueForEveryGate();
					}

					long startTime = System.currentTimeMillis();

					FourPlusOneAlogrithm<Boolean> fourPlusOne = new FourPlusOneAlogrithm<Boolean>(gridGenerator, grid, new LogicGatesFitter());
					fourPlusOne.setFileName("/home/ubuntu/Dokumenty/inż/logicGates/5x9/" + kk + "/algorytm.txt");
					Grid<Boolean> finalGrid = fourPlusOne.generateProgramm();
					
					outPlot.write(probability + "	" + fourPlusOne.getTimesRunned());
					outPlot.write("\n");
					
					finalGrid.calculateValueForEveryGate();// Needed for drawing

					// ... Elapsed time is: ...
					long estimatedTime = System.currentTimeMillis() - startTime;
//					System.out.println("Took: " + estimatedTime / 1000.0 + "s");

					/*GridVisualizer<Boolean> finalGridVisualizer = new GridVisualizer<Boolean>(finalGrid);
					JFrame finalFrame = new JFrame();
					finalFrame.add(finalGridVisualizer);
					// finalFrame.setVisible(true);
					finalFrame.setSize(1600, 900);

					BufferedImage bi = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_ARGB);
					Graphics gg = bi.getGraphics();
					gg.fillRect(0, 0, bi.getWidth(), bi.getHeight());
					finalGridVisualizer.paint(gg);
					gg.dispose();
					fileName = "/home/ubuntu/Dokumenty/inż/logicGates/5x9/";
					Integer prob = (int) (probability * 100);
					fileName += prob.toString();
					fileName += "/grid.png";
					try
					{
						ImageIO.write(bi, "png", new File(fileName));
					}catch (IOException e)
					{
						e.printStackTrace();
					}*/
				}
				System.out.println();
			}
			outPlot.close();

		}
		else if(choose == 2)
		{
			FileWriter plotFile = null;
			plotFile = new FileWriter("/home/ubuntu/Dokumenty/inż/sinus/timesProbability.txt", true);
			BufferedWriter outPlot = new BufferedWriter(plotFile);
			outPlot.write("recurrentProbability	Steps\n");

			String filePath = "/home/ubuntu/Dokumenty/inż/sinus/";
			int columnsTab[] = { 10, 20, 50, 100, 200, 500, 1000, 2000, 5000 };
			double recProbTab[] = { 0.5, 0.1, 0.15, 0.20, 0.25, 0.30, 0.35, 0.40, 0.45, 0.50 };

			for(int ii = 8; ii < columnsTab.length; ii++)
			{
				for(int jj = 0; jj < recProbTab.length; jj++)
				{
					columns = columnsTab[ii];
					recurrentProbability = recProbTab[jj];
					LinkedList<Functional<Double>> functions = sinusFunctions();

					Grid<Double> grid = new Grid<Double>(functions, inputNumber, outputNumber, rows, columns, 0.0, probability, recurrentProbability);

					FourPlusOneAlogrithm<Double> fourPlusOne = new FourPlusOneAlogrithm<Double>(new GridGenerator<Double>(grid), grid, new SinusFitter());
					fourPlusOne.setFileName(filePath + columns + "/" + (int) (recurrentProbability * 100) + "/algorytm.txt");

					// ... Measure time ...
					long startTime = System.currentTimeMillis();
					Grid<Double> finalGrid = fourPlusOne.generateProgramm();
					try
					{
						outPlot.write(recurrentProbability + "	" + (-1) * fourPlusOne.fitnessNumber);
					}catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					outPlot.write("\n");
					// ... Elapsed time is: ...
					long estimatedTime = System.currentTimeMillis() - startTime;
					System.out.println("Took: " + estimatedTime / 1000.0 + "s");

					int num = 200;
					double sinus[] = new double[num];
					double sinusForecast[] = new double[num];

					for(int kk = 0; kk < num; kk++)
					{
						sinus[kk] = Math.sin(2 * Math.PI * kk / 100.0);
						sinusForecast[kk] = 0;
					}

					PrintWriter fileWrite = null;
					try
					{
						fileWrite = new PrintWriter(filePath + columns + "/" + (int) (recurrentProbability * 100) + "/sinus.txt");
					}catch (FileNotFoundException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int kk = 1; kk < num; kk++)
					{
						Double input[] = new Double[1];
						input[0] = sinusForecast[kk - 1];
						finalGrid.setInputValues(input);
						sinusForecast[kk] = finalGrid.calculateOutputValue(0);
						System.out.println(sinus[kk] + "	" + sinusForecast[kk]);
						fileWrite.println(sinus[kk] + "	" + sinusForecast[kk]);
					}
					fileWrite.close();
				}

			}
			outPlot.close();
		}

		else if(choose == 3)
		{
			LinkedList<Functional<Double>> functions = sinusFunctions();

			Grid<Double> grid = new Grid<Double>(functions, 1, 1, 1, 100, 0.0, 0.05, 0.4);

			FourPlusOneAlogrithm<Double> fourPlusOne = new FourPlusOneAlogrithm<Double>(new GridGenerator<Double>(grid), grid, new SinusFitter());

			// ... Measure time ...
			long startTime = System.currentTimeMillis();
			Grid<Double> finalGrid = fourPlusOne.generateProgramm();
			// ... Elapsed time is: ...
			long estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("Took: " + estimatedTime / 1000.0 + "s");

			int num = 400;
			double sinus[] = new double[num];
			double sinusForecast[] = new double[num];

			for(int kk = 0; kk < num; kk++)
			{
				sinus[kk] = Math.sin(2 * Math.PI * kk / 100.0);
				sinusForecast[kk] = 0;
			}

			for(int kk = 1; kk < num; kk++)
			{
				Double input[] = new Double[1];
				input[0] = sinusForecast[kk - 1];
				finalGrid.setInputValues(input);
				sinusForecast[kk] = finalGrid.calculateOutputValue(0);
				System.out.println(sinus[kk] + "	" + sinusForecast[kk]);
			}
		}
		else if(choose == 4)
		{
			FileWriter plotFile = null;
			plotFile = new FileWriter("/home/ubuntu/Dokumenty/inż/sinus/timesProbability.txt", true);
			BufferedWriter outPlot = new BufferedWriter(plotFile);
			outPlot.write("recurrentProbability	Steps\n");

			String filePath = "/home/ubuntu/Dokumenty/inż/sinus/dataNumber/";

			for(int ii = 1; ii < 2; ii++)
			{
				inputNumber = ii;
				LinkedList<Functional<Double>> functions = sinusFunctions();

				Grid<Double> grid = new Grid<Double>(functions, inputNumber, outputNumber, rows, columns, 0.0, probability, recurrentProbability);

				FourPlusOneAlogrithm<Double> fourPlusOne = new FourPlusOneAlogrithm<Double>(new GridGenerator<Double>(grid), grid, new SinusFitter());
				fourPlusOne.setFileName(filePath + ii + "/algorytm.txt");

				// ... Measure time ...
				long startTime = System.currentTimeMillis();
				Grid<Double> finalGrid = fourPlusOne.generateProgramm();
				try
				{
					outPlot.write(recurrentProbability + "	" + (-1) * fourPlusOne.fitnessNumber);
				}catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				outPlot.write("\n");
				// ... Elapsed time is: ...
				long estimatedTime = System.currentTimeMillis() - startTime;
				System.out.println("Took: " + estimatedTime / 1000.0 + "s");

				int num = 200;
				double sinus[] = new double[num];
				double sinusForecast[] = new double[num];

				for(int kk = 0; kk < num; kk++)
				{
					sinus[kk] = Math.sin(2 * Math.PI * kk / 100.0);
					sinusForecast[kk] = 0;
				}

				PrintWriter fileWrite = null;
				try
				{
					fileWrite = new PrintWriter(filePath + ii + "/sinus.txt");
				}catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int kk = 1; kk < num; kk++)
				{
					Double input[] = new Double[1];
					input[0] = sinusForecast[kk - 1];
					finalGrid.setInputValues(input);
					sinusForecast[kk] = finalGrid.calculateOutputValue(0);
					System.out.println(sinus[kk] + "	" + sinusForecast[kk]);
					fileWrite.println(sinus[kk] + "	" + sinusForecast[kk]);
				}
				fileWrite.close();
			}
			outPlot.close();
		}

	}

	private static LinkedList<Functional<Boolean>> logicFunctions()
	{
		LinkedList<Functional<Boolean>> operations = new LinkedList<Functional<Boolean>>();
		operations.add(new And());
		operations.add(new Or());
		operations.add(new Nor());
		operations.add(new Xor());
		operations.add(new Nand());

		return operations;
	}

	private static LinkedList<Functional<Double>> sinusFunctions()
	{
		LinkedList<Functional<Double>> functions = new LinkedList<Functional<Double>>();
		functions.add(new Divide());
		functions.add(new Minus());
		functions.add(new Multiple());
		functions.add(new Plus());
		functions.add(new ToPower());
		functions.add(new AbsoluteValue());
		functions.add(new Square());
		functions.add(new Cube());
		functions.add(new Sine());
		functions.add(new Cosine());

		return functions;
	}
}

/*
 * private static Scanner scannerRead() { return new Scanner(System.in); }
 * 
 * 
 * 
 * JFrame frame[] = new JFrame[5];
 * 
 * @SuppressWarnings("unchecked") GridVisualizer<Boolean> gridVisualizer[] = new
 * GridVisualizer[5];
 * 
 * gridVisualizer[0] = new GridVisualizer<Boolean>(gridGenerator.getMainGrid());
 * for(int ii = 0; ii < 5; ii++) { if(ii != 0) gridVisualizer[ii] = new
 * GridVisualizer<Boolean>(gridGenerator.getGrid()[ii - 1]); frame[ii] = new
 * JFrame(); frame[ii].add(gridVisualizer[ii]);
 * 
 * BufferedImage bi = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_ARGB);
 * Graphics gg = bi.getGraphics(); gg.fillRect(0, 0, bi.getWidth(),
 * bi.getHeight()); gridVisualizer[ii].paint(gg); gg.dispose(); String fileName
 * = "/home/ubuntu/Dokumenty/inż/grids/"; if(ii == 0) { fileName +=
 * "mainGrid.png"; } else { fileName += "grid"; fileName += ii; fileName +=
 * ".png"; } try { ImageIO.write(bi, "png", new File(fileName)); }catch
 * (IOException e) { e.printStackTrace(); } } // ... Measure time ...
 */