package jena.environment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import jena.engine.common.Action;
import jena.engine.common.ActionSingle;

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
        readers.add(new EnvironmentVariableReader()
        {
            public boolean isKey(String key)
            {
                return key.equals("fps");
            }
            public EnvironmentVariable read(Iterator<String> iterator)
            {
                String variable = iterator.next();
                return () -> variable;
            }
        });

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

    @Override
    public void parseValue(String name, ActionSingle<EnvironmentVariable> acceptor, Action noVariableCase)
    {
        if (variables.containsKey(name))
        {
            acceptor.call(variables.get(name));
        }
        else
        {
            noVariableCase.call();
        }
    }
}