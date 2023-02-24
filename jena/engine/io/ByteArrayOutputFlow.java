package jena.engine.io;

public class ByteArrayOutputFlow implements OutputFlow
{
    private byte[] array;
    private int position;

    public ByteArrayOutputFlow(byte[] array)
    {
        this.array = array;
    }

    @Override
    public void write(Input input)
    {
        input.in(in ->
        {
            array[position++] = in;
        });
    }
}