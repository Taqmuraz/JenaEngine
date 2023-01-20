package jena.engine.graphics;

public class ColorFloatStruct extends ColorStruct
{
    public ColorFloatStruct(float r, float g, float b, float a)
    {
        this.r = (int)(r * float2byte);
        this.g = (int)(g * float2byte);
        this.b = (int)(b * float2byte);
        this.a = (int)(a * float2byte);
    }
    public ColorFloatStruct(Color color)
    {
        color.accept((r, g, b, a) ->
        {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        });
    }
}