package jena.engine.io;

public final class MaxCount implements Count
{
    public static final MaxCount identity = new MaxCount();

    @Override
    public int count(int max)
    {
        return max;
    }
} 