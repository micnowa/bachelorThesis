package cartessian.genetic.programmming;

import cartessian.genetic.programmming.operation.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;

import cartessian.genetic.programmming.operation.Operational;

/**
 * @author Michał Nowaliński
 * 
 * 	Class showing program's work. It creates 5 grids with input values of user's type, and operations user implemented.
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

	public static boolean getRandomBoolean()
	{
		return Math.random() < 0.5;
		// I tried another approaches here, still the same result
	}

	public static void main(String[] args) throws InterruptedException
	{
		int input = 4;
		int output = 5;
		int rows = 5;
		int columns = 9;
		int enteringGates = 4;
		double probability = 0.8;

		int sleepTime = 1;

		// Available Operations
		LinkedList<Operational<Boolean>> operations = new LinkedList<Operational<Boolean>>();
		operations.add(new And());
		operations.add(new Or());
		operations.add(new Nor());
		operations.add(new Xor());
		operations.add(new Nand());

		// Grid of operations
		Grid<Operational<Boolean>, Boolean> grid = new Grid<Operational<Boolean>, Boolean>(operations, input, output, rows, columns, enteringGates);
		grid.printGrid();
		Boolean tab[] = new Boolean[input];
		for(int ii = 0; ii < input; ii++)
		{
			tab[ii] = getRandomBoolean();
		}
		grid.setInputValues(tab);
		grid.calculateValueForEveryGate();

		// Set of grids
		GridGenerator<Operational<Boolean>, Boolean> g = new GridGenerator<Operational<Boolean>, Boolean>(grid, probability);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual = new GridVisualizer<>(g.getMainGrid());
		JFrame frame = new JFrame();
		frame.setTitle("Main Grid");
		frame.add(gridVisual);
		frame.setSize(1600, 900);
		frame.setVisible(true);
		TimeUnit.SECONDS.sleep(sleepTime);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual0 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[0]);
		JFrame frame0 = new JFrame();
		frame0.setTitle("Grid0");
		frame0.add(gridVisual0);
		frame0.setSize(1600, 900);
		frame0.setVisible(true);
		TimeUnit.SECONDS.sleep(sleepTime);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual1 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[1]);
		JFrame frame1 = new JFrame();
		frame1.setTitle("Grid1");
		frame1.add(gridVisual1);
		frame1.setSize(1600, 900);
		frame1.setVisible(true);
		System.out.println(frame1.toString());
		TimeUnit.SECONDS.sleep(sleepTime);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual2 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[2]);
		JFrame frame2 = new JFrame();
		frame2.setTitle("Grid2");
		frame2.add(gridVisual2);
		frame2.setSize(1600, 900);
		frame2.setVisible(true);
		TimeUnit.SECONDS.sleep(sleepTime);

		GridVisualizer<Operational<Boolean>, Boolean> gridVisual3 = new GridVisualizer<Operational<Boolean>, Boolean>(g.getGrid()[3]);
		JFrame frame3 = new JFrame();
		frame3.setTitle("Grid3");
		frame3.add(gridVisual3);
		frame3.setSize(1600, 900);
		frame3.setVisible(true);
		TimeUnit.SECONDS.sleep(sleepTime);
	}
}