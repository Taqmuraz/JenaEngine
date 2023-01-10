package jena.engine.math;

public class MathFloat
{
    public static float lerp(float a, float b, float t)
    {
        return a + (b - a) * t;
    }
}