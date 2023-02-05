package jena.engine.math;

public class Vector3fNegative implements Vector3f
{
    Vector3f source;

    public Vector3fNegative(Vector3f source)
    {
        this.source = source;
    }


    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        source.accept((x, y, z) -> acceptor.call(-x, -y, -z));
    }
}