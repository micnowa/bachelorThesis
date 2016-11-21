package cartessian.genetic.programmming;

class Pair
{

	int ii;
	int jj;

	Pair(int x,int y)
	{
		this.ii = x;
		this.jj = y;
	}
	
	Pair(int tab[][])
	{
		this.ii = tab[0][0];
		this.jj = tab[0][1];
	}

	public int getIi()
	{
		return ii;
	}

	public void setIi(int ii)
	{
		this.ii = ii;
	}

	public int getJj()
	{
		return jj;
	}

	public void setJj(int jj)
	{
		this.jj = jj;
	}
	
	

}