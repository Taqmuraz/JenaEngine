package jena.engine.io;

import java.io.IOException;
import java.io.InputStream;

public class InputFlowFromStream implements InputFlow
{
    InputStream stream;

    public InputFlowFromStream(InputStream stream)
    {
        this.stream = stream;
    }

    @Override
    public void read(Count count, Output output) {
        try
        {
            int index = 0;
            int r;
            int c = count.count(Integer.MAX_VALUE);
            while(index++ < c && (r = stream.read()) != -1) output.out((byte)r);
        }
        catch(IOException error) {}
    }
}