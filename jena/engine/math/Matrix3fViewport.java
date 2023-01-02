package jena.engine.math;

public class Matrix3fViewport extends Matrix3fStruct
{
    public Matrix3fViewport(float width, float height)
    {
        width *= 0.5f;
        height *= 0.5f;
        elements[0] = width;
        elements[4] = -height;
        elements[6] = width;
        elements[7] = height;
    }
}