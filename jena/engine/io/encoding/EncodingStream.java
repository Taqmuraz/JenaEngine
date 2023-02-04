package jena.engine.io.encoding;

public interface EncodingStream
{
    void write(byte[] bytes);

    default void writeInt(int value)
    {
        new IntEncoder().writeInt(this, value);
    }
    default void writeText(String text)
    {
        new TextEncoder().writeText(this, text);
    }
    default void writeByteArray(byte[] array)
    {
        new ByteArrayEncoder().writeByteArray(this, array);
    }
}