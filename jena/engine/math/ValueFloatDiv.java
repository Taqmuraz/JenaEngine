package jena.engine.math;

public final class ValueFloatDiv implements ValueFloat
{
    ValueFloat a;
    ValueFloat b;
    public ValueFloatDiv(ValueFloat a, ValueFloat b)
    {
        this.a = a;
        this.b = b;
    }
    @Override
    public void accept(FloatAcceptor acceptor)
    {
        a.accept(a -> b.accept(b -> acceptor.call(a / b)));
    }
}