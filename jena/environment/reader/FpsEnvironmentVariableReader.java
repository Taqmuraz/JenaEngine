package jena.environment.reader;

import java.util.Iterator;

import jena.environment.EnvironmentVariableReader;
import jena.environment.variable.IntegerVariable;

public class FpsEnvironmentVariableReader implements EnvironmentVariableReader
{
    public boolean isKey(String key)
    {
        return key.equals("fps");
    }
    public IntegerVariable read(Iterator<String> iterator)
    {
        int value = Integer.valueOf(iterator.next());
        return () -> value;
    }
}