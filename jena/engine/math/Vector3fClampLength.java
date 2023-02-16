package jena.engine.math;

public class Vector3fClampLength implements Vector3f
{
    Vector3f result;

    public Vector3fClampLength(Vector3f source)
    {
        result = new Vector3fDiv(source, new Vector3fLength(source));
    }

    @Override
    public void accept(Vector3fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}