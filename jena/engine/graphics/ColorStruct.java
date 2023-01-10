package jena.engine.graphics;

public class ColorStruct implements ColorFloat
{
    static final float byte2float = 1f / 255f;
    static final float float2byte = 255f;
    public int r, g, b, a;

    public ColorStruct()
    {
        
    }

    public ColorStruct(int r, int g, int b, int a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public void accept(ColorIntsAcceptor acceptor) 
    {
        acceptor.call(r, g, b, a);
    }

    @Override
    public void acceptFloats(ColorFloatsAcceptor acceptor)
    {
        acceptor.call(
            r * byte2float,
            g * byte2float,
            b * byte2float,
            a * byte2float);
    }
}