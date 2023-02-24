package jena.engine.io;

public class ByteArrayOutputFlow implements OutputFlow
{
    private Output output;
    private int position;

    public ByteArrayOutputFlow(byte[] array)
    {
        output = in -> array[position++] = in;
    }

    @Override
    public void write(Input input)
    {
        input.in(output);
    }
}