package jena.engine.math;

public final class Vector2fAdd implements Vector2f
{
    Vector2f a;
    Vector2f b;
    public Vector2fAdd(Vector2f a, Vector2f b)
    {
        this.a = a;
        this.b = b;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        a.accept((ax, ay) -> b.accept((bx, by) -> acceptor.call(ax + bx, ay + by)));
    }
}