package cartessian.genetic.programmming;

import java.nio.ByteBuffer;

public class Fitness<T, U>
{
	private Grid<T, U> grid;
	private Gate<T, U> input[];
	private int valuesNumber;
	private U values[];
	double fitness;
	
	public Fitness(Grid<T, U> grid)
	{
		this.grid = new Grid<>(grid);
		this.input = new Gate[grid.getInputNumber()];
		for(Gate<T, U> gate : input)
		{
			gate = new Gate<T, U>();
		}
	}
	
	public double getFitness()
	{
		return fitness;
	}

	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}

	public int getValuesNumber()
	{
		return valuesNumber;
	}

	public void setValuesNumber(int valuesNumber)
	{
		this.valuesNumber = valuesNumber;
	}

	public U[] getValues()
	{
		return values;
	}

	public void setValues(U[] values)
	{
		this.values = values;
	}

	void checkGrid()
	{
		Boolean[] bytes = new Boolean[grid.getInputNumber()];
		for(int ii=0; ii < Math.pow(2, grid.getInputNumber()); ii++)
		{
			
		}
		grid.setInput(input);
	}

}
