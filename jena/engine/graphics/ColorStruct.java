package jena.engine.graphics;

public class ColorStruct implements Color
{
    static final float byte2float = 1f / 255f;
    static final float float2byte = 255f;
    public byte r, g, b, a;

    @Override
    public void acceptBytes(ColorBytesAcceptor acceptor) 
    {
        acceptor.call(r, g, b, a);
    }

    @Override
    public void acceptInts(ColorIntsAcceptor acceptor) 
    {
        acceptor.call(Byte.toUnsignedInt(r), Byte.toUnsignedInt(g), Byte.toUnsignedInt(b), Byte.toUnsignedInt(a));
    }

    @Override
    public void acceptFloats(ColorFloatsAcceptor acceptor)
    {
        acceptor.call(
            Byte.toUnsignedInt(r) * byte2float,
            Byte.toUnsignedInt(g) * byte2float,
            Byte.toUnsignedInt(b) * byte2float,
            Byte.toUnsignedInt(a) * byte2float);
    }
}