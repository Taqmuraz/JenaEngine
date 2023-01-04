package jena.environment.reader;

import java.util.Iterator;

import jena.environment.EnvironmentVariableReader;
import jena.environment.variable.DimensionVariable;

public class ScreenSizeEnvironmentVariableReader implements EnvironmentVariableReader
{
    public boolean isKey(String key)
    {
        return key.equals("resolution");
    }
    public DimensionVariable read(Iterator<String> iterator)
    {
        int width = Integer.valueOf(iterator.next());
        int height = Integer.valueOf(iterator.next());
        return acceptor -> acceptor.call(width, height);
    }
}