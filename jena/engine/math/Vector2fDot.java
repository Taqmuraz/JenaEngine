package jena.engine.math;

public final class Vector2fDot implements ValueFloat
{
    private Vector2f mul;
    public Vector2fDot(Vector2f a, Vector2f b)
    {
        mul = new Vector2fMul(a, b);
    }
    @Override
    public void accept(FloatAcceptor acceptor)
    {
        mul.accept((x, y) -> acceptor.call(x + y));
    }
}