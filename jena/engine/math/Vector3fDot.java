package jena.engine.math;

public class Vector3fDot implements ValueFloat
{
    Vector3f a;
    Vector3f b;

    public Vector3fDot(Vector3f a, Vector3f b)
    {
        this.a = a;
        this.b = b;
    }

    @Override
    public float read()
    {
        Vector3fStruct s = new Vector3fStruct(new Vector3fMul(a, b));
        return s.x + s.y + s.z;
    }
}