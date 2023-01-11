package jena.engine.math;

public final class Vector3fAdd implements Vector3f
{
    Vector3f a;
    Vector3f b;

    public Vector3fAdd(Vector3f a, Vector3f b)
    {
        this.a = a;
        this.b = b;
        
    }
    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        a.accept((ax, ay, az) -> b.accept((bx, by, bz) -> acceptor.call(ax + bx, ay + by, az + bz)));
    }
}