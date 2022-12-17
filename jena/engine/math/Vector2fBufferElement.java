package jena.engine.math;

public final class Vector2fBufferElement extends Vector2fStruct
{
    public Vector2fBufferElement(float[] buffer, int startIndex, int stride)
    {
        x = buffer[startIndex];
        y = buffer[startIndex + stride];
    }
}