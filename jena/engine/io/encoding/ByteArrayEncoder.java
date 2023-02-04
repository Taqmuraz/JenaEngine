package jena.engine.io.encoding;

public class ByteArrayEncoder
{
    public void writeByteArray(EncodingStream stream, byte[] array)
    {
        stream.writeInt(array.length);
        stream.write(array);
    }
    public byte[] readByteArray(DecodingStream stream)
    {
        int length = stream.readInt();
        return stream.read(length);
    }
}