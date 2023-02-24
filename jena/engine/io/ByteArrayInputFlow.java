package jena.engine.io;

public class ByteArrayInputFlow implements InputFlow
{
    private byte[] array;
    private int position;

    public ByteArrayInputFlow(byte[] array)
    {
        this.array = array;
    }

    @Override
    public void read(Count count, Output output)
    {
        int c = count.count(array.length);
        new ByteArrayInput(array, position, c).in(output);
        position += c;
    }
}