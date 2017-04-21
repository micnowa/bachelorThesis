package cartessian.genetic.programming.sinus;

import java.util.LinkedList;

import cartessian.genetic.programming.fitness.Functional;

public class Divide implements Functional<Double>
{

	@Override public Double calculateValue(LinkedList<Double> list)
	{
		// TODO Auto-generated method stub
		if(Double.isNaN(list.get(0)) || Double.isInfinite(list.get(0))) return 1.0;
		if(Double.isNaN(list.get(1)) || Double.isInfinite(list.get(1))) return 1.0;

		if(list.get(1) == 0 ) return 1.0;
		double quotient = list.get(0) / list.get(1);
		if(Double.isNaN(quotient) || Double.isInfinite(quotient)) return 1.0;
		return quotient;
	}

	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}

	@Override public String toString()
	{
		return "/";
	}

}
