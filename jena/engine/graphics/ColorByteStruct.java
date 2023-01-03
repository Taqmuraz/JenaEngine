package jena.engine.graphics;

public class ColorByteStruct extends ColorStruct
{
    public ColorByteStruct(int r, int g, int b, int a)
    {
        this.r = (byte)r;
        this.g = (byte)g;
        this.b = (byte)b;
        this.a = (byte)a;
    }
}