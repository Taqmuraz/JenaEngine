package jena.engine.io.encoding;

public interface DecodingStream
{
    byte[] read(int length);

    default int readInt()
    {
        return new IntEncoder().readInt(this);
    }
    default String readText()
    {
        return new TextEncoder().readText(this);
    }
    default byte[] readByteArray()
    {
        return new ByteArrayEncoder().readByteArray(this);
    }
}