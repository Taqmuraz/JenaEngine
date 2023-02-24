package jena.engine.io;

import java.io.IOException;
import java.io.InputStream;

public class InputFlowFromStream implements InputFlow
{
    InputStream stream;
    int r;

    public InputFlowFromStream(InputStream stream)
    {
        this.stream = stream;
        read();
    }

    private void read()
    {
        try { r = stream.read(); }
        catch(IOException error) { r = -1; }
    }

    @Override
    public byte next()
    {
        byte result = (byte)r;
        read();
        return result;
    }

    @Override
    public boolean hasNext()
    {
        return r != -1;
    }   
}