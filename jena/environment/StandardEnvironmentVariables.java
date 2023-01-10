package jena.environment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import jena.engine.common.Action;
import jena.engine.common.ActionSingle;
import jena.environment.reader.FpsEnvironmentVariableReader;
import jena.environment.reader.FullscreenEnvironmentVariableReader;
import jena.environment.reader.ScreenSizeEnvironmentVariableReader;

public class StandardEnvironmentVariables implements EnvironmentVariables
{
    HashMap<String, EnvironmentVariable> variables;

    public StandardEnvironmentVariables(String[] args)
    {
        variables = new HashMap<String, EnvironmentVariable>();
        Iterator<String> iterator = new Iterator<String>()
        {
            int index = 0;
            public boolean hasNext()
            {
                return index < args.length;
            }
            public String next()
            {
                String next = args[index++];
                System.out.println(next);
                return next;
            }
        };

        ArrayList<EnvironmentVariableReader> readers = new ArrayList<>();
        readers.add(new FpsEnvironmentVariableReader());
        readers.add(new FullscreenEnvironmentVariableReader());
        readers.add(new ScreenSizeEnvironmentVariableReader());

        while(iterator.hasNext())
        {
            String key = iterator.next();
            for(EnvironmentVariableReader reader : readers)
            {
                if (reader.isKey(key))
                {
                    EnvironmentVariable result = reader.read(iterator);
                    variables.put(key, result);
                    break;
                }
            }
        }
    }

    @Override @SuppressWarnings("unchecked")
    public <T extends EnvironmentVariable> void findVariable(String name, ActionSingle<? super T> hasVariableCase, Action noVariableCase)
    {
        if (variables.containsKey(name))
        {
            hasVariableCase.call((T)variables.get(name));
        }
        else
        {
            noVariableCase.call();
        }
    }
}