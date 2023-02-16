package jena.engine.math;

public class Vector2fLength implements ValueFloat
{
    Vector2f source;

    public Vector2fLength(Vector2f source)
    {
        this.source = source;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        source.accept((x, y) -> acceptor.call((float)Math.sqrt(x * x + y * y)));
    }
}