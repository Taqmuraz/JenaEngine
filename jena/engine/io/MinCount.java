package jena.engine.io;

public class MinCount implements Count
{
    private int min;

    public MinCount(int min)
    {
        this.min = min;
    }

    @Override
    public int count(int max)
    {
        return Math.min(max, min);
    }
}