package cartessian.genetic.programmming.Graph;


public class Edge
{
	private Vertex begin, // wskazuje wierzcholki miedzy ktorymi rozpieta jest
							// krawedz
			end; // ...
	private double weight; // waga krawedzi

//	private Edge()
//	{
//	}

	/**
	 * @param b
	 *            wierzcholek poczatkowy
	 * @param e
	 *            wierzcholek koncowy
	 * @param w
	 *            waga krawedzi
	 */
	public Edge(Vertex b, Vertex e, double w)
	{
		begin = b;
		end = e;
		weight = w;
	}

	public String toString()
	{
		return Integer.toString(begin.getNumber()) + " ---( "
				+ Double.toString(weight) + " )---> "
				+ Integer.toString(end.getNumber());
	}

	/**
	 * Zwraca wierzcholek poczatkowy krawedzi
	 */
	public Vertex getBegin()
	{
		return begin;
	}

	/**
	 * Zwraca wierzcholek koncowy krawedzi
	 */
	public Vertex getEnd()
	{
		return end;
	}

	/**
	 * Zwraca wage krawedzi
	 */
	public double getWeight()
	{
		return weight;
	}
}
