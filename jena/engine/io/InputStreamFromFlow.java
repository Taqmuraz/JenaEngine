package jena.engine.io;

import java.io.InputStream;

public class InputStreamFromFlow extends InputStream
{
    private InputFlow flow;

    public InputStreamFromFlow(InputFlow flow)
    {
        this.flow = flow;
    }

    @Override
    public int read()
    {
        if (flow.hasNext())
        {
            return Byte.toUnsignedInt(flow.next());
        }
        else return -1;
    }
}