package jena.engine.math;

public final class Matrix3fInversed implements Matrix3f
{
    private Matrix3f source;

    public Matrix3fInversed(Matrix3f source)
    {
        this.source = source;
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        source.accept(s -> acceptor.call(i ->
        {
            return 0f;
        }));
    }
}