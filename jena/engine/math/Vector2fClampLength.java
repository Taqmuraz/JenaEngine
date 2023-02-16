package jena.engine.math;

public class Vector2fClampLength implements Vector2f
{
    Vector2f result;

    public Vector2fClampLength(Vector2f source, ValueFloat limit)
    {
        ValueFloat length = new Vector2fLength(source);
        Vector2f clamped = new Vector2fMul(new Vector2fNormalized(source), limit);
        result = a -> limit.accept(lim -> length.accept(len ->
        {
            if (len > lim) clamped.accept(a);
            else source.accept(a);
        }));
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}