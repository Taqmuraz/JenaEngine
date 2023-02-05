package jena.engine.math;

public class Vector2fNegative implements Vector2f
{
    Vector2f source;

    public Vector2fNegative(Vector2f source)
    {
        this.source = source;
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        source.accept((x, y) -> acceptor.call(-x, -y));
    }
}