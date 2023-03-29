package jena.engine.math;

public final class ValueFloatSin implements ValueFloat
{
    private ValueFloat angle;

    public ValueFloatSin(ValueFloat angle)
    {
        this.angle = angle;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
       angle.accept(angle -> acceptor.call((float)Math.sin(angle)));
    }
}