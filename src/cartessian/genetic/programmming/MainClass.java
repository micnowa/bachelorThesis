package cartessian.genetic.programmming;

import cartessian.genetic.programming.sinus.AbsoluteValue;
import cartessian.genetic.programming.sinus.Cosine;
import cartessian.genetic.programming.sinus.Cube;
import cartessian.genetic.programming.sinus.Divide;
import cartessian.genetic.programming.sinus.Minus;
import cartessian.genetic.programming.sinus.Multiple;
import cartessian.genetic.programming.sinus.Plus;
import cartessian.genetic.programming.sinus.Sine;
import cartessian.genetic.programming.sinus.SinusFitter;
import cartessian.genetic.programming.sinus.Square;
import cartessian.genetic.programming.sinus.ToPower;
import cartessian.genetic.programmming.fitness.Functional;
import cartessian.genetic.programmming.function.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

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

	public static void main(String[] args) throws InterruptedException
	{
		System.out.println("Choose logic gate fitter or sinu fitting");
		System.out.println("1-	logic gates");
		System.out.println("2-	sinus");

		int choose;
		choose = scannerRead().nextInt();
		System.out.println("Number is:	" + choose);

		int inputNumber, outputNumber, rows, columns;
		double probability, recurrentProbability;

		System.out.println("Choose number of inputs");
		inputNumber = scannerRead().nextInt();

		System.out.println("Choose number of outputs");
		outputNumber = scannerRead().nextInt();

		System.out.println("Choose number of rows");
		rows = scannerRead().nextInt();

		System.out.println("Choose number of columns");
		columns = scannerRead().nextInt();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose probability");
		probability = scanner.nextDouble();

		System.out.println("Choose recurrent probability");
		recurrentProbability = scanner.nextDouble();

		scanner.close();
		if(choose == 1)
		{

			Random rand = new Random();

			// Available Operations
			LinkedList<Functional<Boolean>> operations = new LinkedList<Functional<Boolean>>();
			operations.add(new And());
			operations.add(new Or());
			operations.add(new Nor());
			operations.add(new Xor());
			operations.add(new Nand());

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

			JFrame frame[] = new JFrame[5];
			@SuppressWarnings("unchecked") GridVisualizer<Boolean> gridVisualizer[] = new GridVisualizer[5];

			gridVisualizer[0] = new GridVisualizer<Boolean>(gridGenerator.getMainGrid());
			for(int ii = 0; ii < 5; ii++)
			{
				if(ii != 0) gridVisualizer[ii] = new GridVisualizer<Boolean>(gridGenerator.getGrid()[ii - 1]);
				frame[ii] = new JFrame();
				frame[ii].add(gridVisualizer[ii]);

				BufferedImage bi = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_ARGB);
				Graphics gg = bi.getGraphics();
				gg.fillRect(0, 0, bi.getWidth(), bi.getHeight());
				gridVisualizer[ii].paint(gg);
				gg.dispose();
				String fileName = "/home/ubuntu/Dokumenty/inż/grids/";
				if(ii == 0)
				{
					fileName += "mainGrid.png";
				}
				else
				{
					fileName += "grid";
					fileName += ii;
					fileName += ".png";
				}
				try
				{
					ImageIO.write(bi, "png", new File(fileName));
				}catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			// ... Measure time ...
			long startTime = System.currentTimeMillis();

			FourPlusOneAlogrithm<Boolean> fourPlusOne = new FourPlusOneAlogrithm<Boolean>(gridGenerator, grid, new LogicGatesFitter());
			Grid<Boolean> finalGrid = fourPlusOne.generateProgramm();
			finalGrid.calculateValueForEveryGate();// Needed for drawing

			// ... Elapsed time is: ...
			long estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("Took: " + estimatedTime / 1000.0 + "s");

			GridVisualizer<Boolean> finalGridVisualizer = new GridVisualizer<Boolean>(finalGrid);
			JFrame finalFrame = new JFrame();
			finalFrame.add(finalGridVisualizer);
			finalFrame.setVisible(true);
			finalFrame.setSize(1600, 900);
		}
		else if(choose == 2)
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

			Grid<Double> grid = new Grid<Double>(functions, inputNumber,outputNumber,rows,columns,0.0,probability,recurrentProbability);
			FourPlusOneAlogrithm<Double> fourPlusOne = new FourPlusOneAlogrithm<Double>(new GridGenerator<Double>(grid), grid, new SinusFitter());

			// ... Measure time ...
			long startTime = System.currentTimeMillis();
			Grid<Double> finalGrid = fourPlusOne.generateProgramm();
			// ... Elapsed time is: ...
			long estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("Took: " + estimatedTime / 1000.0 + "s");

			int num = 100;
			double sinus[] = new double[num];
			double sinusForecast[] = new double[num];

			for(int ii = 0; ii < num; ii++)
			{
				sinus[ii] = Math.sin(2 * Math.PI * ii / 100.0);
				sinusForecast[ii] = 0;
			}

			PrintWriter fileWrite = null;
			try
			{
				fileWrite = new PrintWriter("/home/ubuntu/Dokumenty/inż/sinus/40procbledu/dane.txt");
			}catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int ii = 1; ii < num; ii++)
			{
				Double input[] = new Double[1];
				input[0] = sinusForecast[ii - 1];
				finalGrid.setInputValues(input);
				sinusForecast[ii] = finalGrid.calculateOutputValue(0);
				System.out.println(sinus[ii] + "	" + sinusForecast[ii]);
				fileWrite.println(sinus[ii] + "	" + sinusForecast[ii]);
			}
			
			fileWrite.close();

		}
	}

	private static Scanner scannerRead()
	{
		return new Scanner(System.in);
	}
}