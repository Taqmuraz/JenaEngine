package jena.engine.math;

public final class ValueFloatNegative implements ValueFloat
{
    ValueFloat source;

    public ValueFloatNegative(ValueFloat source)
    {
        this.source = source;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        source.accept(v -> acceptor.call(-v));
    }
}