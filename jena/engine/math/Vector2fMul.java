package jena.engine.math;

public final class Vector2fMul implements Vector2f
{
    private Vector2f result;

    public Vector2fMul(Vector2f a, Vector2f b)
    {
        result = r -> a.accept((ax, ay) -> b.accept((bx, by) -> r.call(ax * bx, ay * by)));
    }
    public Vector2fMul(Vector2f a, ValueFloat b)
    {
        result = r -> a.accept((ax, ay) -> b.accept(m ->
        {
            r.call(ax * m, ay * m);
        }));
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}