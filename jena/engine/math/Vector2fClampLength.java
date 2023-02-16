package jena.engine.math;

public class Vector2fClampLength implements Vector2f
{
    Vector2f result;

    public Vector2fClampLength(Vector2f source)
    {
        result = new Vector2fDiv(source, new Vector2fLength(source));
    }

    @Override
    public void accept(Vector2fAcceptor acceptor)
    {
        result.accept(acceptor);
    }
}