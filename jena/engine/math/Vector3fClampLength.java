package jena.engine.math;

public class Vector3fClampLength implements Vector3f
{
    Vector3f result;

    public Vector3fClampLength(Vector3f source, ValueFloat limit)
    {
        ValueFloat length = new Vector3fLength(source);
        Vector3f clamped = new Vector3fMul(new Vector3fNormalized(source), limit);
        result = a -> limit.accept(lim -> length.accept(len ->
        {
            if (len > lim) clamped.accept(a);
            else source.accept(a);
        }));
    }

    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}