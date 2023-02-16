package jena.engine.math;

public class Vector3fLength implements ValueFloat
{
    Vector3f source;

    public Vector3fLength(Vector3f source)
    {
        this.source = source;
    }

    @Override
    public void accept(FloatAcceptor acceptor)
    {
        source.accept((x, y, z) -> acceptor.call((float)Math.sqrt(x * x + y * y + z * z)));
    }
}