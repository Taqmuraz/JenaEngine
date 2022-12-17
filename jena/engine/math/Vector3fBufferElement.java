package jena.engine.math;

public final class Vector3fBufferElement extends Vector3fStruct
{
    public Vector3fBufferElement(float[] buffer, int startIndex, int stride)
    {
        x = buffer[startIndex];
        y = buffer[startIndex + stride];
        z = buffer[startIndex + stride + stride];
    }
}