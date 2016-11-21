package cartessian.genetic.programmming.Graph;

import java.util.LinkedList;
/**
 * Klasa reprezentuje wierzcholek grafu.
 * Wierzcholek jest przedstawiony jako punkt oraz krawędzie z niego wychodzące.
 * @author Michał Nowaliński
 * @version 1.0
 */
public class Vertex
{
    protected int number;
    private LinkedList<Edge> edge = new LinkedList<Edge>(); 
    public Vertex()
    {
    	
    }
 
    /**
     * @param i numer porzadkowy tworzonego wierzcholka
     */
    public Vertex(int i)
    {
        number = i;
    }
 
    public String toString()
    {
        if(edge.size() == 0)
            return "Ojoj brak krawędzi, number=" + this.number;
 
        String out = new String();
        for(Edge e : edge)
            out = out + e.toString() + "\n";
        return out;
    }
 
    /**
     * Zwraca numer porzadkowy wierzcholka
     */
    public int getNumber() 
    {
        return number;
    }
 
    /**
     * Zwraca kopie listy krawedzi wychodzacych z wierzcholka
     */
    public LinkedList<Edge> getEdgeList()
    {
        return new LinkedList<Edge>(edge);
    }
 
    /**
     * Usuwa krawedzie biegnace do i-tego wierzcholka (jezeli istnieje)
     * @param i numer wierzcholka do ktorego biegnie usuwana krawedz
     */
    public void removeEdge(int i)
    {
        int e = edge.size()-1;    //liczba krawedzi do sprawdzenia
        while( e>=0 )
        {
            if(edge.get(e).getEnd().getNumber() == i)
                edge.remove(e);
            e--;
        }
    }
 
    /**
     * Usuwa krawedzie biegnace do wierzcholka v
     * @param v wierzcholek do ktorego biegnie kasowana krawedz
     */
    public void removeEdge(Vertex v)
    {
        removeEdge(v.getNumber());        
    }
 
    /**
     * Dodaje nowa krawedz do wierzcholka
     * @param e dodawana krawedz
     */
    public void addEdge(Edge e)
    {
        edge.add(e);
    }
 
    /**
     * Zwraca krawedz biegnaca do wierzcholka o numerze porzadkowym n (jezeli istnieje)
     * @param n numer wierzcholka do ktorego biegnie szukana krawedz
     */
    public Edge getEdge(int n)
    {
        for(Edge e : edge)
        if(e.getEnd().getNumber() == n)
            return e;
        return null;
    } 
 
    /**
     * Zwraca n-ta w kolejnosci krawedz wychodzaca z tego wierzcholka (jezeli istnieje)
     * @param n numer szukanej krawedzi
     */
    public Edge getEdgeAt(int n)
    {
        if(n>=0 && n<edge.size())
            return edge.get(n);
        else
            return null;
    }
}