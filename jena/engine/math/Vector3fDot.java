package jena.engine.math;

public class Vector3fDot implements ValueFloat
{
    Vector3f mul;

    public Vector3fDot(Vector3f a, Vector3f b)
    {
        mul = new Vector3fMul(a, b);
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        mul.accept((x, y, z) -> acceptor.call(x + y + z));
    }
}