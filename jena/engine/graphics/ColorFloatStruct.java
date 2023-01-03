package jena.engine.graphics;

public class ColorFloatStruct extends ColorStruct
{
    public ColorFloatStruct(float r, float g, float b, float a)
    {
        this.r = (byte)(r * float2byte);
        this.g = (byte)(g * float2byte);
        this.b = (byte)(b * float2byte);
        this.a = (byte)(a * float2byte);
    }
}