package cartessian.genetic.programming.sinus;

import java.util.LinkedList;

import cartessian.genetic.programming.fitness.Functional;

public class Sine implements Functional<Double>
{

	@Override public Double calculateValue(LinkedList<Double> list)
	{
		// TODO Auto-generated method stub
		if(Double.isNaN(list.get(0)) || Double.isInfinite(list.get(0))) return 1.0;
		return Math.sin(list.get(0));
	}

	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override public String toString()
	{
		return "sin()";
	}

}
