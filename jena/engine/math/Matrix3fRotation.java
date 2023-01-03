package jena.engine.math;

public class Matrix3fRotation extends Matrix3fStruct
{
    public Matrix3fRotation(float angle)
    {
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        elements[0] = cos;
        elements[1] = sin;
        elements[3] = -sin;
        elements[4] = cos;
    }
}