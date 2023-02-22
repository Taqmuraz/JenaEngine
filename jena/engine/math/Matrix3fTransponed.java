package jena.engine.math;

public final class Matrix3fTransponed implements Matrix3f
{
    private Matrix3f source;

    public Matrix3fTransponed(Matrix3f source)
    {
        this.source = source;
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        source.accept(s -> acceptor.call(i -> s.at((i % 3) * 3 + (i / 3))));
    }
}