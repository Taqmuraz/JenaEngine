package jena.engine.io;

public class ByteArrayInputFlow implements InputFlow
{
    public ByteArrayInputFlow(byte[] array)
    {
        this.array = array;
    }

    private byte[] array;
    private int position;

    @Override
    public byte next()
    {
        return array[position++];
    }

    @Override
    public boolean hasNext()
    {
        return position < array.length;
    }
}