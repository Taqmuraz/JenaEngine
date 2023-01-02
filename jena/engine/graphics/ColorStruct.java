package jena.engine.graphics;

public class ColorStruct implements Color
{
    static final float byte2float = 1f / 255f;
    static final float float2byte = 255f;
    public byte r, g, b, a;

    public ColorStruct(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    public ColorStruct(float r, float g, float b, float a)
    {
        this.r = (byte)(r * float2byte);
        this.g = (byte)(g * float2byte);
        this.b = (byte)(b * float2byte);
        this.a = (byte)(a * float2byte);
    }

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