package jena.engine.math;

public final class ValueFloatVector2fX implements ValueFloat
{
    Vector2f source;

    public ValueFloatVector2fX(Vector2f source)
    {
        this.source = source;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        source.accept((x, y) -> acceptor.call(x));
    }
}