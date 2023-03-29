package jena.engine.math;

public final class ValueFloatCos implements ValueFloat
{
    private ValueFloat angle;

    public ValueFloatCos(ValueFloat angle)
    {
        this.angle = angle;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
       angle.accept(angle -> acceptor.call((float)Math.cos(angle)));
    }
}