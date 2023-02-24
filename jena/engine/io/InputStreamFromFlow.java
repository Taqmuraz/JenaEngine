package jena.engine.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InputStreamFromFlow extends InputStream
{
    private List<Byte> buffer = new ArrayList<Byte>();
    private int position;

    public InputStreamFromFlow(InputFlow flow)
    {
        flow.read(MaxCount.identity, buffer::add);
    }

    @Override
    public int read()
    {
        if (position < buffer.size()) return Byte.toUnsignedInt(buffer.get(position++));
        else return -1;
    }
}