package jena.engine.common;

import java.util.Iterator;

public final class RangeIterable implements Iterable<Integer>
{
    int start;
    int end;
    public RangeIterable(int start, int length)
    {
        this.start = start;
        this.end = start + length;
    }
    @Override
    public Iterator<Integer> iterator()
    {
        return new Iterator<Integer>()
        {
            int position = start;
            @Override
            public boolean hasNext()
            {
                return position < end;
            }
            @Override
            public Integer next()
            {
                return position++;
            }
        };
    }
}