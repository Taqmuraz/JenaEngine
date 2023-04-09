package jena.engine.math;

public final class ValueFloatStruct implements ValueFloat
{
    public float value;

    public ValueFloatStruct(float value) {
        this.value = value;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        acceptor.call(value);
    }
}