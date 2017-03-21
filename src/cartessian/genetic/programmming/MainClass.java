package cartessian.genetic.programmming;

import cartessian.genetic.programmming.fitness.LogicGatesFitter;
import cartessian.genetic.programmming.function.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

	public static void main(String[] args) throws InterruptedException
	{
		int input = 5;
		int output = 5;
		int rows = 5;
		int columns = 9;
		int enteringGates = 2;
		double probability = 0.2;
		double recurrentProbability = 0;
		Random rand = new Random();

		// Available Operations
		LinkedList<Functional<Boolean>> operations = new LinkedList<Functional<Boolean>>();
		operations.add(new And());
		operations.add(new Or());
		operations.add(new Nor());
		operations.add(new Xor());
		operations.add(new Nand());

		// Grid of operations
		Grid<Boolean> grid = new Grid<Boolean>(operations, input, output, rows, columns, false, enteringGates, probability, recurrentProbability);
		Boolean tab[] = new Boolean[input];
		for(int ii = 0; ii < input; ii++)
		{
			tab[ii] = rand.nextBoolean();
		}
		grid.setInputValues(tab);
		grid.calculateValueForEveryGate();

		// Set of grids
		GridGenerator<Boolean> gridGenerator = new GridGenerator<Boolean>(grid, probability, recurrentProbability);
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
			gridVisualizer[ii].setActiveOutput(0);
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

		LogicGatesFitter fitness = new LogicGatesFitter();

		FourPlusOneAlogrithm<Boolean> fourPlusOne = new FourPlusOneAlogrithm<Boolean>(gridGenerator, grid, fitness);
		Grid<Boolean> finalGrid = fourPlusOne.generateProgramm();
		finalGrid.calculateValueForEveryGate();// Needed for drawing

		// ... Elapsed time is: ...
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Took: " + estimatedTime / 1000.0 + "s");

		GridVisualizer<Boolean> finalGridVisualizer = new GridVisualizer<Boolean>(finalGrid);
		JFrame finalFrame = new JFrame();
		finalGridVisualizer.setActiveOutput(0);
		finalFrame.add(finalGridVisualizer);
		finalFrame.setVisible(true);
		finalFrame.setSize(1600, 900);

	}
}