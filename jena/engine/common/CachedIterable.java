package jena.engine.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CachedIterable<T> implements Iterable<T>
{
    List<T> list;

    public CachedIterable(Iterable<T> source)
    {
        list = new ArrayList<T>();
        for(T item : source) list.add(item);
    }

    @Override
    public Iterator<T> iterator()
    {
        return list.iterator();
    }
}