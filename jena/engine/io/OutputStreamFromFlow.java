package jena.engine.io;

import java.io.OutputStream;

public class OutputStreamFromFlow extends OutputStream
{
    OutputFlow flow;

    public OutputStreamFromFlow(OutputFlow flow)
    {
        this.flow = flow;
    }

    @Override
    public void write(int b)
    {
        flow.write(Byte.valueOf((byte)b));
    }
}