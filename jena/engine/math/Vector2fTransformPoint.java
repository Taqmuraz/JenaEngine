package jena.engine.math;

public final class Vector2fTransformPoint implements Vector2f
{
    Vector2f result;

    public Vector2fTransformPoint(Matrix3f transform, Vector2f point)
    {
        Vector3f p = new Vector3fMul(a -> point.accept((x, y) -> a.call(x, y, 1f)), transform);
        result = a -> p.accept((x, y, z) -> a.call(x, y));
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}