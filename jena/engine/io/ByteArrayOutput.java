package jena.engine.io;

public class ByteArrayOutput implements Output
{
    private int position;
    private byte[] array;

    public ByteArrayOutput(byte[] array)
    {
        this.array = array;
    }

    @Override
    public void out(byte b)
    {
        array[position++] = b;
    }
}