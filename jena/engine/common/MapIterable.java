package jena.engine.common;

import java.util.Iterator;

public final class MapIterable<In, Out> implements Iterable<Out>
{
    Iterable<In> source;
    FunctionSingle<In, Out> mapping;

    public MapIterable(Iterable<In> source, FunctionSingle<In, Out> mapping)
    {
        this.source = source;
        this.mapping = mapping;
    }

    @Override
    public Iterator<Out> iterator()
    {
        Iterator<In> iterator = source.iterator();
        return new Iterator<Out>()
        {
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }

            @Override
            public Out next()
            {
                return mapping.call(iterator.next());
            }
        };
    }
}