package jena.environment.reader;

import java.util.Iterator;

import jena.environment.EnvironmentVariableReader;
import jena.environment.variable.StringVariable;

public class GraphicsVariableReader implements EnvironmentVariableReader
{
    @Override
    public boolean isKey(String key)
    {
        return key.equals("graphics");
    }

    @Override
    public StringVariable read(Iterator<String> iterator)
    {
        String value = iterator.next();
        return () -> value;
    }
}