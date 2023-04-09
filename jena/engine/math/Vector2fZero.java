package jena.engine.math;

public final class Vector2fZero implements Vector2f
{
    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        acceptor.call(0f, 0f);
    }
}