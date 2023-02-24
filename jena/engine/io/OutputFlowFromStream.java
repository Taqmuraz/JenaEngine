package jena.engine.io;

import java.io.IOException;
import java.io.OutputStream;

public class OutputFlowFromStream implements OutputFlow
{
    OutputStream stream;

    public OutputFlowFromStream(OutputStream stream)
    {
        this.stream = stream;
    }

    @Override
    public void write(byte in)
    {
        try { stream.write(in); }
        catch(IOException error) {}
    }
}