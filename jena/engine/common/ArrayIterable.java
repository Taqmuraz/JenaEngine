package jena.engine.common;

import java.util.Iterator;

public final class ArrayIterable<T> implements Iterable<T>
{
    T[] array;

    @SafeVarargs
    public ArrayIterable(T... array)
    {
        this.array = array;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<>()
        {
            int position;
            @Override
            public boolean hasNext()
            {
                return position < array.length;
            }
            @Override
            public T next()
            {
                return array[position++];
            }
        };
    }
}