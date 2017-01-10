package cartessian.genetic.programmming;

public class Fitness<T>
{
	private Grid<T> grid;
	private Gate<T> input[];
	private int valuesNumber;
	private T values[];
	double fitness;
	
	@SuppressWarnings({ "unchecked", "unused" }) public Fitness(Grid<T> grid)
	{
		this.grid = new Grid<>(grid);
		this.input = new Gate[grid.getInputNumber()];
		for(Gate<T> gate : input)
		{
			gate = new Gate<T>();
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

	public T[] getValues()
	{
		return values;
	}

	public void setValues(T[] values)
	{
		this.values = values;
	}

	@SuppressWarnings("unused") void checkGrid()
	{
		Boolean[] bytes = new Boolean[grid.getInputNumber()];
		for(int ii=0; ii < Math.pow(2, grid.getInputNumber()); ii++)
		{
			
		}
		grid.setInput(input);
	}

}
