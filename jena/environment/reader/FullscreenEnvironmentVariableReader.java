package jena.environment.reader;

import java.util.Iterator;

import jena.environment.EnvironmentVariableReader;
import jena.environment.variable.FlagVariable;

public class FullscreenEnvironmentVariableReader implements EnvironmentVariableReader
{
    @Override
    public boolean isKey(String key)
    {
        return key.equals("fullscreen");
    }
    @Override
    public FlagVariable read(Iterator<String> iterator)
    {
        return new FlagVariable();
    }
}