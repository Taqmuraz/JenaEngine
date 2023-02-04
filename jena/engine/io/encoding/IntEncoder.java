package jena.engine.io.encoding;

import java.nio.ByteBuffer;

public class IntEncoder
{
    public void writeInt(EncodingStream stream, int value)
    {
        stream.write(ByteBuffer.allocate(4).putInt(value).array());
    }
    public int readInt(DecodingStream stream)
    {
        return ByteBuffer.wrap(stream.read(4)).getInt();
    }
}