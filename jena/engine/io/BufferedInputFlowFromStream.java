package jena.engine.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputFlowFromStream implements InputFlow
{
    private InputStream stream;
    private byte[] buffer;
    private int position;
    private int length;
    private boolean end;
    private final int bufferSize = 1024;

    public BufferedInputFlowFromStream(InputStream stream)
    {
        this.stream = stream;
        buffer = new byte[bufferSize];
        readBuffer();
    }

    private void readBuffer()
    {
        position = 0;
        try
        {
            length = stream.read(buffer);
            if (length == -1) end = true;
        }
        catch(IOException error) { end = true; };
    }


    @Override
    public byte next()
    {
        return buffer[position++];
    }
    @Override
    public boolean hasNext()
    {
        if (position >= length) readBuffer();
        return !end;
    }
}