package cartessian.genetic.programming.sinus;

import java.util.LinkedList;

import cartessian.genetic.programmming.fitness.Functional;

public class ToPower implements Functional<Double>
{

	@Override public Double calculateValue(LinkedList<Double> list)
	{
		// TODO Auto-generated method stub
		if(Double.isNaN(list.get(0)) || Double.isInfinite(list.get(0))) return 1.0;
		if(Double.isNaN(list.get(1)) || Double.isInfinite(list.get(1))) return 1.0;
		double power;
		try
		{
			power = Math.pow(list.get(0), list.get(1));
		}catch (ArithmeticException e)
		{
			// TODO: handle exception
			return 1.0;
		}
		if(Double.isNaN(power) || Double.isInfinite(power)) return 1.0;
		return power;
	}

	@Override public int argsNumber()
	{
		// TODO Auto-generated method stub
		return 2;
	}

	@Override public String toString()
	{
		return "^";
	}
}
