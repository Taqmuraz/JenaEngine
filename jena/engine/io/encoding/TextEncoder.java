package jena.engine.io.encoding;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class TextEncoder
{
    Charset charset;
    public TextEncoder()
    {
        charset = Charset.forName("US-ASCII");
    }

    public void writeText(EncodingStream stream, String text)
    {
        stream.writeByteArray(charset.encode(text).array());
    }
    
    public String readText(DecodingStream stream)
    {
        int length = stream.readInt();
        System.out.println("text length : " + length);
        return charset.decode(ByteBuffer.wrap(stream.read(length))).toString();
    }
}